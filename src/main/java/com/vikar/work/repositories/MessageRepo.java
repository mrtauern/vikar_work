package com.vikar.work.repositories;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("MessageRepo")
@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {

    Optional<Message> findById(Long id);
    Iterable<Message> findAll();
}
