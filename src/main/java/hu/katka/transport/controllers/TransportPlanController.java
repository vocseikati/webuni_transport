package hu.katka.transport.controllers;

import hu.katka.transport.dtos.DelayDto;
import hu.katka.transport.dtos.TransportPlanDto;
import hu.katka.transport.entities.TransportPlan;
import hu.katka.transport.mapper.TransportPlanMapper;
import hu.katka.transport.services.TransportPlanService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

  @Autowired
  TransportPlanService transportPlanService;

  @Autowired
  TransportPlanMapper mapper;

  @GetMapping
  public List<TransportPlanDto> getAllTransportPlan() {
    List<TransportPlan> transportPlans = transportPlanService.findAllTransportPlan();
    return mapper.transportPlansToDtos(transportPlans);
  }

  @PostMapping("/{id}/delay")
  public void transportDelay(@PathVariable Long id, @RequestBody @Valid DelayDto delayDto) {
    transportPlanService.setDelay(id, delayDto.getMilestoneId(), delayDto.getDelay());
  }
}
