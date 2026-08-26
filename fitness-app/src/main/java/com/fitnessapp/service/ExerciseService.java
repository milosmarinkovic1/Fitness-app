package com.fitnessapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitnessapp.dto.ExerciseResponseDTO;
import com.fitnessapp.model.Exercise;
import com.fitnessapp.repository.ExerciseRepository;

@Service 
public class ExerciseService {
    
@Autowired
private ExerciseRepository exerciseRepository;

public List<ExerciseResponseDTO> getAllExcercises (){

    List<Exercise> exercises = exerciseRepository.findAll();

    return exercises.stream().map( exercise -> {
        
        ExerciseResponseDTO dto = new ExerciseResponseDTO();

         dto.setExerciseId(exercise.getExerciseId());
         dto.setDescription(exercise.getDescription());
         dto.setCategoryName(exercise.getCategory().getName());//JPA automatski uradi JOIN
         dto.setName(exercise.getName());
        return dto;
    }).collect(Collectors.toList());

}

}
