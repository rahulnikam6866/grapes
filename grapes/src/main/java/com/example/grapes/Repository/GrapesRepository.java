package com.example.grapes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.grapes.Model.Grapes;

public interface GrapesRepository extends JpaRepository<Grapes, Long> {

    Grapes findByNameAndPassword(String name, String password);
}
