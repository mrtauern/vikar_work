package com.vikar.work.TestData;

import com.vikar.work.models.Company;
import com.vikar.work.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestDataCompany implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Qualifier("CompanyRepo")
    private CompanyRepo companyRepo;

    private List<Company> createCompany(){
        List<Company> companies = new ArrayList<>();

        Company company1 = new Company();
        company1.setCVRNumber(12345678);
        company1.setBankNumber(987654321);
        company1.setHouseNumber(67);
        company1.setZip(3030);

        company1.setCompanyName("AudiBilService");
        company1.setUsername("Audi");
        company1.setPassword("smukkebiler420");
        company1.setStreetName("Route66");
        company1.setCity("Ingolfstadt");

        companies.add(company1);
        return companies;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed company");
        companyRepo.saveAll(createCompany());
    }
}
