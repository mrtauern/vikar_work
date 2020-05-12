package com.vikar.work.services;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Message;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public interface MessageService {
    Optional<Message> findById(long id);
    Iterable<Message> findAll();
    Message save(Message message);

}
