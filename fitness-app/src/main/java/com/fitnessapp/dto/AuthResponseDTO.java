package com.fitnessapp.dto;

public class AuthResponseDTO {  
    //šta vraćamo korisniku nakon uspešnog 
    // login/register (samo token)
    private String token;

    public AuthResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}