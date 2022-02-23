package hu.katka.transport.controllers;

import hu.katka.transport.dtos.AddressDto;
import hu.katka.transport.entities.Address;
import hu.katka.transport.mapper.AddressMapper;
import hu.katka.transport.services.AddressService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

  @Autowired
  private AddressService addressService;

  @Autowired
  private AddressMapper addressMapper;

  @GetMapping
  public List<AddressDto> getAllAddresses() {
    return addressMapper.addressesToDto(addressService.findAll());
  }

  @GetMapping("/{id}")
  public AddressDto getById(@PathVariable Long id) {
    Address address = addressService.findById(id);
    return addressMapper.addressToDto(address);
  }

  @PostMapping
  public AddressDto createAddress(@RequestBody @Valid AddressDto addressDto) {
    Address address = addressService.addAddress(addressMapper.dtoToAddress(addressDto));
    return addressMapper.addressToDto(address);
  }

  @DeleteMapping("{id}")
  public void deleteAddress(@PathVariable Long id) {
    addressService.deleteAddress(id);
  }

  @PutMapping("{id}")
  public AddressDto modifyAddress(@PathVariable Long id,
                                  @RequestBody @Valid AddressDto addressDto) {
//    addressDto.setId(id);
    Address savedAddress = addressService.modifyAddress(addressMapper.dtoToAddress(addressDto), id);
    return addressMapper.addressToDto(savedAddress);
  }

}
