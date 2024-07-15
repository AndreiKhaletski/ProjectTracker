package krainet.trainee.dao.api;

import krainet.trainee.dao.entity.UserVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserVerificationDao extends JpaRepository<UserVerificationEntity, String> {

    UserVerificationEntity findByMail(String mail);
}
