package identifier.repository;

import identifier.models.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
  Optional<Workspace> findByIdAndAvailableTrue(int id);
}
