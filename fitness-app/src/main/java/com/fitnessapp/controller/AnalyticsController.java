package com.fitnessapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessapp.dto.MaxRepsDTO;
import com.fitnessapp.dto.PersonalRecordDTO;
import com.fitnessapp.dto.WeightTrendDTO;
import com.fitnessapp.dto.WorkoutVolumeDTO;
import com.fitnessapp.service.SetService;

    
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private SetService setService;

    @GetMapping("/personal-records")
    public List<PersonalRecordDTO> getPersonalRecords() {
        return setService.getPersonalRecords();
    }

    @GetMapping("/weight-trend")
    public List<WeightTrendDTO> getWeightTrend() {
        return setService.getWeightTrend();
    }
    
    @GetMapping("/workout-volume")
    public List<WorkoutVolumeDTO> getWorkoutVolume(){
        return setService.getWorkoutVolume();
    }

        @GetMapping("/max-reps")
    public ResponseEntity<List<MaxRepsDTO>> getMaxReps() {
        List<MaxRepsDTO> maxReps = setService.getMaxReps();
        return new ResponseEntity<>(maxReps, HttpStatus.OK);
    }
}



