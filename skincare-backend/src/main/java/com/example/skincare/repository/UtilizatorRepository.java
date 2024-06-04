package com.example.skincare.repository;

import com.example.skincare.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
    boolean existsByEmail(String email);
    Utilizator findByEmail(String email);
}