package krainet.trainee.controller.http;

import krainet.trainee.dao.entity.ProjectEntity;
import krainet.trainee.service.api.IProjectService;
import krainet.trainee.service.converter.project.ConverterEntityToProjectDTO;
import krainet.trainee.service.core.dto.PageOfProjectDto;
import krainet.trainee.service.core.dto.ProjectDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final IProjectService projectService;
    private final ConverterEntityToProjectDTO convertToDto;

    public ProjectController(IProjectService projectService,
                             ConverterEntityToProjectDTO convertToDto) {
        this.projectService = projectService;
        this.convertToDto = convertToDto;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectDto projectDto) {
        projectService.create(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageOfProjectDto> get(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return ResponseEntity.ok(projectService.findByProject(page, size));
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<ProjectDto> get(@PathVariable("uuid") UUID uuid){
        Optional<ProjectEntity> entity = projectService.findByUuid(uuid);
        if (entity.isPresent()){
            return ResponseEntity.ok(convertToDto.convert(entity.get()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid){
        projectService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
