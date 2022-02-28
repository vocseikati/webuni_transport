package hu.katka.transport.repositories;

import hu.katka.transport.entities.Milestone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

  List<Milestone> findByAddressId(Long addressId);
}
