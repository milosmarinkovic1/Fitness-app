package com.fitnessapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class WorkoutRequestDTO {

    @Min(1) @Max(5)
    private Integer feeling;

    private String note;

    @Min(1)
    private Integer duration;

    public WorkoutRequestDTO (){}

    public Integer getFeeling() {
        return feeling;
    }

    public void setFeeling(Integer feeling) {
        this.feeling = feeling;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    
}
