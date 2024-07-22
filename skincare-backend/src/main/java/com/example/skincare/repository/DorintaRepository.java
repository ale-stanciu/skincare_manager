package com.example.skincare.repository;

import com.example.skincare.model.Dorinta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DorintaRepository extends JpaRepository<Dorinta, Long> {
    List<Dorinta> findByUtilizatorId(Long userId);
}
