package com.fitnessapp.controller;

import com.fitnessapp.dto.AuthResponseDTO;
import com.fitnessapp.dto.LoginRequestDTO;
import com.fitnessapp.dto.RegisterRequestDTO;
import com.fitnessapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

        //@PostMapping  prima POST zahteve.
        // Register i login su POST jer šaljemo podatke na server.

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }
        //@RequestBody — govori Springu da uzme JSON iz tela zahteva i
            //  pretvori ga u Java objekat automatski.
            //Bez njega, morao bi ručno da čitaš JSON i parsiraš ga

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}
