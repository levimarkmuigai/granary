package com.example.granary_backend.infrastructure.web.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping("/dashboard-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getDashboardSummary() {

        return ResponseEntity.ok("Welcome, Admin! This is highly sensitive dashboard data.");
    }

}