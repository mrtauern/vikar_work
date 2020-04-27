package com.vikar.work.repositories;

import com.vikar.work.models.CV;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Qualifier("CVRepo")
@Repository
public interface CVRepo extends CrudRepository<CV, Long> {

}
