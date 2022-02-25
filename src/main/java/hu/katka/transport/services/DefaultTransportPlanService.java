package hu.katka.transport.services;

import hu.katka.transport.entities.Milestone;
import hu.katka.transport.entities.Section;
import hu.katka.transport.entities.TransportPlan;
import hu.katka.transport.exceptions.EntityNotFoundException;
import hu.katka.transport.repositories.MilestoneRepository;
import hu.katka.transport.repositories.SectionRepository;
import hu.katka.transport.repositories.TransportPlanRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultTransportPlanService implements TransportPlanService {

  @Autowired
  TransportPlanRepository transportPlanRepository;

  @Autowired
  MilestoneRepository milestoneRepository;

  @Autowired
  SectionRepository sectionRepository;

  @Override
  @Transactional
  public void setDelay(Long transportPlanId, Long milestoneId, int delayInMinute) {
    TransportPlan transportPlan = getTransportPlanOrThrow(transportPlanId);
    Milestone milestone = getMilestoneOrThrow(milestoneId);
    List<Section> sections =
        sectionRepository.findByTransportPlanAndMilestones(transportPlanId, milestoneId);
    if (sections.isEmpty()) {
      throw new IllegalArgumentException(
          "The given milestone does not part of the given transport plan.");
    }
    milestone.setPlannedTime(milestone.getPlannedTime().plusMinutes(delayInMinute));
    Section section = sectionRepository.findByMilestoneId(milestoneId);
    Milestone nextMilestone = null;
    if (section.getFrom().getId().equals(milestoneId)) {
      nextMilestone = section.getTo();
    } else {
      int nextSectionNumber = section.getNumber() + 1;
      Section nextSection =
          sectionRepository.findByTransportPlanIdAndNumber(transportPlanId, nextSectionNumber);
      if (nextSection != null){
        nextMilestone = nextSection.getFrom();
      }
    }
    if (nextMilestone != null) {
      nextMilestone.setPlannedTime(nextMilestone.getPlannedTime().plusMinutes(delayInMinute));
    }
  }

  @Override
  public List<TransportPlan> findAllTransportPlan() {
    return transportPlanRepository.findAll();
  }

  private Milestone getMilestoneOrThrow(Long milestoneId) {
    Optional<Milestone> byId = milestoneRepository.findById(milestoneId);
    if (byId.isEmpty()) {
      throw new EntityNotFoundException("There is no milestone with the provided id.");
    }
    return byId.get();
  }

  private TransportPlan getTransportPlanOrThrow(Long transportPlanId) {
    Optional<TransportPlan> byId = transportPlanRepository.findById(transportPlanId);
    if (byId.isEmpty()) {
      throw new EntityNotFoundException("There is no transport plan with the provided id.");
    }
    return byId.get();
  }
}
