package com.fitnessapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "body_measurements")
public class BodyMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private Long measurementId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "measure_date", nullable = false)
    private LocalDate measureDate;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "body_fat")
    private BigDecimal bodyFat;

    @Column(name = "muscle_mass")
    private BigDecimal muscleMass;

    @Column(name = "waist_cm")
    private BigDecimal waistCm;

    @Column(name = "chest_cm")
    private BigDecimal chestCm;

    @Column(name = "arm_cm")
    private BigDecimal armCm;

    @Column(name = "leg_cm")
    private BigDecimal legCm;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ===== GETTERI =====
    public Long getMeasurementId() { return measurementId; }
    public void setMeasurementId(Long measurementId) { this.measurementId = measurementId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getMeasureDate() { return measureDate; }
    public void setMeasureDate(LocalDate measureDate) { this.measureDate = measureDate; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getBodyFat() { return bodyFat; }
    public void setBodyFat(BigDecimal bodyFat) { this.bodyFat = bodyFat; }

    public BigDecimal getMuscleMass() { return muscleMass; }
    public void setMuscleMass(BigDecimal muscleMass) { this.muscleMass = muscleMass; }

    public BigDecimal getWaistCm() { return waistCm; }
    public void setWaistCm(BigDecimal waistCm) { this.waistCm = waistCm; }

    public BigDecimal getChestCm() { return chestCm; }
    public void setChestCm(BigDecimal chestCm) { this.chestCm = chestCm; }

    public BigDecimal getArmCm() { return armCm; }
    public void setArmCm(BigDecimal armCm) { this.armCm = armCm; }

    public BigDecimal getLegCm() { return legCm; }
    public void setLegCm(BigDecimal legCm) { this.legCm = legCm; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}