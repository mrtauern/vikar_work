package com.vikar.work.services;

import com.vikar.work.models.Job;
import com.vikar.work.models.Worker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JobService {
    Optional<Job> findById(long id);
    Job save(Job job);
    Job updateJob(Job job);
    void deleteJob(Job job);
    Iterable<Job> findAll();
}
