package krainet.trainee.dao.api;

import krainet.trainee.dao.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IProjectDao extends JpaRepository <ProjectEntity, UUID> {
    Optional<ProjectEntity> findByUuid(UUID uuid);
}
