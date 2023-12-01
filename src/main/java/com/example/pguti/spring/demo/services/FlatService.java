package com.example.pguti.spring.demo.services;

import com.example.pguti.spring.demo.entity.Flat;
import com.example.pguti.spring.demo.repository.FlatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlatService {
    @Value("${spring.datasource.url}")
    private String test;

    private final FlatsRepository flatsRepository;

    public void save(Flat flat) {
        flatsRepository.save(flat);
    }

    public List<Flat> getAll() {
        return flatsRepository.findAll();
    }

    public Optional<Flat> getById(Long id) throws NameNotFoundException {
        Optional<Flat> flat = flatsRepository.findById(id);
        if (flat.isEmpty()) {
            throw new NameNotFoundException("No such ID");
        }
        return flat;
    }

    public Flat updateById(Long id, Flat updatedFlat) throws NameNotFoundException {
        if(flatsRepository.findById(id).isEmpty()){
            throw new NameNotFoundException("No such ID");
        }
        updatedFlat.setId(id);
        return flatsRepository.save(updatedFlat);
    }
    public void deleteById(Long id) throws NameNotFoundException {
        if(flatsRepository.findById(id).isEmpty()){
            throw new NameNotFoundException("No such ID");
        }
        flatsRepository.deleteById(id);
    }

    public String testRetry2(boolean retry) {
        return testRetry(retry);
    }

    @Retryable(retryFor = IllegalArgumentException.class, maxAttempts = 3)
    public String testRetry(boolean retry) {
        if (retry) {
            throw new IllegalArgumentException("Земля плоская");
        }

        return "Земля круглая";
    }
}
