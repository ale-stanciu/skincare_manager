package com.example.skincare.service;

import com.example.skincare.model.Prieten;
import com.example.skincare.model.Utilizator;
import com.example.skincare.repository.PrietenRepository;
import com.example.skincare.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {
    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private PrietenRepository prietenRepository;

    public List<Utilizator> getAllUtilizatori() {
        return utilizatorRepository.findAll();
    }

    public Optional<Utilizator> getUtilizatorById(Long id) {
        return utilizatorRepository.findById(id);
    }

    public Utilizator saveUtilizator(Utilizator utilizator) {
        return utilizatorRepository.save(utilizator);
    }

    public void deleteUtilizator(Long id) {
        utilizatorRepository.deleteById(id);
    }
    public Utilizator getUtilizatorByEmail(String email) {
        return utilizatorRepository.findByEmail(email);
    }

    public List<Utilizator> getPrieteni(Long userId) {
        List<Prieten> prietenii1 = prietenRepository.findByUser1IdAndAccepted(userId, true);
        List<Prieten> prietenii2 = prietenRepository.findByUser2IdAndAccepted(userId, true);
        List<Utilizator> prieteni = new ArrayList<>();

        for (Prieten prietenie : prietenii1) {
            utilizatorRepository.findById(prietenie.getUser2Id()).ifPresent(prieteni::add);
        }

        for (Prieten prietenie : prietenii2) {
            utilizatorRepository.findById(prietenie.getUser1Id()).ifPresent(prieteni::add);
        }

        return prieteni;
    }
}
