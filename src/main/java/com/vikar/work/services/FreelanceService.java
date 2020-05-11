package com.vikar.work.services;

import com.vikar.work.models.Company;
import com.vikar.work.models.Assignment;
import com.vikar.work.models.MapMarker;
import com.vikar.work.models.Worker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service("FreelanceService")
public interface FreelanceService {
    Optional<Worker> findById(long id);
    Worker save(Worker worker);
    Worker updateWorker(Worker worker);
    void deleteWorker(long id);
    Iterable<Worker> findAll();
    ArrayList<MapMarker> markerList();
    String checkSession(int userId, String sessionString);
}
