package hu.katka.transport.dtos;

import lombok.Data;

@Data
public class SectionDto {

  private Long id;
  private int number;
  private MilestoneDto from;
  private MilestoneDto to;

}
