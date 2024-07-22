package com.example.skincare.repository;

import com.example.skincare.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    List<Rutina> findByUtilizatorId(Long utilizatorId);

}
