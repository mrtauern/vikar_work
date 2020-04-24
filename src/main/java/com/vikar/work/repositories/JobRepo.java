package com.vikar.work.repositories;

import com.vikar.work.models.Job;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("JobRepo")
@Repository
public interface JobRepo extends CrudRepository<Job, Long> {
    Optional<Job>findById(Long id);
}
