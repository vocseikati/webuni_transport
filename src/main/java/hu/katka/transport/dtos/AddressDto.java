package hu.katka.transport.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {

  private Long id;

  @NotEmpty(message = "Country ISO must have a value.")
  @Size(min=2, max=2)
  private String ISO;

  @NotEmpty(message = "City must have a value.")
  private String city;
  @NotEmpty(message = "Street must have a value.")
  private String street;
  @NotEmpty(message = "Zip code must have a value.")
  private String zipCode;
  @NotEmpty(message = "House number must have a value.")
  private String houseNumber;
  private double width;
  private double length;

}
