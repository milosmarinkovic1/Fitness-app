package com.fitnessapp.controller;

import com.fitnessapp.dto.SetRequestDTO;
import com.fitnessapp.dto.SetResponseDTO;
import com.fitnessapp.dto.WorkoutRequestDTO;
import com.fitnessapp.dto.WorkoutResponseDTO;
import com.fitnessapp.service.SetService;
import com.fitnessapp.service.WorkoutService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    // CREATE - Kreiranje novog treninga
    @PostMapping
    public ResponseEntity<WorkoutResponseDTO> createWorkout(
            @Valid @RequestBody WorkoutRequestDTO request) {
        
        WorkoutResponseDTO response = workoutService.createWorkout(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
    }

    @Autowired
    private SetService setService;

    @PostMapping("/{workoutId}/sets")
    public ResponseEntity<SetResponseDTO> addSet(
    //@PathVariable — izvlači vrednost iz URL-a i ubacuje je kao parametar metode
            @PathVariable Long workoutId,
          @Valid  @RequestBody SetRequestDTO request) {
        
        SetResponseDTO response = setService.addSet(workoutId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<WorkoutResponseDTO> getMyWorkouts() {
        return workoutService.getMyWorkouts();
    }
}