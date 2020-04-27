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

public class TestDataWorker /*implements ApplicationListener<ContextRefreshedEvent>*/{

    @Autowired
    @Qualifier("FreelanceRepo")
    private FreelanceRepo freelanceRepo;

    List<Worker> createWorker() {
        List<Worker> workers = new ArrayList<>();

        Worker worker1 = new Worker();
        worker1.setCVRNumber(22311551);
        worker1.setBankNumber(22154321);
        worker1.setHouseNumber(33);
        worker1.setZip(2400);

        worker1.setFirstname("Kalle");
        worker1.setLastname("Kallesen");

        worker1.setEmail("KalleKallesen@gmail.com");
        worker1.setPassword("SmukkeKalle21");

        worker1.setStreetName("Helgevej");
        worker1.setCity("Copenhagen");
        worker1.setUsername("user1234");

        workers.add(worker1);

        //testworker2
        Worker worker2 = new Worker();
        worker1.setCVRNumber(1122332);
        worker1.setBankNumber(22333111);
        worker1.setHouseNumber(22);
        worker1.setZip(2311);

        worker1.setFirstname("palle");
        worker1.setLastname("pallesen");
        worker1.setEmail("pallepallesen@gmail.com");
        worker1.setPassword("Smukkepalle21");
        worker1.setStreetName("Svendevej");
        worker1.setCity("Copenhagen");
        worker1.setUsername("user321");

        workers.add(worker2);

        return workers;
    }

/*    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed");
        freelanceRepo.saveAll(createWorker());
    }*/
}