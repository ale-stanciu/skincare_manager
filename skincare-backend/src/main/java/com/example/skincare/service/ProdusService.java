package com.example.skincare.service;

import com.example.skincare.model.Produs;
import com.example.skincare.repository.ProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdusService {

    @Autowired
    private ProdusRepository produsRepository;

    public Produs saveProdus(Produs produs) {
        return produsRepository.save(produs);
    }

    public List<Produs> getAllProduse() {
        return produsRepository.findAll();
    }

    public void deleteProdus(Long id) {
        produsRepository.deleteById(id);
    }

    public Produs getProdusById(Long id) {
        return produsRepository.findById(id).orElse(null);
    }

    public List<Produs> getProduseByUtilizatorId(Long userId) {
        return produsRepository.findByUtilizatorId(userId);
    }


}
