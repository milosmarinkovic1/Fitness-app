package com.fitnessapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WorkoutVolumeDTO {

    private Long workoutId;
    private LocalDateTime workoutDate;
    private BigDecimal totalVolume;

    public WorkoutVolumeDTO (){}

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public LocalDateTime getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDateTime workoutDate) {
        this.workoutDate = workoutDate;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    

    
    
}
