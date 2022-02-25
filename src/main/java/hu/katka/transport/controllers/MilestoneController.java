package hu.katka.transport.controllers;

import hu.katka.transport.dtos.MilestoneDto;
import hu.katka.transport.mapper.MilestoneMapper;
import hu.katka.transport.services.MilestoneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

  @Autowired
  MilestoneService milestoneService;

  @Autowired
  MilestoneMapper mapper;

  @GetMapping
  public List<MilestoneDto> getAllMilestones(){
    return mapper.milestonesToDtos(milestoneService.getMilestones());
  }
}
