package krainet.trainee.dao.api;

import krainet.trainee.dao.entity.RecordEntity;
import krainet.trainee.service.core.enums.EnumStatusUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IRecordDao extends JpaRepository<RecordEntity, UUID> {

    RecordEntity findByUserEntityUuidAndStatus(UUID userUuid, EnumStatusUser status);

    List<RecordEntity> findAllByUserEntityUuidAndStatus(UUID uuid, EnumStatusUser enumStatusUser);

}
