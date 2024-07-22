package com.example.skincare.controller;

import com.example.skincare.model.*;
import com.example.skincare.service.ProdusService;
import com.example.skincare.service.RutinaService;
import com.example.skincare.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rutina")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @Autowired
    private UtilizatorService utilizatorService;

    @Autowired
    private ProdusService produsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<RutinaDTO> getRutinaByUserId(@PathVariable Long userId) {
        List<Rutina> rutine = rutinaService.getRutinaByUtilizatorId(userId);
        if (rutine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Rutina rutina = rutine.get(0); // Assuming one routine per user
        RutinaDTO rutinaDTO = new RutinaDTO();
        rutinaDTO.setProduseDimineata(rutina.getProduseDimineata());
        rutinaDTO.setProduseSeara(rutina.getProduseSeara());

        return ResponseEntity.ok(rutinaDTO);
    }


    @PostMapping("/add")
    public ResponseEntity<Rutina> addProdusToRutina(@RequestBody RutinaRequest request) {
        Optional<Utilizator> utilizatorOpt = utilizatorService.getUtilizatorById(request.getUtilizatorId());
        if (utilizatorOpt.isPresent()) {
            Utilizator utilizator = utilizatorOpt.get();
            System.out.println("User found: " + utilizator.getEmail());
            List<Rutina> rutinaList = rutinaService.getRutinaByUtilizatorId(utilizator.getId());
            Rutina rutina;
            if (rutinaList.isEmpty()) {
                rutina = new Rutina();
                rutina.setUtilizatorId(utilizator.getId());
            } else {
                rutina = rutinaList.get(0); // Assuming one routine per user
            }

            Produs produs = produsService.getProdusById(request.getProdusId());
            if (produs == null) {
                System.out.println("Product not found with ID: " + request.getProdusId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if ("Dimineața".equals(request.getTip())) {
                rutina.getProduseDimineata().add(produs);
            } else {
                rutina.getProduseSeara().add(produs);
            }

            rutinaService.saveRutina(rutina);
            System.out.println("Product added to routine successfully");
            return ResponseEntity.ok(rutina);
        } else {
            System.out.println("User not found with ID: " + request.getUtilizatorId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Rutina> deleteProdusFromRutina(@RequestBody RutinaRequest request) {
        System.out.println("Request received to delete product from routine");
        Optional<Utilizator> utilizatorOpt = utilizatorService.getUtilizatorById(request.getUtilizatorId());
        if (utilizatorOpt.isPresent()) {
            Utilizator utilizator = utilizatorOpt.get();
            List<Rutina> rutinaList = rutinaService.getRutinaByUtilizatorId(utilizator.getId());
            if (rutinaList.isEmpty()) {
                System.out.println("No routine found for user");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Rutina rutina = rutinaList.get(0); // Assuming one routine per user

            Produs produs = produsService.getProdusById(request.getProdusId());
            if (produs == null) {
                System.out.println("Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if ("Dimineața".equals(request.getTip())) {
                rutina.getProduseDimineata().remove(produs);
            } else {
                rutina.getProduseSeara().remove(produs);
            }

            System.out.println("Product removed from routine, saving updated routine");
            Rutina updatedRutina = rutinaService.saveRutina(rutina);
            return ResponseEntity.ok(updatedRutina);
        } else {
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getRutinaPrieteni(@PathVariable Long userId) {
        List<Utilizator> prieteni = utilizatorService.getPrieteni(userId);
        List<Map<String, Object>> rutinePrieteni = new ArrayList<>();

        for (Utilizator prieten : prieteni) {
            List<Rutina> rutine = rutinaService.getRutinaByUtilizatorId(prieten.getId());
            for (Rutina rutina : rutine) {
                Map<String, Object> rutinaMap = new HashMap<>();
                rutinaMap.put("utilizator", prieten); // Include utilizatorul în răspuns
                rutinaMap.put("rutina", rutina);
                rutinePrieteni.add(rutinaMap);
            }
        }

        return ResponseEntity.ok(rutinePrieteni);
    }






}
