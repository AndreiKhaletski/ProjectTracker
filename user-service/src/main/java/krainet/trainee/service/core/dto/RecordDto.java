package krainet.trainee.service.core.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    private UUID uuid;
    private UserDto userDto;
    private ProjectDto projectDto;
    private LocalDateTime login_start;
    private LocalDateTime logout_stop;
}
