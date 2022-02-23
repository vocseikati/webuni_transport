package hu.katka.transport.services;

import hu.katka.transport.entities.Address;
import java.util.List;

public interface AddressService {

  Address addAddress(Address newAddress);

  List<Address> findAll();

  Address findById(Long id);

  void deleteAddress(Long id);

  Address modifyAddress(Address address, Long id);
}
