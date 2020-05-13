package com.vikar.work.services;

import com.vikar.work.controllers.FreelanceController;
import com.vikar.work.models.Assignment;
import com.vikar.work.models.Company;
import com.vikar.work.models.MapMarker;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.FreelanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

@Service("FreelanceService")
public class FreelanceServiceImpl implements FreelanceService {
    @Autowired
    FreelanceRepo freelanceRepo;

    public Optional<Worker> findById(long id){
        return freelanceRepo.findById(id);
    }

    @Override
    public Worker save(Worker worker) {
        return freelanceRepo.save(worker);
    }
  
    public Worker updateWorker(Worker worker) {
        return freelanceRepo.save(worker);
    }

    public void deleteWorker(long id) {
        freelanceRepo.deleteById(id);
    }

    public Iterable<Worker> findAll(){
        return freelanceRepo.findAll();
    }

    Logger log = Logger.getLogger(FreelanceService.class.getName());

    @Override
    public ArrayList<MapMarker> markerList() {
        ArrayList<MapMarker> markers = new ArrayList<>();
        MapMarker markerTest = new MapMarker();

        markerTest.setId(1);
        markerTest.setLatitude(55.716346);
        markerTest.setLongitude(12.531859);
        markerTest.setTitle("Test");
        markerTest.setStreetname("Møntmestervej");
        markerTest.setStreetnumber(20);
        markerTest.setZIP(2400);

        MapMarker markerTest2 = new MapMarker();

        markerTest2.setId(2);
        markerTest2.setLatitude(55.716092);
        markerTest2.setLongitude(12.530518);
        markerTest2.setTitle("Test2");
        markerTest2.setStreetname("Møntmestervej");
        markerTest2.setStreetnumber(30);
        markerTest2.setZIP(2400);

        markers.add(markerTest);
        markers.add(markerTest2);

        return markers;
    }

    public String[] checkSession(String sessionString) {
        String[] returnString = new String[] {"",""};

        returnString[0] = sessionString.substring(1);
        returnString[1] = sessionString.substring(0, 1);

        return returnString;
    }


}
