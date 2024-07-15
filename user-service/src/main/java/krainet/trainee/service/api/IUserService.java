package krainet.trainee.service.api;

import krainet.trainee.dao.entity.UserEntity;
import krainet.trainee.service.core.dto.UserDto;
import krainet.trainee.service.core.dto.PageOfUserDto;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    void create(UserDto userDTO);

    PageOfUserDto findByMail(Integer page, Integer size);

    Optional<UserEntity> findByMail(UUID uuid);

    void update(UUID uuid, Long dtUpdate, UserDto userDTO);

    UserEntity findByMail(String mail);

    void save (UserEntity userEntity);

    void delete(UUID uuid);
}
