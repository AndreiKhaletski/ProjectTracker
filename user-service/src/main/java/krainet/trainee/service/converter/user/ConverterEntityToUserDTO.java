package krainet.trainee.service.converter.user;

import krainet.trainee.dao.entity.ProjectEntity;
import krainet.trainee.dao.entity.UserEntity;
import krainet.trainee.service.api.IProjectService;
import krainet.trainee.service.converter.project.ConverterEntityToProjectDTO;
import krainet.trainee.service.core.dto.AboutUserDto;
import krainet.trainee.service.core.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Component
public class ConverterEntityToUserDTO {

    private final ConverterEntityToProjectDTO converterToDto;
    private final IProjectService projectService;

    public ConverterEntityToUserDTO(ConverterEntityToProjectDTO converterToDto,
                                    IProjectService projectService) {
        this.converterToDto = converterToDto;
        this.projectService = projectService;
    }

    public UserDto convertUser(UserEntity item){
        UserDto userDTO = new UserDto();
        userDTO.setUuid(item.getUuid());
        userDTO.setDt_create(item.getDt_create().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userDTO.setDt_update(item.getDt_update().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userDTO.setMail(item.getMail());
        userDTO.setFio(item.getFio());
        userDTO.setRole(item.getRole());
        userDTO.setStatus(item.getStatus());
        return userDTO;
    }

    public AboutUserDto convertAboutUser(UserEntity item){
        AboutUserDto userAboutDTO = new AboutUserDto();
        Optional<ProjectEntity> entity = get(item);
        if(entity.isPresent()){
            userAboutDTO.setUuid_project(converterToDto.convert(entity.get()));
        }
        userAboutDTO.setUuid(item.getUuid());
        userAboutDTO.setDt_create(item.getDt_create().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userAboutDTO.setDt_update(item.getDt_update().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userAboutDTO.setMail(item.getMail());
        userAboutDTO.setFio(item.getFio());
        userAboutDTO.setRole(item.getRole());
        userAboutDTO.setStatus(item.getStatus());
        return userAboutDTO;
    }

    private Optional<ProjectEntity> get(UserEntity item) {
        if (item.getProjectEntity() != null) {
            UUID projectUuid = item.getProjectEntity().getUuid();
            return projectService.findByUuid(projectUuid);
        }
        return Optional.empty();
    }
}
