package com.example.grapes.admin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.grapes.Model.Admin;
import com.example.grapes.admin.repository.AdminRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AdminInitializer {

    @Autowired
    private AdminRepository adminRepository;

    @PostConstruct
    public void createAdmin() {

        if(adminRepository.count() == 0) {

            Admin admin = new Admin();
            admin.setId(1L);
            admin.setName("Rahul Nikam");
            admin.setPassword("admin123");

            adminRepository.save(admin);

            System.out.println("Default Admin Created");
        }
    }
}