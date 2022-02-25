package hu.katka.transport.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MilestoneDto {

  private Long id;
  private LocalDateTime plannedTime;
  private AddressDto address;
}
