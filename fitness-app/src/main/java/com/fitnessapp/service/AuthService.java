package com.fitnessapp.service;

import com.fitnessapp.dto.AuthResponseDTO;
import com.fitnessapp.dto.LoginRequestDTO;
import com.fitnessapp.dto.RegisterRequestDTO;
import com.fitnessapp.model.User;
import com.fitnessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //biblioteka koja hešuje lozinku  — jednosmerni proces

    public AuthResponseDTO register(RegisterRequestDTO request) {
        // 1. Provjeri da li email već postoji
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email već postoji");
        }

        // 2. Kreiraj novog korisnika
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
                /*
                Uzima lozinku iz zahteva Pretvara je u heš pomoću 
                passwordEncoder.encode()
                Upisuje taj heš u korisnika (setPasswordHash)
                */
        user.setAccountStatus("ACTIVE");

        // 3. Sačuvaj u bazu
        userRepository.save(user);

        // 4. Generiši token i vrati ga
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        // 1. Pronađi korisnika po emailu
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Korisnik ne postoji"));

        // 2. Provjeri lozinku
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Pogrešna lozinka");
        }
        //passwordEncoder.matches(raw, hashed)
        //proverava da li se lozinka poklapa sa hešom, bez dekriptovanja.

        // 3. Generiši token i vrati ga
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponseDTO(token);
    }
}