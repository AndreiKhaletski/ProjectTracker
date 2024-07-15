package krainet.trainee.service.emailservice;


import krainet.trainee.dao.entity.UserVerificationEntity;
import krainet.trainee.service.emailservice.properties.EmailProperties;
import krainet.trainee.service.api.IEmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;


@Service
public class EmailService implements IEmailService {

    private final EmailProperties emailProperties;

    public EmailService(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    public void sendEmailMessage(UserVerificationEntity userVerificationEntity) {

        // Создание сессии электронной почты unkingip@mail.ru
        Session session = Session.getInstance(emailProperties.getProps(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        emailProperties.getProps().getProperty("mail.smtp.user"),
                        emailProperties.getProps().getProperty("mail.smtp.password")
                );
            }
        });

        // Создание сообщения электронной почты mail.ru
        Message messageToUser = new MimeMessage(session);
        try {
            messageToUser.setFrom(new InternetAddress(
                    emailProperties.getProps().getProperty("mail.smtp.user"))
            );
            messageToUser.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userVerificationEntity.getMail()));
            messageToUser.setSubject("Подтверждение почтового адреса");
            messageToUser.setText(userVerificationEntity.getCode());

            // Отправка сообщения для пользователя из MessagesDTO
            Transport.send(messageToUser);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке email" + e.getMessage(), e);
        }
    }
}
