package com.example.skincare.repository;

import com.example.skincare.model.Notificare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificareRepository extends JpaRepository<Notificare, Long> {
    List<Notificare> findByUserId(Long userId);
}
