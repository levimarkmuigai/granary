package com.example.granary_backend.infrastructure.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.granary_backend.infrastructure.security.entity.AdminJpaEntity;

public interface AdminJpaRepository extends JpaRepository<AdminJpaEntity, Long> {

    Optional<AdminJpaEntity> findByUsername(String username);
}
