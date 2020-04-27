package com.vikar.work.services;

import com.vikar.work.models.Job;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepo jobRepo;

    public Optional<Job> findById(long id) {
        return jobRepo.findById(id);
    }

    @Override
    public Job save(Job job) {
        return null;
    }

    @Override
    public Job updateJob(Job job) {
        return null;
    }

    @Override
    public void deleteJob(Job job) {

    }

    @Override
    public Iterable<Job> findAll() {
        return jobRepo.findAll();
    }
}
