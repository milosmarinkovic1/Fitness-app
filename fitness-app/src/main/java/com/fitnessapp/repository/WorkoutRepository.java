package com.fitnessapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitnessapp.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {

    List<Workout> findByUserUserId(Long userId);

// Ovo pronalazi sve treninge jednog korisnika   
//Spring čita kao: findBy → User (polje u Workout) → UserId (polje u User)
    
}