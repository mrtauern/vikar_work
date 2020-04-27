package com.vikar.work.services;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Company;
import com.vikar.work.repositories.AssignmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service("AssignmentService")
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    AssignmentRepo assignmentRepo;

    public Optional<Assignment> findById(long id) { return assignmentRepo.findById(id); }

    public Iterable<Assignment> findAll() { return assignmentRepo.findAll(); }

    public Assignment save(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }

    @Override
    public Date createDateFromString(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date returnDate = new Date();
        try {

            returnDate = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

}

