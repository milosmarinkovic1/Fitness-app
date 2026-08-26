
package com.fitnessapp.dto;

import java.time.LocalDateTime;

/*
CREATE TABLE workouts (
    workout_id        SERIAL PRIMARY KEY,
    user_id           INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    plan_id           INTEGER NULL REFERENCES plans(plan_id) ON DELETE SET NULL,
    workout_date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    duration          INTEGER,
    feeling           INTEGER CHECK (feeling BETWEEN 1 AND 5),
    note              VARCHAR(500)
);
*/

public class WorkoutResponseDTO {
    private Long workoutId;
    private LocalDateTime workoutDate;
    private Integer feeling;
    private String note;
    private Integer duration;
    private String userFullName; //ime korisnika (kao categoryName kod Exercise)


    public WorkoutResponseDTO(){}


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


    public String getUserFullName() {
        return userFullName;
    }


    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }


    

    


}