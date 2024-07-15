package krainet.trainee.service.core.dto;

import krainet.trainee.service.core.enums.EnumRole;
import krainet.trainee.service.core.enums.EnumStatusRegistration;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AboutUserDto {
    private ProjectDto uuid_project;
    private UUID uuid;
    private Long dt_create;
    private Long dt_update;
    private String mail;
    private String fio;
    private EnumRole role;
    private EnumStatusRegistration status;
    private String duration;
}
