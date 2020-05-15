package com.vikar.work.services;

import com.vikar.work.models.CV;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.CVRepo;
import com.vikar.work.repositories.FreelanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("CVService")
public class CVServiceImpl implements CVService {
    @Autowired
    CVRepo cvRepo;

    @Autowired
    FreelanceService freelanceService;

    public Optional<CV> findById(long id){
        return cvRepo.findById(id);
    }

    public Iterable<CV> findAll(){
        return cvRepo.findAll();
    }

    public void save(CV cv){
        cvRepo.save(cv);

        Worker worker = freelanceService.findById(cv.getWorker().getId()).get();
        worker.getCvs().add(cv);
        freelanceService.save(worker);
    }

    public void delete(CV cv){
        Worker worker = freelanceService.findById(cv.getWorker().getId()).get();
        worker.getCvs().remove(cv);
        freelanceService.save(worker);

        cvRepo.delete(cv);
    }
}
