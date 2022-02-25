package hu.katka.transport.mapper;

import hu.katka.transport.dtos.TransportPlanDto;
import hu.katka.transport.entities.TransportPlan;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

  List<TransportPlanDto> transportPlansToDtos(List<TransportPlan> transportPlans);

  @Mapping(target = "sections.from.address", ignore = true)
  @Mapping(target = "sections.to.address", ignore = true)
  TransportPlanDto transportPlanToDto(TransportPlan transportPlan);
}
