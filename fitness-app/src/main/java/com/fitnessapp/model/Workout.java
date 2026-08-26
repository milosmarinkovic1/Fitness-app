package com.fitnessapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long workoutId;

    /* 
    @ManyToOne //jedan plan može biti korišćen za više treninga
    @JoinColumn(name = "plan_id", nullable = true)
    private Plan plan;
    */
    @Column(name = "plan_id")
    private Long planId;

    @ManyToOne // Jedan korisnik može imati mnogo treninga
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(name = "workout_date")
    private LocalDateTime workoutDate;

    @Column(name = "duration")
    private Integer duration;
 
    @Column(name = "feeling")
    private Integer feeling;

    @Column(name = "note")
    private String note;

    public Workout(){}

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDateTime workoutDate) {
        this.workoutDate = workoutDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
}
