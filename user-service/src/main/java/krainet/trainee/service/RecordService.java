package krainet.trainee.service;

import krainet.trainee.dao.api.IRecordDao;
import krainet.trainee.dao.entity.RecordEntity;
import krainet.trainee.dao.entity.UserEntity;
import krainet.trainee.service.api.IRecordService;
import krainet.trainee.service.core.enums.EnumStatusUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RecordService implements IRecordService {

    private final IRecordDao recordDao;

    public RecordService(IRecordDao recordDao) {
        this.recordDao = recordDao;
    }

    @Override
    @Transactional
    public void create(UserEntity userEntity) {
        RecordEntity recordEntity = new RecordEntity();

        if (recordDao.findByUserEntityUuidAndStatus(userEntity.getUuid(), EnumStatusUser.ACTIVE) == null){
            recordEntity.setUuid(UUID.randomUUID());
            recordEntity.setUserEntity(userEntity);
            recordEntity.setProjectEntity(userEntity.getProjectEntity());
            recordEntity.setLogin(LocalDateTime.now());
            recordEntity.setStatus(EnumStatusUser.ACTIVE);

            recordDao.saveAndFlush(recordEntity);
        }else {
            throw new IllegalArgumentException("Вы не завершили предыдущую сессию");
        }
    }

    @Override
    @Transactional
    public void exit(UUID uuid) {
        RecordEntity recordEntity = recordDao.findByUserEntityUuidAndStatus(uuid, EnumStatusUser.ACTIVE);
        recordEntity.setStatus(EnumStatusUser.NOT_ACTIVE);
        recordEntity.setLogout(LocalDateTime.now());

        Integer start = recordEntity.getLogin().getSecond();
        Integer end = recordEntity.getLogout().getSecond();
        Integer result = end - start;

        recordEntity.setDuration(result);
        recordDao.saveAndFlush(recordEntity);
    }

    @Override
    public String timeToWorkProject(UUID uuid) {
        List<RecordEntity> userList = recordDao
                .findAllByUserEntityUuidAndStatus(uuid, EnumStatusUser.NOT_ACTIVE);

        Long time = 0L;
        for (int i = 0; i < userList.size(); i++) {
            time += userList.get(i).getDuration();
        }

        long hour = time / 3600;
        long min = (time / 60) % 60;
        long sec = time % 60;

        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
}
