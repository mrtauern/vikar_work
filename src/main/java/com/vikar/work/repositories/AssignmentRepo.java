package com.vikar.work.repositories;

import com.vikar.work.models.Assignment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("AssignmentRepo")
@Repository
public interface AssignmentRepo extends CrudRepository<Assignment, Long> {

    Optional<Assignment> findById(Long id);
    Iterable<Assignment> findAll();
}
