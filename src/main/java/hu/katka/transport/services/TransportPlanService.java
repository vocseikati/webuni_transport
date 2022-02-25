package hu.katka.transport.services;

import hu.katka.transport.entities.TransportPlan;
import java.util.List;

public interface TransportPlanService {

  void setDelay(Long transportPlanId, Long milestoneId, int delayInMinute);

  List<TransportPlan> findAllTransportPlan();
}
