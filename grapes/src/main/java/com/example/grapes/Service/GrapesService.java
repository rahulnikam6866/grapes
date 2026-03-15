package com.example.grapes.Service;

import org.springframework.stereotype.Service;
import com.example.grapes.Model.Grapes;
import com.example.grapes.Repository.GrapesRepository;

@Service
public class GrapesService {

    private final GrapesRepository repository;

    public GrapesService(GrapesRepository repository) {
        this.repository = repository;
    }

    // Register user
    public void register(Grapes grapes) {
        repository.save(grapes);
    }

    // Login user
    public Grapes login(String name, String password) {
        return repository.findByNameAndPassword(name, password);
    }
}
