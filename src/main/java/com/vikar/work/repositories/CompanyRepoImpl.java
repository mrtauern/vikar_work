package com.vikar.work.repositories;

import com.vikar.work.models.Company;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CompanyRepoImpl {

    
    public <S extends Company> S save(S s) {
        return null;
    }

    
    public <S extends Company> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    
    public Optional<Company> findById(Long id) {
        return Optional.empty();
    }

    
    public boolean existsById(Long aLong) {
        return false;
    }

    
    public Iterable<Company> findAll() {
        return null;
    }

    
    public Iterable<Company> findAllById(Iterable<Long> iterable) {
        return null;
    }

    
    public long count() {
        return 0;
    }

    
    public void deleteById(Long aLong) {

    }

    
    public void delete(Company company) {

    }

    
    public void deleteAll(Iterable<? extends Company> iterable) {

    }

    
    public void deleteAll() {

    }
}
