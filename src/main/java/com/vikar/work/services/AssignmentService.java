package com.vikar.work.services;

import com.vikar.work.models.Assignment;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AssignmentService{
    Optional<Assignment> findById(long id);
    Iterable<Assignment> findAll();
}
