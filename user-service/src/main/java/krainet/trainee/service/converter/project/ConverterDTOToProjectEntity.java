package krainet.trainee.service.converter.project;

import krainet.trainee.dao.entity.ProjectEntity;
import krainet.trainee.service.core.dto.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ConverterDTOToProjectEntity {

    public ProjectEntity convert(ProjectDto item){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(item.getName());
        projectEntity.setDescription(item.getDescription());
        return projectEntity;
    }
}
