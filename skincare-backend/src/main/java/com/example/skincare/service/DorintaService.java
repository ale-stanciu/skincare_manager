package com.example.skincare.service;

import com.example.skincare.model.Dorinta;
import com.example.skincare.repository.DorintaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DorintaService {

    @Autowired
    private DorintaRepository dorintaRepository;

    public Dorinta saveDorinta(Dorinta dorinta) {
        return dorintaRepository.save(dorinta);
    }

    public List<Dorinta> getDorinteByUtilizatorId(Long userId) {
        return dorintaRepository.findByUtilizatorId(userId);
    }

    public void deleteDorinta(Long id) {
        dorintaRepository.deleteById(id);
    }

    public Dorinta getDorintaById(Long id) {
        return dorintaRepository.findById(id).orElse(null);
    }
}
