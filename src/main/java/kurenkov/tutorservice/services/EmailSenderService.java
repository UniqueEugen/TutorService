package kurenkov.tutorservice.services;

import jakarta.mail.MessagingException;
import kurenkov.tutorservice.entities.UserData;
import kurenkov.tutorservice.interfaces.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class EmailSenderService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailSenderService.class);
    EmailService emailService;

    private EmailSenderService(DefaultEmailService emailService){
        this.emailService = emailService;
    }


    public ResponseEntity<String> sendSimpleEmail(String email) {

        try {
            emailService.sendSimpleEmail(email, "Welcome", "This is a welcome email for your!!");
        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }



    public void sendEmailAttachment(String datetime, UserData userData, UserData tutor) {

        try {
            String email = setHTMLContent(tutor.getName(), datetime,  tutor.getTutor().getSpecialisation(), userData.getName());
            emailService.sendEmailWithAttachment(userData.getEMail(), "Order Confirmation", email, "classpath:Order.pdf");
        } catch (MessagingException | FileNotFoundException mailException) {
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            //return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //return new ResponseEntity<>("Please check your inbox for order confirmation", HttpStatus.OK);
    }

    public String setHTMLContent(String tutorName, String dateTime, String subject, String userName){
        String htmlContent = String.format(
                "<h1>Подтверждение бронирования репетитора</h1>" +
                        "<p>Здравствуйте, %s</p>" +
                        "<p>Спасибо за ваше бронирование! Мы рады сообщить вам, что ваш репетитор был успешно забронирован. Отслеживайте свою бронь в личном кабинете</p>" +
                        "<p><strong>Детали бронирования:</strong></p>" +
                        "<ul>" +
                        "<li><strong>Имя репетитора:</strong> %s</li>" +
                        "<li><strong>Дата и время:</strong> %s</li>" +
                        "<li><strong>Предмет:</strong> %s</li>" +
                        "</ul>" +
                        "<p>Если у вас есть вопросы, не стесняйтесь связаться с нами.</p>" +
                        "<p>С уважением,<br>Команда репетиторов</p>" +
                        "<div class='footer'>Это автоматическое сообщение, пожалуйста, не отвечайте на него.</div>",
                userName, tutorName, dateTime, subject);
        return htmlContent;
    }
}
