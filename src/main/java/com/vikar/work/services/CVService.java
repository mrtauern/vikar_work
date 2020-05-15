package com.vikar.work.services;

import com.vikar.work.models.CV;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("CVService")
public interface CVService {
    Optional<CV> findById(long id);
    Iterable<CV> findAll();
    void delete(CV cv);
    void save(CV cv);

}
