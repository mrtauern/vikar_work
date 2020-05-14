package com.vikar.work.services;

import com.vikar.work.controllers.HomeController;
import com.vikar.work.models.Assignment;
import com.vikar.work.models.Message;
import com.vikar.work.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
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
}
