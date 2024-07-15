package krainet.trainee.service;

import krainet.trainee.dao.api.IUserVerificationDao;
import krainet.trainee.dao.entity.UserVerificationEntity;
import krainet.trainee.service.api.IEmailService;
import krainet.trainee.service.api.IVerificationService;
import krainet.trainee.service.core.enums.EnumStatusSendEmail;
import krainet.trainee.service.exceptions.FailMailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.ExecutorService;

@Service
@Transactional(readOnly = true)
public class VerificationService implements IVerificationService {

    private final IUserVerificationDao verificationDao;
    private final IEmailService emailService;
    private final ExecutorService executorService;

    public VerificationService(IUserVerificationDao verificationDao,
                               IEmailService emailService,
                               ExecutorService executorService) {
        this.verificationDao = verificationDao;
        this.emailService = emailService;
        this.executorService = executorService;
    }

    @Override
    @Transactional
    public void create(String mail) {
        UserVerificationEntity userVerificationEntity = new UserVerificationEntity();
        userVerificationEntity.setMail(mail);
        userVerificationEntity.setCode(String.valueOf(new Random().nextInt(100000)));
        userVerificationEntity.setEnumStatusSendEmail(EnumStatusSendEmail.LOADED);
        verificationDao.saveAndFlush(userVerificationEntity);

        executorService.submit(() -> {
            try {
                emailService.sendEmailMessage(userVerificationEntity);
                userVerificationEntity.setEnumStatusSendEmail(EnumStatusSendEmail.OK);
                verificationDao.saveAndFlush(userVerificationEntity);

            }catch (FailMailSendException e){
                userVerificationEntity.setEnumStatusSendEmail(EnumStatusSendEmail.ERROR);
                verificationDao.saveAndFlush(userVerificationEntity);

                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String get(String mail) {
        return verificationDao.findByMail(mail).getCode();
    }
}
