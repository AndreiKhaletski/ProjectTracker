package krainet.trainee.service.api;

import krainet.trainee.dao.entity.UserEntity;

public interface IUserDetailsService {
    UserEntity get(String username);
}
