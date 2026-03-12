package com.example.grapes.admin.repository;

import com.example.grapes.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find admin by username and password
    Admin findByNameAndPassword(String name, String password);
}