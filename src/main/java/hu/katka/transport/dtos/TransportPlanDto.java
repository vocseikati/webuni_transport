package hu.katka.transport.dtos;

import java.util.List;
import lombok.Data;

@Data
public class TransportPlanDto {

  private Long id;
  private double income;

  private List<SectionDto> sections;

}
