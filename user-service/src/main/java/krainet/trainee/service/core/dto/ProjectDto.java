package krainet.trainee.service.core.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private UUID uuid;
    private String name;
    private Long dt_create;
    private Long dt_update;
    private String description;
}
