package hu.katka.transport.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitDbService {

  @Autowired
  AddressRepository addressRepository;

  @Autowired
  MilestoneRepository milestoneRepository;

  @Autowired
  SectionRepository sectionRepository;

  @Autowired
  TransportPlanRepository transportPlanRepository;

  @Transactional
  public void deleteDb() {
    addressRepository.deleteAll();
    milestoneRepository.deleteAll();
    sectionRepository.deleteAll();
    transportPlanRepository.deleteAll();
  }

  @Transactional
  public void insertTestData() {
    Address address1 =
        createAddress("HU", "test city 1", "test street 1", "test zipcode 1", "test houseNumber 1",
            10, 20);
    Address address2 =
        createAddress("HU", "test city 2", "test street 2", "test zipcode 2", "test houseNumber 2",
            30, 40);
    Address address3 =
        createAddress("UK", "London", "Backer street", "1234", "42",
            320, 80);
    Address address4 =
        createAddress("US", "New York", "Trafalgar street", "7896", "63",
            320, 810);
    Milestone milestone1 = createMilestone(LocalDateTime.now().plusDays(1), address1);
    Milestone milestone2 = createMilestone(LocalDateTime.now().plusDays(2), address2);
    Milestone milestone3 = createMilestone(LocalDateTime.now().plusDays(3), address3);
    Milestone milestone4 = createMilestone(LocalDateTime.now().plusDays(4), address4);
    Milestone milestone5 = createMilestone(LocalDateTime.now().plusDays(5), address2);
    Milestone milestone6 = createMilestone(LocalDateTime.now().plusDays(6), address3);
    Milestone milestone7 = createMilestone(LocalDateTime.now().plusDays(7), address1);
    Milestone milestone8 = createMilestone(LocalDateTime.now().plusDays(8), address4);

    Section section1 = createSection(0, milestone1, milestone2);
    Section section2 = createSection(1, milestone3, milestone4);
    Section section3 = createSection(2, milestone5, milestone6);
    Section section4 = createSection(3, milestone7, milestone8);

    TransportPlan transportPlan = createTransportPlan(10000);
    transportPlan.addSection(section1);
    transportPlan.addSection(section2);
    transportPlan.addSection(section3);
    transportPlan.addSection(section4);
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
