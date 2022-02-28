package hu.katka.transport.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import hu.katka.transport.configurations.TransportConfigurationProperties;
import hu.katka.transport.dtos.DelayDto;
import hu.katka.transport.dtos.LoginDto;
import hu.katka.transport.entities.Milestone;
import hu.katka.transport.entities.Section;
import hu.katka.transport.entities.TransportPlan;
import hu.katka.transport.repositories.AddressRepository;
import hu.katka.transport.repositories.MilestoneRepository;
import hu.katka.transport.repositories.SectionRepository;
import hu.katka.transport.repositories.TransportPlanRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class TransportPlanControllerTest {

  public static final String BASE_URI = "/api/transportPlans";

  private String jwt;

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  TransportPlanRepository transportPlanRepository;

  @Autowired
  AddressRepository addressRepository;

  @Autowired
  MilestoneRepository milestoneRepository;

  @Autowired
  SectionRepository sectionRepository;

  @Autowired
  TransportConfigurationProperties config;

  @BeforeEach
  public void init() {
    LoginDto body = new LoginDto("Administrator", "pass");
    jwt = webTestClient.post()
        .uri("/api/login")
        .bodyValue(body)
        .exchange()
        .expectBody(String.class)
        .returnResult()
        .getResponseBody();
  }

  @Test
  public void testSetDelayWithMilestone() {
    DelayDto delayDto = new DelayDto(1L, 30);
    Milestone milestoneBefore = milestoneRepository.findById(delayDto.getMilestoneId()).get();
    LocalDateTime plannedTimeBefore = milestoneBefore.getPlannedTime();
    postDelay(1L, delayDto);
    Milestone milestoneAfter = milestoneRepository.findById(delayDto.getMilestoneId()).get();
    assertThat(milestoneAfter.getPlannedTime())
        .isEqualTo(plannedTimeBefore.plusMinutes(delayDto.getDelay()));
  }

  @Test
  public void testSetDelayWithStartMilestoneWorksCorrectly() {
    TransportPlan transportPlanBefore = transportPlanRepository.findById(1L).get();
    double incomeBefore = transportPlanBefore.getIncome();
    Long sectionId = 1L;
    Section sectionBefore = sectionRepository.findById(sectionId).get();
    Milestone fromBefore = sectionBefore.getFrom();
    Milestone toBefore = sectionBefore.getTo();
    LocalDateTime fromPlannedTimeBefore = fromBefore.getPlannedTime();
    LocalDateTime toPlannedTimeBefore = toBefore.getPlannedTime();

    int delayInMinutes = config.getReducing().getLimit1();
    DelayDto delayDto = new DelayDto(fromBefore.getId(), delayInMinutes);

    postDelay(transportPlanBefore.getId(), delayDto);

    TransportPlan transportPlanAfter = transportPlanRepository.findById(1L).get();
    double incomeAfter = transportPlanAfter.getIncome();
    Section sectionAfter = sectionRepository.findById(sectionId).get();
    Milestone fromAfter = sectionAfter.getFrom();
    Milestone toAfter = sectionAfter.getTo();
    LocalDateTime fromPlannedTimeAfter = fromAfter.getPlannedTime();
    LocalDateTime toPlannedTimeAfter = toAfter.getPlannedTime();

    assertThat(fromPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(fromPlannedTimeAfter);
    assertThat(toPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(toPlannedTimeAfter);
    assertThat(incomeAfter)
        .isEqualTo(incomeBefore * ((double) (100 - config.getReducing().getPercent1()) / 100));
  }

  @Test
  public void testSetDelayWithEndMilestoneWorksCorrectly() {
    TransportPlan transportPlanBefore = transportPlanRepository.findById(1L).get();
    double incomeBefore = transportPlanBefore.getIncome();
    Long sectionId1 = 1L;
    Long sectionId2 = 2L;
    Section section1Before = sectionRepository.findById(sectionId1).get();
    Section section2Before = sectionRepository.findById(sectionId2).get();
    Milestone toBefore = section1Before.getTo();
    Milestone fromBefore = section2Before.getFrom();
    LocalDateTime toPlannedTimeBefore = toBefore.getPlannedTime();
    LocalDateTime fromPlannedTimeBefore = fromBefore.getPlannedTime();

    int delayInMinutes = config.getReducing().getLimit2();
    DelayDto delayDto = new DelayDto(2L, delayInMinutes);

    postDelay(transportPlanBefore.getId(), delayDto);

    TransportPlan transportPlanAfter = transportPlanRepository.findById(1L).get();
    double incomeAfter = transportPlanAfter.getIncome();
    Section section1After = sectionRepository.findById(sectionId1).get();
    Section section2After = sectionRepository.findById(sectionId2).get();
    Milestone toAfter = section1After.getTo();
    Milestone fromAfter = section2After.getFrom();
    LocalDateTime toPlannedTimeAfter = toAfter.getPlannedTime();
    LocalDateTime fromPlannedTimeAfter = fromAfter.getPlannedTime();

    assertThat(toPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(toPlannedTimeAfter);
    assertThat(fromPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(fromPlannedTimeAfter);
    assertThat(incomeAfter)
        .isEqualTo(incomeBefore * ((double) (100 - config.getReducing().getPercent2()) / 100));
  }

  @Test
  public void testThatMilestoneIdNotPartOfTransportPlanThrowBadRequest() {
    String path = BASE_URI + "/" + 1 + "/delay";
    DelayDto delayDto = new DelayDto(0L, 120);
    webTestClient
        .post()
        .uri(path)
        .headers(headers -> headers.setBearerAuth(jwt))
        .bodyValue(delayDto)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  public void testThatNonexistentTransportPlanThrowNotFound() {
    String path = BASE_URI + "/" + 0 + "/delay";
    DelayDto delayDto = new DelayDto(1L, 10);
    webTestClient
        .post()
        .uri(path)
        .headers(headers -> headers.setBearerAuth(jwt))
        .bodyValue(delayDto)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  private void postDelay(Long transportPlanId, DelayDto delayDto) {
    String path = BASE_URI + "/" + transportPlanId + "/delay";
    webTestClient
        .post()
        .uri(path)
        .headers(headers -> headers.setBearerAuth(jwt))
        .bodyValue(delayDto)
        .exchange()
        .expectStatus()
        .isOk();
  }

}