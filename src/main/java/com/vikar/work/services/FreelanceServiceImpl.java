package com.vikar.work.services;

import com.vikar.work.models.Company;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.FreelanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FreelanceServiceImpl implements FreelanceService {
    @Autowired
    FreelanceRepo freelanceRepo;

    public Optional<Worker> findById(long id){
        return freelanceRepo.findById(id);
    }

    @Override
    public Worker save(Worker worker) {
        return freelanceRepo.save(worker);
    }
}
