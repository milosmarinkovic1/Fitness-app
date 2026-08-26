package com.fitnessapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessapp.dto.UserResponseDTO;
import com.fitnessapp.service.UserService;

@RestController  //odgovara na web zahteve ,vraća JSON odgovore automatski.
@RequestMapping("/api/users") //definiše osnovnu putanju za sve 
// endpoint-e u ovoj klasi

public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping   //Ova metoda hvata GET zahteve
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers(); // metoda iz user service
        //@RestController automatski pretvara tu listu u JSON.
    }

    @GetMapping("/me")
    public UserResponseDTO getCurrentUser() {
        return userService.getCurrentUser();
    }


}
