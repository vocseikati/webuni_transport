package hu.katka.transport.repositories;

import hu.katka.transport.entities.TransportPlan;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

  @EntityGraph(attributePaths = {"sections", "sections.from", "sections.to", "sections.from.address", "sections.to.address"})
  @Query("SELECT t FROM TransportPlan t")
  List<TransportPlan> findAll();
}
