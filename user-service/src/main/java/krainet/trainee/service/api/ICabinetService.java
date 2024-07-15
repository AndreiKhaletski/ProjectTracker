package krainet.trainee.service.api;

import krainet.trainee.service.core.dto.AboutUserDto;
import krainet.trainee.service.core.dto.AuthorizationDto;

import java.util.UUID;

public interface ICabinetService {

    void verification(String code, String mail);

    String authorization(AuthorizationDto authorizationDTO);

    AboutUserDto getInfoMe();

    void exit(UUID uuid);
}
