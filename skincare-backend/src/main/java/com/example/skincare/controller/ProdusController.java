package com.example.skincare.controller;

import com.example.skincare.model.Produs;
import com.example.skincare.model.Utilizator;
import com.example.skincare.service.ProdusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/produse")
public class ProdusController {

    @Autowired
    private ProdusService produsService;

    @PostMapping("/add")
    public ResponseEntity<Produs> addProdus(
            @RequestParam("nume") String nume,
            @RequestParam("firma") String firma,
            @RequestParam("nuanta") String nuanta,
            @RequestParam("dataDeschidere") String dataDeschidere,
            @RequestParam("valabilitate") String valabilitate,
            @RequestParam("alteDetalii") String alteDetalii,
            @RequestParam("poza") MultipartFile poza,
            @RequestParam("utilizatorId") Long utilizatorId) {

        System.out.println("Received request to add product:");
        System.out.println("nume: " + nume);
        System.out.println("firma: " + firma);
        System.out.println("nuanta: " + nuanta);
        System.out.println("dataDeschidere: " + dataDeschidere);
        System.out.println("valabilitate: " + valabilitate);
        System.out.println("alteDetalii: " + alteDetalii);
        System.out.println("poza: " + (poza != null ? poza.getOriginalFilename() : "No file"));
        System.out.println("utilizatorId: " + utilizatorId);

        try {
            int valabilitateInt = Integer.parseInt(valabilitate.replace("'", ""));
            LocalDate dataDeschidereLD = LocalDate.parse(dataDeschidere.replace("'", "") + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Produs produs = new Produs();
            produs.setNume(nume);
            produs.setFirma(firma);
            produs.setNuanta(nuanta);
            produs.setDataDeschidere(dataDeschidereLD);
            produs.setValabilitate(valabilitateInt);
            produs.setAlteDetalii(alteDetalii);

            Utilizator utilizator = new Utilizator();
            utilizator.setId(utilizatorId);
            produs.setUtilizator(utilizator);

            if (poza != null && !poza.isEmpty()) {
                byte[] bytes = poza.getBytes();
                Path directory = Paths.get("path/to/upload/directory");
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }
                Path path = directory.resolve(poza.getOriginalFilename());
                Files.write(path, bytes);
                produs.setPoza(poza.getOriginalFilename());
            }

            produsService.saveProdus(produs);
            System.out.println("Produs salvat cu succes: " + produs);
            return ResponseEntity.ok(produs);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for valabilitate: " + valabilitate);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Produs>> getAllProduse(@PathVariable Long userId) {
        List<Produs> produse = produsService.getProduseByUtilizatorId(userId);
        return ResponseEntity.ok(produse);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProdus(@PathVariable Long id) {
        produsService.deleteProdus(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Produs> updateProdus(
            @PathVariable Long id,
            @RequestParam("nume") String nume,
            @RequestParam("firma") String firma,
            @RequestParam("nuanta") String nuanta,
            @RequestParam("dataDeschidere") String dataDeschidere,
            @RequestParam("valabilitate") String valabilitate,
            @RequestParam("alteDetalii") String alteDetalii,
            @RequestParam(value = "poza", required = false) MultipartFile poza) {

        try {
            Produs produs = produsService.getProdusById(id);
            if (produs == null) {
                return ResponseEntity.notFound().build();
            }

            produs.setNume(nume);
            produs.setFirma(firma);
            produs.setNuanta(nuanta);
            produs.setDataDeschidere(LocalDate.parse(dataDeschidere.replace("'", "") + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            produs.setValabilitate(Integer.parseInt(valabilitate.replace("'", "")));
            produs.setAlteDetalii(alteDetalii);

            if (poza != null && !poza.isEmpty()) {
                byte[] bytes = poza.getBytes();
                Path directory = Paths.get("path/to/upload/directory");
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }
                Path path = directory.resolve(poza.getOriginalFilename());
                Files.write(path, bytes);
                produs.setPoza(poza.getOriginalFilename());
            }

            produsService.saveProdus(produs);
            return ResponseEntity.ok(produs);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Produs>> getProduseByUserId(@PathVariable Long userId) {
        List<Produs> produse = produsService.getProduseByUtilizatorId(userId);
        return ResponseEntity.ok(produse);
    }


}
