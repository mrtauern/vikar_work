package com.vikar.work.services;

import com.vikar.work.models.Company;
import com.vikar.work.models.Worker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CompanyService {
    Optional<Company> findById(long id);
    Company updateCompany(Company company);
    void deleteCompany(long id);

}
