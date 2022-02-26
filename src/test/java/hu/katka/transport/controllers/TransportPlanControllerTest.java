package hu.katka.transport.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import hu.katka.transport.dtos.DelayDto;
import hu.katka.transport.entities.Address;
import hu.katka.transport.entities.Milestone;
import hu.katka.transport.entities.Section;
import hu.katka.transport.entities.TransportPlan;
import hu.katka.transport.repositories.AddressRepository;
import hu.katka.transport.repositories.MilestoneRepository;
import hu.katka.transport.repositories.SectionRepository;
import hu.katka.transport.repositories.TransportPlanRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class TransportPlanControllerTest {

  public static final String BASE_URI = "/api/transportPlans";

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

  @BeforeEach
  public void init() {
    Address address1 =
        createAddress("HU", "test city 1", "test street 1", "test zipcode 1", "test houseNumber 1",
            10, 20);
    Milestone milestone1 = createMilestone(LocalDateTime.now().plusMinutes(30), address1);
    Milestone milestone2 = createMilestone(LocalDateTime.now().plusMinutes(80), address1);
    Milestone milestone3 = createMilestone(LocalDateTime.now().plusHours(2), address1);
    Milestone milestone4 = createMilestone(LocalDateTime.now().plusHours(4), address1);
    Section section1 = createSection(0, milestone1, milestone2);
    Section section2 = createSection(1, milestone3, milestone4);
    TransportPlan transportPlan = createTransportPlan(10000);
    section1.setTransportPlan(transportPlan);
    section2.setTransportPlan(transportPlan);
    List<Section> sections = List.of(section1, section2);
    transportPlan.setSections(sections);
  }

  @Test
  public void testSetDelayWithStartMilestone() {
    List<TransportPlan> transportPlansBefore = getAllTransportPlans();
    TransportPlan transportPlanBefore = transportPlansBefore.get(0);
    Section sectionBefore = transportPlanBefore.getSections().get(0);
    Milestone fromBefore = sectionBefore.getFrom();
    Milestone toBefore = sectionBefore.getTo();
    LocalDateTime fromPlannedTimeBefore = fromBefore.getPlannedTime();
    LocalDateTime toPlannedTimeBefore = toBefore.getPlannedTime();

    int delayInMinutes = 30;
    DelayDto delayDto = new DelayDto(fromBefore.getId(), delayInMinutes);

    postDelay(fromBefore.getId(), delayDto)
        .expectStatus()
        .isOk();

    List<TransportPlan> transportPlansAfter= getAllTransportPlans();
    TransportPlan transportPlanAfter = transportPlansAfter.get(0);
    Section sectionAfter = transportPlanAfter.getSections().get(0);
    Milestone fromAfter = sectionAfter.getFrom();
    Milestone toAfter = sectionAfter.getTo();
    LocalDateTime fromPlannedTimeAfter = fromAfter.getPlannedTime();
    LocalDateTime toPlannedTimeAfter = toAfter.getPlannedTime();

    assertThat(fromPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(fromPlannedTimeAfter);
    assertThat(toPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(toPlannedTimeAfter);
    assertThat(transportPlanAfter.getIncome()).isEqualTo(9500);
  }

  @Test
  public void testSetDelayWithEndMilestone() {
    List<TransportPlan> transportPlansBefore = getAllTransportPlans();
    TransportPlan transportPlanBefore = transportPlansBefore.get(0);
    Section section1Before = transportPlanBefore.getSections().get(0);
    Section section2Before = transportPlanBefore.getSections().get(1);
    Milestone toBefore = section1Before.getTo();
    Milestone fromBefore = section2Before.getFrom();
    LocalDateTime toPlannedTimeBefore = toBefore.getPlannedTime();
    LocalDateTime fromPlannedTimeBefore = fromBefore.getPlannedTime();

    int delayInMinutes = 60;
    DelayDto delayDto = new DelayDto(2L, delayInMinutes);

    postDelay(toBefore.getId(), delayDto)
        .expectStatus()
        .isOk();

    List<TransportPlan> transportPlansAfter= getAllTransportPlans();
    TransportPlan transportPlanAfter = transportPlansAfter.get(0);
    Section section1After = transportPlanAfter.getSections().get(0);
    Section section2After = transportPlanAfter.getSections().get(1);
    Milestone toAfter = section1After.getTo();
    Milestone fromAfter = section2After.getFrom();
    LocalDateTime toPlannedTimeAfter = toAfter.getPlannedTime();
    LocalDateTime fromPlannedTimeAfter = fromAfter.getPlannedTime();

    assertThat(toPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(toPlannedTimeAfter);
    assertThat(fromPlannedTimeBefore.plusMinutes(delayInMinutes)).isEqualTo(fromPlannedTimeAfter);
    assertThat(transportPlanAfter.getIncome()).isEqualTo(9000);
  }

  private List<TransportPlan> getAllTransportPlans() {
    return webTestClient
        .get()
        .uri(BASE_URI)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(TransportPlan.class)
        .returnResult()
        .getResponseBody();
  }

  private ResponseSpec postDelay(Long transportPlanId, DelayDto delayDto){
    String path = BASE_URI + "/" + transportPlanId + "/delay";
    return webTestClient
        .post()
        .uri(path)
        .bodyValue(delayDto)
        .exchange();
  }

  private Address createAddress(String ISO, String city, String street, String zipCode,
                                String houseNumber, double width, double length) {
    return addressRepository.save(
        Address.builder()
            .ISO(ISO)
            .city(city)
            .street(street)
            .zipCode(zipCode)
            .houseNumber(houseNumber)
            .width(width)
            .length(length)
            .build());
  }

  private Milestone createMilestone(LocalDateTime plannedTime, Address address) {
    return milestoneRepository.save(
        Milestone.builder()
            .plannedTime(plannedTime)
            .address(address)
            .build());
  }

  private Section createSection(int number, Milestone from, Milestone to) {
    return sectionRepository.save(
        Section.builder()
            .number(number)
            .from(from)
            .to(to)
            .build());
  }

  private TransportPlan createTransportPlan(double income) {
    return transportPlanRepository.save(
        TransportPlan.builder()
            .income(income)
            .build());
  }
}