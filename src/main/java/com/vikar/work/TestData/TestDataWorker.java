package com.vikar.work.TestData;

import com.vikar.work.models.Worker;
import com.vikar.work.repositories.FreelanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestDataWorker implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    @Qualifier("FreelanceRepo")
    private FreelanceRepo freelanceRepo;

    private List<Worker> createWorker() {
        List<Worker> workers = new ArrayList<>();

        Worker worker1 = new Worker();
        worker1.setCVRNumber(22311551);
        worker1.setBankNumber(22154321);
        worker1.setHouseNumber(33);
        worker1.setZip(2400);

        worker1.setFirstname("Kalle");
        worker1.setLastname("Kallesen");
        worker1.setEmail("Kalle@Kallesen@gmail.com");
        worker1.setPassword("SmukkeKalle21");
        worker1.setStreetName("Helgevej");
        worker1.setCity("Copenhagen");
        worker1.setUsername("user123");

        workers.add(worker1);

        return workers;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed");
        freelanceRepo.saveAll(createWorker());
    }
}