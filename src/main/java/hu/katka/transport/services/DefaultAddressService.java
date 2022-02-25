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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

  @Override
  public Page<Address> findAddressByExample(Address example, Pageable pageable) {
    requireNonNull(example, "Example can not be null.");
    Specification<Address> spec = Specification.where(null);
    String city = example.getCity();
    String street = example.getStreet();
    String country = example.getISO();
    String zipCode = example.getZipCode();

    if (StringUtils.hasText(city)){
      spec = spec.and(AddressSpecification.hasCity(city));
    }

    if (StringUtils.hasText(street)){
      spec = spec.and(AddressSpecification.hasStreet(street));
    }

    if (StringUtils.hasText(country)){
      spec = spec.and(AddressSpecification.hasCountryCode(country));
    }

    if (StringUtils.hasText(zipCode)){
      spec = spec.and(AddressSpecification.hasZipCode(zipCode));
    }

    return addressRepository.findAll(spec, pageable);
  }

  private Address getAddressOrThrow(Long id) {
    Optional<Address> byId = addressRepository.findById(id);
    if (byId.isEmpty()){
      throw new EntityNotFoundException("There is no address with the provided id.");
    }
    return byId.get();
  }
}
