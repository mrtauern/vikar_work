package com.vikar.work.services;

import com.vikar.work.models.Assignment;

import com.vikar.work.models.Company;
import com.vikar.work.models.Worker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service("AssignmentService")
public interface AssignmentService{
    Optional<Assignment> findById(long id);
    Iterable<Assignment> findAll();
    Assignment save(Assignment assignment);
    Date createDateFromString(String date);
}
