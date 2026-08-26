package com.fitnessapp.dto;

import java.math.BigDecimal;

public class MaxRepsDTO {

    private String exerciseName;
    private Integer maxReps;
    private BigDecimal weightAtMaxReps;

    public MaxRepsDTO(){}

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getMaxReps() {
        return maxReps;
    }

    public void setMaxReps(Integer maxReps) {
        this.maxReps = maxReps;
    }

    public BigDecimal getWeightAtMaxReps() {
        return weightAtMaxReps;
    }

    public void setWeightAtMaxReps(BigDecimal weightAtMaxReps) {
        this.weightAtMaxReps = weightAtMaxReps;
    }

    
    
}
