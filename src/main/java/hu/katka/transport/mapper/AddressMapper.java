package hu.katka.transport.mapper;

import hu.katka.transport.dtos.AddressDto;
import hu.katka.transport.entities.Address;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  Address dtoToAddress(AddressDto addressDto);

  AddressDto addressToDto(Address address);

  List<Address> dtosToAddresses(List<AddressDto> addressDtos);

  List<AddressDto> addressesToDto(List<Address> addresses);

}
