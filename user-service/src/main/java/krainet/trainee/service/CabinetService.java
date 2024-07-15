package krainet.trainee.service;

import krainet.trainee.dao.entity.UserEntity;
import krainet.trainee.service.api.IRecordService;
import krainet.trainee.service.api.IUserService;
import krainet.trainee.service.api.IVerificationService;
import krainet.trainee.service.converter.user.ConverterEntityToUserDTO;
import krainet.trainee.service.core.dto.AboutUserDto;
import krainet.trainee.service.core.enums.EnumStatusRegistration;
import krainet.trainee.service.jwt.JwtTokenHandler;
import krainet.trainee.service.api.ICabinetService;
import krainet.trainee.service.core.dto.AuthorizationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CabinetService implements ICabinetService {

    private final IVerificationService verificationService;
    private final JwtTokenHandler jwtTokenHandler;
    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final IRecordService recordService;
    private final ConverterEntityToUserDTO dtoConvert;

    public CabinetService(IVerificationService verificationService,
                          JwtTokenHandler jwtTokenHandler,
                          IUserService userService,
                          PasswordEncoder encoder,
                          IRecordService recordService,
                          ConverterEntityToUserDTO dtoConvert) {
        this.verificationService = verificationService;
        this.jwtTokenHandler = jwtTokenHandler;
        this.userService = userService;
        this.encoder = encoder;
        this.recordService = recordService;
        this.dtoConvert = dtoConvert;
    }

    @Override
    @Transactional
    public void verification(String code, String mail) {
        UserEntity userEntity = userService.findByMail(mail);

        if (Objects.equals(code, verificationService.get(mail))) {
            userEntity.setStatus(EnumStatusRegistration.ACTIVATED);
            userService.save(userEntity);
        } else {
            throw new IllegalArgumentException("Неверный код верификации");
        }
    }

    @Override
    @Transactional
    public String authorization(AuthorizationDto authorizationDTO) {
        UserEntity userEntity = userService.findByMail(authorizationDTO.getMail());

        if (!encoder.matches(authorizationDTO.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Неверный пользователь или пароль");
        }

        if(userEntity.getStatus() == EnumStatusRegistration.ACTIVATED){

            String token = jwtTokenHandler.generateAccessToken(
                    authorizationDTO.getMail(),
                    String.valueOf(userEntity.getRole()));

            recordService.create(userEntity);

            return token;
        }else{
            throw new IllegalArgumentException("Необходимо подтвердить аккаунт для входа в систему");
        }
    }

    @Override
    @Transactional
    public void exit(UUID uuid) {
        recordService.exit(uuid);
    }

    @Override
    public AboutUserDto getInfoMe() {
        AboutUserDto dto = dtoConvert.convertAboutUser(userService.findByMail(UserHolder.getUser().getUsername()));

        dto.setDuration(recordService.timeToWorkProject(dto.getUuid()));
        return dto;
    }
}
