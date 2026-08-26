package com.fitnessapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessapp.dto.ExerciseResponseDTO;
import com.fitnessapp.service.ExerciseService;

@RestController //klasa vraća JSON odgovore
@RequestMapping("/api/exercises")

public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseResponseDTO> getAllExercises(){
        return exerciseService.getAllExcercises();
    }
    
}
