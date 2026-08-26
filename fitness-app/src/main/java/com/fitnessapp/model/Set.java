package com.fitnessapp.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/*
 set_id            SERIAL PRIMARY KEY,
    workout_id        INTEGER NOT NULL REFERENCES workouts(workout_id) ON DELETE CASCADE,
    exercise_id       INTEGER NOT NULL REFERENCES exercises(exercise_id) ON DELETE RESTRICT,
    set_number        INTEGER NOT NULL,
    weight            NUMERIC(5,2),
    reps              INTEGER,
    CONSTRAINT unique_set_per_workout_exercise UNIQUE (workout_id, exercise_id, set_number)
);
*/

@Entity
@Table(name = "sets")
public class Set {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "set_id")
private Long setId;
    
@ManyToOne //Ista vežba može biti u mnogo setova
@JoinColumn(name = "exercise_id", nullable = false)
private Exercise exercise;

@ManyToOne
@JoinColumn(name = "workout_id", nullable = false)
private Workout workout;

@NotNull
@Column(name = "set_number")
private Integer setNumber;

@Column(name = "weight")
private BigDecimal weight;

@Column(name = "reps")
private Integer reps;

public Set(){}

public Long getSetId() {
    return setId;
}

public void setSetId(Long setId) {
    this.setId = setId;
}

public Exercise getExercise() {
    return exercise;
}

public void setExercise(Exercise exercise) {
    this.exercise = exercise;
}

public Workout getWorkout() {
    return workout;
}

public void setWorkout(Workout workout) {
    this.workout = workout;
}

public Integer getSetNumber() {
    return setNumber;
}

public void setSetNumber(Integer setNumber) {
    this.setNumber = setNumber;
}

public BigDecimal getWeight() {
    return weight;
}

public void setWeight(BigDecimal weight) {
    this.weight = weight;
}

public Integer getReps() {
    return reps;
}

public void setReps(Integer reps) {
    this.reps = reps;
}



}
