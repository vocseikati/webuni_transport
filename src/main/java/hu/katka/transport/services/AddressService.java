package hu.katka.transport.services;

import hu.katka.transport.entities.Address;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

  Address addAddress(Address newAddress);

  List<Address> findAll();

  Address findById(Long id);

  void deleteAddress(Long id);

  Address modifyAddress(Address address, Long id);

  Page<Address> findAddressByExample(Address example, Pageable pageable);
}
