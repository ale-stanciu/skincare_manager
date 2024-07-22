package com.example.skincare.controller;

import com.example.skincare.model.Utilizator;
import com.example.skincare.repository.UtilizatorRepository;
import com.example.skincare.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UtilizatorController {

    @Autowired
    private UtilizatorRepository utilizatorRepository;
    private UtilizatorService utilizatorService;

    @PostMapping("/register")
    public Response register(@RequestBody Utilizator utilizator) {
        if (utilizatorRepository.existsByEmail(utilizator.getEmail())) {
            return new Response(false, "Email deja folosit");
        }
        utilizatorRepository.save(utilizator);
        return new Response(true, "Înregistrare reușită");
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable Long userId) {
        Optional<Utilizator> utilizator = utilizatorService.getUtilizatorById(userId);
        if (utilizator.isPresent()) {
            return ResponseEntity.ok(utilizator.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Utilizator utilizator) {
        Utilizator existent = utilizatorRepository.findByEmail(utilizator.getEmail());
        if (existent != null && existent.getParola().equals(utilizator.getParola())) {
            return ResponseEntity.ok(new LoginResponse(true, "Autentificare reușită", existent.getId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Email sau parolă incorectă", null));
    }

    class Response {
        private boolean success;
        private String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters și setters
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    class LoginResponse extends Response {
        private Long id;

        public LoginResponse(boolean success, String message, Long id) {
            super(success, message);
            this.id = id;
        }

        // Getter și setter pentru id
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
