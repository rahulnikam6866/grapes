package com.example.grapes.Repository;

import com.example.grapes.Model.Grapes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrapesRepository extends JpaRepository<Grapes, Long> {
    Grapes findByNameAndPassword(String name, String password);
}