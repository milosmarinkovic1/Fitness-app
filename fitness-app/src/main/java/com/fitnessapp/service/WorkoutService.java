package com.fitnessapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.fitnessapp.dto.WorkoutRequestDTO;
import com.fitnessapp.dto.WorkoutResponseDTO;
import com.fitnessapp.model.User;
import com.fitnessapp.model.Workout;
import com.fitnessapp.repository.UserRepository;
import com.fitnessapp.repository.WorkoutRepository;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    
    public WorkoutResponseDTO createWorkout(WorkoutRequestDTO request) {
    
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

    Workout workout = new Workout(); 
    workout.setUser(user);
    workout.setFeeling(request.getFeeling());
    workout.setNote(request.getNote());
    workout.setDuration(request.getDuration());
    workout.setWorkoutDate(LocalDateTime.now());
    
    workoutRepository.save(workout);

    WorkoutResponseDTO dto = new WorkoutResponseDTO();
    dto.setDuration(workout.getDuration());
    dto.setFeeling(workout.getFeeling());
    dto.setNote(workout.getNote());
    dto.setWorkoutDate(workout.getWorkoutDate());
    dto.setWorkoutId(workout.getWorkoutId());
    dto.setUserFullName(user.getFullName());
        
    return dto;
}

public List<WorkoutResponseDTO> getMyWorkouts() {
    
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

    List<Workout> workouts = workoutRepository.findByUserUserId(user.getUserId());

    return workouts.stream().map(workout -> {
        WorkoutResponseDTO dto = new WorkoutResponseDTO();
        dto.setWorkoutId(workout.getWorkoutId());
        dto.setWorkoutDate(workout.getWorkoutDate());
        dto.setDuration(workout.getDuration());
        dto.setFeeling(workout.getFeeling());
        dto.setNote(workout.getNote());
        dto.setUserFullName(workout.getUser().getFullName());
        return dto;
    }).collect(Collectors.toList());
}

}