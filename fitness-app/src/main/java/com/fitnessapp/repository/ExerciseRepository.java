
package com.fitnessapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitnessapp.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

    Optional<Exercise> findByName (String name);
}