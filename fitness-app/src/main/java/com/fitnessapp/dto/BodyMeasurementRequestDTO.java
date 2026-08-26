package com.fitnessapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BodyMeasurementRequestDTO {
    private LocalDate measureDate;
    private BigDecimal weight;
    private BigDecimal bodyFat;
    private BigDecimal muscleMass;
    private BigDecimal waistCm;
    private BigDecimal chestCm;
    private BigDecimal armCm;
    private BigDecimal legCm;
    private String note;

    public BodyMeasurementRequestDTO() {}

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
}