package com.fitnessapp.controller;

import com.fitnessapp.dto.BodyMeasurementRequestDTO;
import com.fitnessapp.dto.BodyMeasurementResponseDTO;
import com.fitnessapp.service.BodyMeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class BodyMeasurementController {

    @Autowired
    private BodyMeasurementService bodyMeasurementService;

    @PostMapping
    public ResponseEntity<BodyMeasurementResponseDTO> addMeasurement(
            @Valid @RequestBody BodyMeasurementRequestDTO request) {
        BodyMeasurementResponseDTO response = bodyMeasurementService.addMeasurement(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BodyMeasurementResponseDTO>> getMyMeasurements() {
        List<BodyMeasurementResponseDTO> measurements = bodyMeasurementService.getMyMeasurements();
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PutMapping("/{measurementId}")
    public ResponseEntity<BodyMeasurementResponseDTO> updateMeasurement(
            @PathVariable Long measurementId,
            @Valid @RequestBody BodyMeasurementRequestDTO request) {
        BodyMeasurementResponseDTO response = bodyMeasurementService.updateMeasurement(measurementId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{measurementId}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long measurementId) {
        bodyMeasurementService.deleteMeasurement(measurementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}