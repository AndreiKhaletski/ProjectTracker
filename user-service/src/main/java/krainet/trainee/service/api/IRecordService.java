package krainet.trainee.service.api;

import krainet.trainee.dao.entity.UserEntity;

import java.util.UUID;

public interface IRecordService {
    void create(UserEntity userEntity);

    void exit(UUID uuid);

    String timeToWorkProject(UUID uuid);
}
