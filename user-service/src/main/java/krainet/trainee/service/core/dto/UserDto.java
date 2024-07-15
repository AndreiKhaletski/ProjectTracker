package krainet.trainee.service.core.dto;


import krainet.trainee.service.core.enums.EnumRole;
import krainet.trainee.service.core.enums.EnumStatusRegistration;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID uuid;
    private ProjectDto uuid_project;
    private Long dt_create;
    private Long dt_update;
    @NotBlank(message = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз")
    private String mail;
    private String fio;
    private EnumRole role;
    private EnumStatusRegistration status;
    @NotBlank(message = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз")
    private String password;
}
