package hu.katka.transport.dtos;

import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class DelayDto {

  @Positive(message = "Milestone id must have a positive value.")
  private Long milestoneId;
  @Positive(message = "Delay must have a positive value.")
  private int delay;
}
