package com.vikar.work.services;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Message;
import com.vikar.work.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepo messageRepo;

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
}
