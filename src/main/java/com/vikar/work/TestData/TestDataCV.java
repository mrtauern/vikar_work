package com.vikar.work.TestData;

import com.vikar.work.models.CV;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.CVRepo;
import com.vikar.work.repositories.FreelanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TestDataCV implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Qualifier("FreelanceRepo")
    private FreelanceRepo freelanceRepo;

    @Autowired
    @Qualifier("CVRepo")
    private CVRepo cvRepo;

    private List<CV> createCV(){
        List<CV> cvs = new ArrayList<>();

        long workerId = 1;

        Optional<Worker> worker = freelanceRepo.findById(workerId);

        Worker worker1 = worker.get();

        CV cv1 = new CV();
        cv1.setWorker(worker1);
        cv1.setWorkplace("BMW");
        cv1.setJobTitle("CEO");
        cv1.setStartDate("03/11/2014");
        cv1.setEndDate("07/05/2019");

        cvs.add(cv1);

        return cvs;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed");
        cvRepo.saveAll(createCV());
    }
}
