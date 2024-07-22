package com.example.skincare.repository;

import com.example.skincare.model.Prieten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrietenRepository extends JpaRepository<Prieten, Long> {
    List<Prieten> findByUser1Id(Long userId);
    List<Prieten> findByUser1IdAndAccepted(Long userId, boolean accepted);
    List<Prieten> findByUser2IdAndAccepted(Long userId, boolean accepted);
    List<Prieten> findByUser2IdAndAcceptedFalse(Long userId);
    List<Prieten> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}
