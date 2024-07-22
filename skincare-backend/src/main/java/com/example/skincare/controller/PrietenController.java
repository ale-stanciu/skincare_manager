package com.example.skincare.controller;

import com.example.skincare.model.Prieten;
import com.example.skincare.model.PrietenRequest;
import com.example.skincare.model.Utilizator;
import com.example.skincare.service.PrietenService;
import com.example.skincare.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prieteni")
public class PrietenController {

    @Autowired
    private PrietenService prietenService;

    @Autowired
    private UtilizatorService utilizatorService;

    @PostMapping("/add")
    public ResponseEntity<Prieten> addPrieten(@RequestBody PrietenRequest request) {
        Utilizator utilizator = utilizatorService.getUtilizatorByEmail(request.getEmail());
        if (utilizator != null) {
            Prieten prieten = new Prieten();
            prieten.setUser1Id(request.getUserId());
            prieten.setUser2Id(utilizator.getId());
            prieten.setAccepted(false);
            prietenService.savePrieten(prieten);
            return ResponseEntity.ok(prieten);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/requests/{userId}")
    public ResponseEntity<List<PrietenRequest>> getAllFriendRequests(@PathVariable Long userId) {
        List<PrietenRequest> friendRequests = prietenService.getFriendRequestsByUserId(userId);
        return ResponseEntity.ok(friendRequests);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Utilizator>> getAllFriends(@PathVariable Long userId) {
        List<Utilizator> friends = prietenService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friends);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long id) {
        prietenService.acceptFriendRequest(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<Void> rejectFriendRequest(@PathVariable Long id) {
        prietenService.deletePrieten(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/{userId1}/{userId2}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long userId1, @PathVariable Long userId2) {
        prietenService.deleteFriendship(userId1, userId2);
        return ResponseEntity.noContent().build();
    }
}
