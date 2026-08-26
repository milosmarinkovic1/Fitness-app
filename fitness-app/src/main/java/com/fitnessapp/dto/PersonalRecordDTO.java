package com.fitnessapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PersonalRecordDTO {

    private String exerciseName;
    private BigDecimal maxWeight;
    private LocalDateTime achievedDate; 
    
    public PersonalRecordDTO (){}

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public BigDecimal getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(BigDecimal maxWeight) {
        this.maxWeight = maxWeight;
    }

    public LocalDateTime getAchievedDate() {
        return achievedDate;
    }

    public void setAchievedDate(LocalDateTime achievedDate) {
        this.achievedDate = achievedDate;
    }

    

    
}
