package com.example.skincare.service;

import com.example.skincare.model.Produs;
import com.example.skincare.model.Rutina;
import com.example.skincare.repository.ProdusRepository;
import com.example.skincare.repository.RutinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutinaService {

    @Autowired
    private RutinaRepository rutinaRepository;
    @Autowired
    private ProdusRepository produsRepository;

    public List<Rutina> getRutinaByUtilizatorId(Long utilizatorId) {
        return rutinaRepository.findByUtilizatorId(utilizatorId);
    }

    public Rutina saveRutina(Rutina rutina) {

        return rutinaRepository.save(rutina);
    }

    public Optional<Produs> getProdusById(Long id) {

        return produsRepository.findById(id);
    }
}


