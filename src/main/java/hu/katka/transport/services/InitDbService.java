package hu.katka.transport.services;

import hu.katka.transport.entities.Address;
import hu.katka.transport.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitDbService {

  @Autowired
  AddressRepository addressRepository;

//  @Transactional
  public void deleteDb(){
    addressRepository.deleteAll();
  }

  public void insertTestData() {
    Address testAddress1 =
        createAddress("HU", "test city 1", "test street 1", "test zipcode 1", "test houseNumber 1",
            10, 20);
    Address testAddress2 =
        createAddress("HU", "test city 2", "test street 2", "test zipcode 2", "test houseNumber 2",
            30, 40);
  }

  private Address createAddress(String ISO, String city, String street, String zipCode,
                                String houseNumber, double width, double length) {
    return addressRepository.save(
        Address.builder()
        .ISO(ISO)
        .city(city)
        .street(street)
        .zipCode(zipCode)
        .houseNumber(houseNumber)
        .width(width)
        .length(length)
        .build());
  }
}
