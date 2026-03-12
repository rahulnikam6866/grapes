package com.example.grapes.Service;

import com.example.grapes.Model.Grapes;
import com.example.grapes.Repository.GrapesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrapesService {

    @Autowired
    private GrapesRepository repository;

    // Register new user
    public Grapes register(Grapes grapes) {
        return repository.save(grapes);
    }

    // Login user by name and password
    public Grapes login(String name, String password) {
        return repository.findByNameAndPassword(name, password);
    }
}