package krainet.trainee.service.api;

import krainet.trainee.dao.entity.ProjectEntity;
import krainet.trainee.service.core.dto.PageOfProjectDto;
import krainet.trainee.service.core.dto.ProjectDto;

import java.util.Optional;
import java.util.UUID;

public interface IProjectService {

    void create(ProjectDto projectDto);

    PageOfProjectDto findByProject(Integer page, Integer size);

    void delete(UUID uuid);

    Optional<ProjectEntity> findByUuid(UUID uuid);
}
