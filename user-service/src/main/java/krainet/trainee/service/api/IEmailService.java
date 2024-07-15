package krainet.trainee.service.api;


import krainet.trainee.dao.entity.UserVerificationEntity;
import krainet.trainee.service.exceptions.FailMailSendException;

public interface IEmailService {

    void sendEmailMessage(UserVerificationEntity userVerificationEntity) throws FailMailSendException;

}
