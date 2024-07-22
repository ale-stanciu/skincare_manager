package com.example.skincare.controller;

import com.example.skincare.model.Notificare;
import com.example.skincare.service.NotificareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificareController {

    @Autowired
    private NotificareService notificareService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notificare>> getNotificariByUserId(@PathVariable Long userId) {
        List<Notificare> notificari = notificareService.getNotificariByUserId(userId);
        return ResponseEntity.ok(notificari);
    }

    @PostMapping("/add")
    public ResponseEntity<Notificare> addNotificare(@RequestBody Notificare notificare) {
        Notificare savedNotificare = notificareService.saveNotificare(notificare.getUserId(), notificare.getMessage());
        return ResponseEntity.ok(savedNotificare);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificare(@PathVariable Long id) {
        notificareService.deleteNotificareById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all/{userId}")
    public ResponseEntity<Void> deleteAllNotificari(@PathVariable Long userId) {
        notificareService.deleteAllNotificariByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}

