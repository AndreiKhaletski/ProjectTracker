package krainet.trainee.service;

import krainet.trainee.dao.api.IProjectDao;
import krainet.trainee.dao.entity.ProjectEntity;
import krainet.trainee.service.api.IProjectService;
import krainet.trainee.service.converter.project.ConverterDTOToProjectEntity;
import krainet.trainee.service.converter.project.ConverterEntityToProjectDTO;
import krainet.trainee.service.core.dto.PageOfProjectDto;
import krainet.trainee.service.core.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProjectService implements IProjectService {

    private final IProjectDao projectDao;
    private final ConverterDTOToProjectEntity convertToEntity;
    private final ConverterEntityToProjectDTO convertToDto;

    public ProjectService(IProjectDao projectDao,
                          ConverterDTOToProjectEntity convertToEntity,
                          ConverterEntityToProjectDTO convertToDto) {
        this.projectDao = projectDao;
        this.convertToEntity = convertToEntity;
        this.convertToDto = convertToDto;
    }

    @Override
    @Transactional
    public void create(ProjectDto projectDto) {
        ProjectEntity entity = convertToEntity.convert(projectDto);
        entity.setUuid(UUID.randomUUID());
        projectDao.saveAndFlush(entity);
    }

    @Override
    public Optional<ProjectEntity> findByUuid(UUID uuid) {
        return projectDao.findByUuid(uuid);
    }

    @Override
    public PageOfProjectDto findByProject(Integer page, Integer size) {
        Page<ProjectEntity> project = projectDao.findAll(PageRequest.of(page, size));
        return getPageProject(project);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        if (projectDao.existsById(uuid)) {
            projectDao.deleteById(uuid);
        }else{
            throw new IllegalArgumentException("Проект не найден");
        }
    }

    private PageOfProjectDto getPageProject(Page<ProjectEntity> objects) {
        PageOfProjectDto pageOfProject = new PageOfProjectDto();
        pageOfProject.setNumber(objects.getNumber());
        pageOfProject.setSize(objects.getSize());
        pageOfProject.setTotal_pages(objects.getTotalPages());
        pageOfProject.setNumber_of_elements(objects.getNumberOfElements());
        pageOfProject.setFirst(objects.isFirst());
        pageOfProject.setTotal_elements(objects.getTotalElements());
        pageOfProject.setLast(objects.isLast());

        List<ProjectDto> projectDtoList = objects
                .getContent()
                .stream()
                .map(convertToDto::convert)
                .collect(Collectors.toList());

        pageOfProject.setContent(projectDtoList);
        return pageOfProject;
    }
}
