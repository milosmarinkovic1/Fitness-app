package com.fitnessapp.service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fitnessapp.dto.MaxRepsDTO;
import com.fitnessapp.dto.PersonalRecordDTO;
import com.fitnessapp.dto.SetRequestDTO;
import com.fitnessapp.dto.SetResponseDTO;
import com.fitnessapp.dto.WeightTrendDTO;
import com.fitnessapp.dto.WorkoutVolumeDTO;
import com.fitnessapp.model.Exercise;
import com.fitnessapp.model.User;
import com.fitnessapp.model.Workout;
import com.fitnessapp.repository.ExerciseRepository;
import com.fitnessapp.repository.SetRepository;
import com.fitnessapp.repository.UserRepository;
import com.fitnessapp.repository.WorkoutRepository;


@Service
public class SetService {
    
@Autowired
private SetRepository setRepository;

@Autowired
private WorkoutRepository workoutRepository;

@Autowired
private ExerciseRepository exerciseRepository;


public SetResponseDTO addSet(Long workoutId, SetRequestDTO request){

    Workout workout = workoutRepository.findById(workoutId).orElseThrow(
        () -> new RuntimeException("Workout nije pronađen sa ID: " + workoutId));

    //pretrazujemo request id sa getterom
    Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise nije pronađen sa ID: " + request.getExerciseId()));


    //mora puno ime klase , konflikt sa java.util.Set
    com.fitnessapp.model.Set set = new com.fitnessapp.model.Set();

    set.setWorkout(workout);
    set.setExercise(exercise);
    set.setSetNumber(request.getSetNumber());
    set.setReps(request.getReps());
    set.setWeight(request.getWeight());

    com.fitnessapp.model.Set savedSet = setRepository.save(set);

   SetResponseDTO dto = new SetResponseDTO();
    dto.setSetId(savedSet.getSetId());
    dto.setExerciseName(savedSet.getExercise().getName());
    dto.setSetNumber(savedSet.getSetNumber());
    dto.setReps(savedSet.getReps());
    dto.setWeight(savedSet.getWeight());

    return dto;
   
}

@Autowired
private UserRepository userRepository;

public List<PersonalRecordDTO> getPersonalRecords() {
    
    // 1. Uzmi email trenutnog korisnika
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    // 2. Pronađi korisnika
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

    // 3. Pozovi native SQL upit
    List<Object[]> results = setRepository.findPersonalRecordsByUserId(user.getUserId());

    // 4. Konvertuj svaki red u DTO
    List<PersonalRecordDTO> personalRecords = new ArrayList<>();
    
    for (Object[] row : results) {
        PersonalRecordDTO dto = new PersonalRecordDTO();
        dto.setExerciseName((String) row[0]);
        dto.setMaxWeight((BigDecimal) row[1]);
        
        dto.setAchievedDate(LocalDateTime.parse(
        row[2].toString().replace(" ", "T")
        ));
        //zameni razmak sa T pa 2026-08-06 11:37:39 
        // postaje 2026-08-06T11:37:39
        // što LocalDateTime može da parsira.

        personalRecords.add(dto);
    }

    return personalRecords;
}


public List<WeightTrendDTO> getWeightTrend() {
    
    // 1. Uzmi email trenutnog korisnika
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    // 2. Pronađi korisnika
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

    // 3. Pozovi native SQL upit
    List<Object[]> results = setRepository.findWeightTrendByUserId(user.getUserId());

    // 4. Konvertuj svaki red u DTO
    List<WeightTrendDTO> weightTrend = new ArrayList<>();
    
    for (Object[] row : results) {
        WeightTrendDTO dto = new WeightTrendDTO();
        dto.setExerciseName((String) row[0]);
        dto.setWorkoutDate(LocalDateTime.parse(row[1].toString().replace(" ", "T")));
        dto.setWeight((BigDecimal) row[2]);
        
        dto.setPreviousWeight((BigDecimal) row[3]); 
        // ako je null, setter će postaviti null
        
        // difference može biti NULL, ali smo u SQL-u koristili COALESCE pa je uvek broj
        dto.setDifference((BigDecimal) row[4]);
        
        weightTrend.add(dto);
    }

    return weightTrend;
}



public List<WorkoutVolumeDTO> getWorkoutVolume(){

    /*
    private Long workoutId;
    private LocalDateTime workoutDate;
    private BigDecimal totalVolume;
    */
    String email = SecurityContextHolder.getContext()
        .getAuthentication()
        .getName();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

    List<Object[]> results = setRepository.findWorkoutVolumeByUserId(user.getUserId());
    

    List<WorkoutVolumeDTO> workoutVolumeDTO =  new ArrayList<>();

    for(Object[] row : results){
        WorkoutVolumeDTO dto = new WorkoutVolumeDTO();
        dto.setWorkoutId( (Long) row[0]);
        dto.setWorkoutDate(LocalDateTime.parse(//Parsira string u LocalDateTime objekat
            row[1].toString().replace(" ", "T")));
            //2026-01-25T10:00:00
        dto.setTotalVolume((BigDecimal) row[2]);

        workoutVolumeDTO.add(dto);
    }
    return workoutVolumeDTO;
 
}


public List<MaxRepsDTO> getMaxReps() {
    
    // 1. Uzmi email trenutnog korisnika
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    // 2. Pronađi korisnika
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

    // 3. Pozovi native SQL upit
    List<Object[]> results = setRepository.findMaxRepsByUserId(user.getUserId());

    // 4. Konvertuj svaki red u DTO
    List<MaxRepsDTO> maxRepsList = new ArrayList<>();
    
    for (Object[] row : results) {
        MaxRepsDTO dto = new MaxRepsDTO();
        dto.setExerciseName((String) row[0]);
        dto.setMaxReps((Integer) row[1]);
        dto.setWeightAtMaxReps((BigDecimal) row[2]);
        
        maxRepsList.add(dto);
    }

    return maxRepsList;
}


}
