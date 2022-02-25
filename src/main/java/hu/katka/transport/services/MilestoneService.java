package hu.katka.transport.services;

import hu.katka.transport.entities.Milestone;
import hu.katka.transport.repositories.MilestoneRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilestoneService {

  @Autowired
  MilestoneRepository milestoneRepository;

  public List<Milestone> getMilestones(){
    return milestoneRepository.findAll();
  }
}
