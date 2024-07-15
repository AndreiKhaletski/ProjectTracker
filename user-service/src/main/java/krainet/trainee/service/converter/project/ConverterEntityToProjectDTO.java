package krainet.trainee.service.converter.project;

import krainet.trainee.dao.entity.ProjectEntity;
import krainet.trainee.service.core.dto.ProjectDto;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ConverterEntityToProjectDTO {

    public ProjectDto convert(ProjectEntity item){
        ProjectDto projectDto = new ProjectDto();
        projectDto.setUuid(item.getUuid());
        projectDto.setName(item.getName());
        projectDto.setDt_create(item.getDt_create().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        projectDto.setDt_update(item.getDt_update().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        projectDto.setDescription(item.getDescription());
        return projectDto;
    }
}
