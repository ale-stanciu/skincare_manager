package com.example.skincare.service;

import com.example.skincare.model.Notificare;
import com.example.skincare.model.Produs;
import com.example.skincare.repository.NotificareRepository;
import com.example.skincare.repository.ProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificareService {

    @Autowired
    private NotificareRepository notificareRepository;

    @Autowired
    private ProdusRepository produsRepository;

    public List<Notificare> getNotificariByUserId(Long userId) {
        return notificareRepository.findByUserId(userId);
    }

    public Notificare saveNotificare(Long userId, String message) {
        Notificare notificare = new Notificare();
        notificare.setUserId(userId);
        notificare.setMessage(message);
        notificare.setTimestamp(LocalDateTime.now());
        return notificareRepository.save(notificare);
    }



    @Scheduled(cron = "0 0 0 * * ?") // Rulează zilnic la miezul nopții
    public void verificaProduse() {
        List<Produs> produse = produsRepository.findAll();
        for (Produs produs : produse) {
            LocalDate dataDeschidere = produs.getDataDeschidere();
            int valabilitate = produs.getValabilitate();
            LocalDate dataExpirarii = dataDeschidere.plusMonths(valabilitate);

            if (dataExpirarii.minusMonths(1).isEqual(LocalDate.now())) {
                String message = "Produsul " + produs.getNume() + " va expira în curând.";
                saveNotificare(produs.getUtilizator().getId(), message);
            }
        }
    }

    public void deleteNotificareById(Long id) {
        notificareRepository.deleteById(id);
    }

    public void deleteAllNotificariByUserId(Long userId) {
        List<Notificare> notificari = notificareRepository.findByUserId(userId);
        notificareRepository.deleteAll(notificari);
    }


}
