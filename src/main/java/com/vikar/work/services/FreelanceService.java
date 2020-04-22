package com.vikar.work.services;

import com.vikar.work.models.Worker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface FreelanceService {
    Optional<Worker> findById(long id);
    Worker save(Worker worker);
    Worker updateWorker(Worker worker);
    void deleteWorker(long id);
}
