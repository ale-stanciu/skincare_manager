package com.example.skincare.service;

import com.example.skincare.model.Utilizator;
import com.example.skincare.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {
    @Autowired
    private UtilizatorRepository utilizatorRepository;

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
}
