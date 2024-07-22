package com.example.skincare.service;

import com.example.skincare.model.Notificare;
import com.example.skincare.model.Prieten;
import com.example.skincare.model.PrietenRequest;
import com.example.skincare.model.Utilizator;
import com.example.skincare.repository.PrietenRepository;
import com.example.skincare.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrietenService {

    @Autowired
    private PrietenRepository prietenRepository;
    @Autowired
    private UtilizatorRepository utilizatorRepository;

    public Prieten savePrieten(Prieten prieten) {
        Prieten savedPrieten = prietenRepository.save(prieten);

        // Trimite notificare utilizatorului care primește cererea de prietenie
        Utilizator utilizator = utilizatorRepository.findById(prieten.getUser1Id()).orElse(null);
        if (utilizator != null) {
            String mesaj = "Ai primit o cerere de prietenie de la " + utilizator.getEmail() + "!";
            notificareService.saveNotificare(prieten.getUser2Id(), mesaj);
        }

        return savedPrieten;
    }

    public List<Prieten> getPrieteni(Long userId) {
        return prietenRepository.findByUser1Id(userId);
    }

    public List<Prieten> getFriendRequests(Long userId) {
        return prietenRepository.findByUser2IdAndAcceptedFalse(userId);
    }

    public Optional<Prieten> getPrietenById(Long id) {
        return prietenRepository.findById(id);
    }

    public void deletePrieten(Long id) {
        prietenRepository.deleteById(id);
    }

    public List<PrietenRequest> getFriendRequestsByUserId(Long userId) {
        List<Prieten> prieteni = prietenRepository.findByUser2IdAndAccepted(userId, false);
        return prieteni.stream().map(prieten -> {
            Utilizator utilizator = utilizatorRepository.findById(prieten.getUser1Id()).orElse(null);
            return new PrietenRequest(prieten.getId(), utilizator != null ? utilizator.getEmail() : null);
        }).collect(Collectors.toList());
    }

    public List<Utilizator> getFriendsByUserId(Long userId) {
        List<Prieten> prieteni1 = prietenRepository.findByUser1IdAndAccepted(userId, true);
        List<Prieten> prieteni2 = prietenRepository.findByUser2IdAndAccepted(userId, true);
        List<Utilizator> friends = new ArrayList<>();

        for (Prieten prieten : prieteni1) {
            utilizatorRepository.findById(prieten.getUser2Id()).ifPresent(friends::add);
        }

        for (Prieten prieten : prieteni2) {
            utilizatorRepository.findById(prieten.getUser1Id()).ifPresent(friends::add);
        }

        return friends;
    }

    public void acceptFriendRequest(Long requestId) {
        Optional<Prieten> prietenOpt = prietenRepository.findById(requestId);
        if (prietenOpt.isPresent()) {
            Prieten prieten = prietenOpt.get();
            prieten.setAccepted(true);
            prietenRepository.save(prieten);
        }
    }

    public void deleteFriendship(Long userId1, Long userId2) {
        List<Prieten> prieteni1 = prietenRepository.findByUser1IdAndUser2Id(userId1, userId2);
        List<Prieten> prieteni2 = prietenRepository.findByUser1IdAndUser2Id(userId2, userId1);

        prieteni1.forEach(prieten -> prietenRepository.deleteById(prieten.getId()));
        prieteni2.forEach(prieten -> prietenRepository.deleteById(prieten.getId()));
    }

    @Autowired
    private NotificareService notificareService;



}
