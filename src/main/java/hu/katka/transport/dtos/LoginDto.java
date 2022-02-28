package hu.katka.transport.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {

  private String username;
  private String password;

}
