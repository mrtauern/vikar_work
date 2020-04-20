package com.vikar.work.services;

import com.vikar.work.repositories.FreelanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreelanceServiceImpl implements FreelanceService {
    @Autowired
    FreelanceRepo freelanceRepo;
}
