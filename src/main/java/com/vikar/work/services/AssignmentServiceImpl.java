package com.vikar.work.services;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Company;
import com.vikar.work.repositories.AssignmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    AssignmentRepo assignmentRepo;

    public Optional<Assignment> findById(long id) { return assignmentRepo.findById(id); }

    public Iterable<Assignment> findAll() { return assignmentRepo.findAll(); }

    public Assignment save(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }

}

