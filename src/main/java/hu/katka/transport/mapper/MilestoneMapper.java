package hu.katka.transport.mapper;

import hu.katka.transport.dtos.MilestoneDto;
import hu.katka.transport.entities.Milestone;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {

  List<MilestoneDto> milestonesToDtos(List<Milestone> milestones);

  @Mapping(target = "address", ignore = true)
  MilestoneDto milestoneToDto(Milestone milestone);
}
