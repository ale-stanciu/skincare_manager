package com.example.skincare.model;

public class PrietenRequest {
    private Long id;
    private Long userId;
    private String email;

    // Constructori
    public PrietenRequest() {
    }

    public PrietenRequest(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    // Getteri și setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
