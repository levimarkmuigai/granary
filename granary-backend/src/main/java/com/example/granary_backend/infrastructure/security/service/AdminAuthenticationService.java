package com.example.granary_backend.infrastructure.security.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.granary_backend.infrastructure.security.repository.AdminJpaRepository;

@Service
public class AdminAuthenticationService implements UserDetailsService {

    private final AdminJpaRepository adminJpaRepository;

    public AdminAuthenticationService(AdminJpaRepository adminJpaRepository) {
        this.adminJpaRepository = adminJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Admin not found with username: " + username));
    }
}
