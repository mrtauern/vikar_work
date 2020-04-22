package com.vikar.work.repositories;

import com.vikar.work.models.Company;
import com.vikar.work.models.Worker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("FreelanceRepo")
@Repository
public interface FreelanceRepo extends CrudRepository<Worker, Long> {
    Optional<Worker> findById(Long id);
}
