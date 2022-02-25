package hu.katka.transport.repositories;

import hu.katka.transport.entities.Section;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SectionRepository extends JpaRepository<Section, Long> {

  @EntityGraph(attributePaths = {"from", "to", "transportPlan"})
  @Query("SELECT s FROM Section s")
  List<Section> findAll();

  @Query("SELECT s FROM Section s WHERE s.transportPlan.id = :transportPlanId AND (s.from.id = :milestoneId OR s.to.id = :milestoneId)")
  List<Section> findByTransportPlanAndMilestones(Long transportPlanId, Long milestoneId);

  @Query("SELECT s FROM Section s WHERE s.from.id = :milestoneId OR s.to.id = :milestoneId")
  Section findByMilestoneId(Long milestoneId);

  @Query("SELECT s FROM Section s WHERE s.transportPlan.id = :transportPlanId AND s.number = :number")
  Section findByTransportPlanIdAndNumber(Long transportPlanId, int number);
}
