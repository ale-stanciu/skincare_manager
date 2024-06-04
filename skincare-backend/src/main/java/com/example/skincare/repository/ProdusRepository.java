package com.example.skincare.repository;

import com.example.skincare.model.Produs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdusRepository extends JpaRepository<Produs, Long> {
    List<Produs> findByUtilizatorId(Long userId);
}
