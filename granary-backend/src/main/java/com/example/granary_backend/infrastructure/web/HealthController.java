package com.example.granary_backend.infrastructure.web;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.PersistenceContext;

import jakarta.persistence.EntityManager;

import java.util.HashMap;

import java.util.Map;

@RestController
public class HealthController {

    @PersistenceContext
    private EntityManager entityManager;
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");

        try{
            entityManager.createNativeQuery("Select 1").getSingleResult();
            status.put("db", "Up");
        }catch(Exception e){
            status.put("db", "DOWN");
        }

        return status;
    }
}
