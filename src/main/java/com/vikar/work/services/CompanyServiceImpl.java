package com.vikar.work.services;

import com.vikar.work.models.Company;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("CompanyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepo companyRepo;

    public Optional<Company> findById(long id){
        return companyRepo.findById(id);
    }

    @Override
    public Company save(Company company) {
        return companyRepo.save(company);
    }

    public Company updateCompany(Company company) {
        return companyRepo.save(company);
    }

    public void deleteCompany(long id) {
        companyRepo.deleteById(id);
    }

    public Iterable<Company> findAll(){
        return companyRepo.findAll();
    }

    public String[] checkSession(String sessionString) {
        String[] returnString = new String[] {"",""};

        returnString[0] = sessionString.substring(1);
        returnString[1] = sessionString.substring(0, 1);

        return returnString;
    }
}

