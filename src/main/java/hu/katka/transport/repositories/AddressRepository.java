package hu.katka.transport.repositories;

import hu.katka.transport.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository
    extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address>,
    PagingAndSortingRepository<Address, Long> {



}
