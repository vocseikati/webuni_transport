package hu.katka.transport.mapper;

import hu.katka.transport.dtos.SectionDto;
import hu.katka.transport.entities.Section;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectionMapper {

  List<SectionDto> sectionsToDtos(List<Section> sections);

  @Mapping(target = "from.address", ignore = true)
  @Mapping(target = "to.address", ignore = true)
  SectionDto sectionToDto(Section section);
}
