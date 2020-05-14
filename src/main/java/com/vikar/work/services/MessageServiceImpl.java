package com.vikar.work.services;

import com.vikar.work.controllers.HomeController;
import com.vikar.work.models.Assignment;
import com.vikar.work.models.Message;
import com.vikar.work.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepo messageRepo;

    Logger log = Logger.getLogger(MessageServiceImpl.class.getName());

    @Override
    public Optional<Message> findById(long id) {
        return messageRepo.findById(id);
    }

    @Override
    public Iterable<Message> findAll() {
        return messageRepo.findAll();
    }

    public Message save(Message message) {
        return messageRepo.save(message);
    }

    public ArrayList<Message> findMessages(String sessionId, String sessionType) {
        ArrayList<Message> messages = (ArrayList<Message>) findAll();
        ArrayList<Message> recievedMessages = new ArrayList<>();

        if(sessionType.equals("w")) {
            for (Message m: messages) {
                log.info("Test worker");
                if(m.getRecipientWorker() != null && m.getRecipientWorker().getId() == Integer.valueOf(sessionId)) {
                    log.info("Found message for worker adding to list");
                    recievedMessages.add(m);
                }
            }
        }
        else if(sessionType.equals("c")) {
            for (Message m: messages) {
                log.info("test company");
                if(m.getRecipientCompany() != null && m.getRecipientCompany().getId() == Integer.valueOf(sessionId)) {
                    log.info("Found message for company adding to list");
                    recievedMessages.add(m);

                }
            }
        }

        else {
            log.info("Error sessiontype is neither c or w");
        }
        log.info("returning messages");
        return recievedMessages;
    }

    public void sendEmail(int userId) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("VikarWork@gmail.com", "VW202005");
            }
        });
        try {

            MimeMessage msg = new MimeMessage(session);

            String to = "VikarWork@gmail.com, niklasld@gmail.com";

            InternetAddress[] address = InternetAddress.parse(to, true);

            msg.setRecipients(MimeMessage.RecipientType.TO, address);
            msg.setSubject("Password reset vikar work");
            msg.setSentDate(new Date());

            String mailText;

            mailText = "Hej" + "\n\n";
            mailText += "Reset dit password her \n\n";
            mailText += "http://localhost/resetPassword/" + userId + "\n\n";
            mailText += "Med venlig hilsen.\n";
            mailText += "Vikar Work";

            msg.setText(mailText);

            msg.setHeader("XPriority", "1");
            Transport.send(msg);
            log.info("Mail has been sent successfully");
        } catch (MessagingException mex) {
            log.info("Unable to send an email" + mex);
        }
    }
}
