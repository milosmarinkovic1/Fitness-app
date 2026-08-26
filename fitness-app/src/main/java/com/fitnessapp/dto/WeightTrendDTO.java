package com.fitnessapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WeightTrendDTO {

    private String exerciseName;
    private LocalDateTime workoutDate;
    private BigDecimal weight;
    private BigDecimal previousWeight;
    private BigDecimal difference;

    public WeightTrendDTO (){}

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public LocalDateTime getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDateTime workoutDate) {
        this.workoutDate = workoutDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPreviousWeight() {
        return previousWeight;
    }

    public void setPreviousWeight(BigDecimal previousWeight) {
        this.previousWeight = previousWeight;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    

}
