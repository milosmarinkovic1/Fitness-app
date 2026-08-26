
package com.fitnessapp.dto;

/*
 exercise_id       SERIAL PRIMARY KEY,
    name              VARCHAR(100) NOT NULL UNIQUE,
    description       VARCHAR(500),
    category_id       INTEGER NOT NULL REFERENCES categories(category_id) ON DELETE RESTRICT
*/


public class ExerciseResponseDTO {

    private Long exerciseId;
    private String name;
    private String description;
    private String categoryName;

    public ExerciseResponseDTO(){}


    public Long getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}