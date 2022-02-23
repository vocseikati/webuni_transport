package hu.katka.transport.services;

import static hu.katka.transport.services.ValidationService.requireNonNull;
import static hu.katka.transport.services.ValidationService.requireNull;

import hu.katka.transport.dtos.AddressDto;
import hu.katka.transport.entities.Address;
import hu.katka.transport.exceptions.EntityNotFoundException;
import hu.katka.transport.repositories.AddressRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAddressService implements AddressService{

  @Autowired
  private AddressRepository addressRepository;

  @Override
  public List<Address> findAll() {
    return addressRepository.findAll();
  }

  @Override
  public Address findById(Long id) {
    return getAddressOrThrow(id);
  }

  @Override
  public void deleteAddress(Long id) {
    if (!addressRepository.existsById(id)){
      return;
    }
    addressRepository.deleteById(id);
  }

  @Override
  public Address modifyAddress(Address address, Long id) {
    if (address.getId() != null && !address.getId().equals(id)){
      throw new IllegalArgumentException("Address id should be null or equal with the provided id.");
    }
    Address originalAddress = getAddressOrThrow(id);
    address.setId(id);
    return addressRepository.save(address);
  }

  @Override
  public Address addAddress(Address newAddress) {
    requireNull(newAddress.getId(), "Address id must be null.");
    requireNonNull(newAddress, "Address must not be null.");
    return addressRepository.save(newAddress);
  }

  private Address getAddressOrThrow(Long id) {
    Optional<Address> byId = addressRepository.findById(id);
    if (byId.isEmpty()){
      throw new EntityNotFoundException("There is no address with the provided id.");
    }
    return byId.get();
  }
}
