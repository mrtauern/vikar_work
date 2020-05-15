package com.vikar.work.repositories;

import com.vikar.work.models.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Qualifier("CompanyRepo")
@Repository
public interface CompanyRepo extends CrudRepository<Company, Long> {

    Optional<Company> findById(Long id);

}
