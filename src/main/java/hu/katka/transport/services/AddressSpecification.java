package hu.katka.transport.services;

import hu.katka.transport.entities.Address;
import hu.katka.transport.entities.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

  public static Specification<Address> hasCity(String city) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder
        .like(criteriaBuilder.lower(root.get(Address_.city)), city.toLowerCase() + "%"));
  }

  public static Specification<Address> hasStreet(String street) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder
        .like(criteriaBuilder.lower(root.get(Address_.street)), street.toLowerCase() + "%"));
  }

  public static Specification<Address> hasCountryCode(String code){
    return ((root, query, criteriaBuilder) -> criteriaBuilder
        .equal(root.get(Address_.ISO), code));
  }

  public static Specification<Address> hasZipCode(String zipCode){
    return ((root, query, criteriaBuilder) -> criteriaBuilder
        .equal(root.get(Address_.zipCode), zipCode));
  }
}
