package kurenkov.tutorservice.interfaces;


import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;

import java.io.FileNotFoundException;

public interface EmailService {

    void sendSimpleEmail(final String toAddress, final String subject, final String message);
    @Async
    void sendEmailWithAttachment(final String toAddress, final String subject, final String message, final String attachment) throws MessagingException, FileNotFoundException, jakarta.mail.MessagingException;
}
