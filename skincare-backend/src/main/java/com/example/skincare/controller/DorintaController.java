package com.example.skincare.controller;

import com.example.skincare.model.Dorinta;
import com.example.skincare.model.Utilizator;
import com.example.skincare.service.DorintaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/dorinte")
public class DorintaController {

    @Autowired
    private DorintaService dorintaService;

    @PostMapping("/add")
    public ResponseEntity<Dorinta> addDorinta(
            @RequestParam("nume") String nume,
            @RequestParam("firma") String firma,
            @RequestParam("nuanta") String nuanta,
            @RequestParam("alteDetalii") String alteDetalii,
            @RequestParam("poza") MultipartFile poza,
            @RequestParam("utilizatorId") Long utilizatorId) {

        try {
            Dorinta dorinta = new Dorinta();
            dorinta.setNume(nume);
            dorinta.setFirma(firma);
            dorinta.setNuanta(nuanta);
            dorinta.setAlteDetalii(alteDetalii);

            Utilizator utilizator = new Utilizator();
            utilizator.setId(utilizatorId);
            dorinta.setUtilizator(utilizator);

            if (poza != null && !poza.isEmpty()) {
                byte[] bytes = poza.getBytes();
                Path directory = Paths.get("path/to/upload/directory");
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }
                Path path = directory.resolve(poza.getOriginalFilename());
                Files.write(path, bytes);
                dorinta.setPoza(poza.getOriginalFilename());
            }

            dorintaService.saveDorinta(dorinta);
            return ResponseEntity.ok(dorinta);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Dorinta>> getAllDorinte(@PathVariable Long userId) {
        List<Dorinta> dorinte = dorintaService.getDorinteByUtilizatorId(userId);
        return ResponseEntity.ok(dorinte);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDorinta(@PathVariable Long id) {
        dorintaService.deleteDorinta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Dorinta> updateDorinta(
            @PathVariable Long id,
            @RequestParam("nume") String nume,
            @RequestParam("firma") String firma,
            @RequestParam("nuanta") String nuanta,
            @RequestParam("alteDetalii") String alteDetalii,
            @RequestParam(value = "poza", required = false) MultipartFile poza) {

        try {
            Dorinta dorinta = dorintaService.getDorintaById(id);
            if (dorinta == null) {
                return ResponseEntity.notFound().build();
            }

            dorinta.setNume(nume);
            dorinta.setFirma(firma);
            dorinta.setNuanta(nuanta);
            dorinta.setAlteDetalii(alteDetalii);

            if (poza != null && !poza.isEmpty()) {
                byte[] bytes = poza.getBytes();
                Path directory = Paths.get("path/to/upload/directory");
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }
                Path path = directory.resolve(poza.getOriginalFilename());
                Files.write(path, bytes);
                dorinta.setPoza(poza.getOriginalFilename());
            }

            dorintaService.saveDorinta(dorinta);
            return ResponseEntity.ok(dorinta);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
