package krainet.trainee.service.converter.user;

import krainet.trainee.dao.entity.UserEntity;
import krainet.trainee.service.core.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class ConverterDTOToUserEntity {

    public UserEntity convert(UserDto item){
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(item.getUuid());
        userEntity.setMail(item.getMail());
        userEntity.setFio(item.getFio());
        userEntity.setRole(item.getRole());
        userEntity.setStatus(item.getStatus());
        return userEntity;
    }
}
