package com.fitnessapp.service;

import com.fitnessapp.dto.BodyMeasurementRequestDTO;
import com.fitnessapp.dto.BodyMeasurementResponseDTO;
import com.fitnessapp.model.BodyMeasurement;
import com.fitnessapp.model.User;
import com.fitnessapp.repository.BodyMeasurementRepository;
import com.fitnessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BodyMeasurementService {

    @Autowired
    private BodyMeasurementRepository bodyMeasurementRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BodyMeasurementResponseDTO addMeasurement(BodyMeasurementRequestDTO request) {
        // 1. Uzmi email trenutnog korisnika
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Pronađi korisnika
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Kreiraj novi BodyMeasurement
        BodyMeasurement measurement = new BodyMeasurement();
        measurement.setUser(user);

        // Postavi datum – ako je poslat, koristi njega, inače danas
        LocalDate measureDate = request.getMeasureDate();
        measurement.setMeasureDate(measureDate != null ? measureDate : LocalDate.now());

        measurement.setWeight(request.getWeight());
        measurement.setBodyFat(request.getBodyFat());
        measurement.setMuscleMass(request.getMuscleMass());
        measurement.setWaistCm(request.getWaistCm());
        measurement.setChestCm(request.getChestCm());
        measurement.setArmCm(request.getArmCm());
        measurement.setLegCm(request.getLegCm());
        measurement.setNote(request.getNote());
        measurement.setCreatedAt(LocalDateTime.now());

        // 4. Sačuvaj
        BodyMeasurement saved = bodyMeasurementRepository.save(measurement);

        // 5. Konvertuj u DTO
        return convertToDTO(saved);
    }

    public List<BodyMeasurementResponseDTO> getMyMeasurements() {
        // 1. Uzmi email trenutnog korisnika
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Pronađi korisnika
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Dohvati sva merenja za korisnika
        List<BodyMeasurement> measurements = bodyMeasurementRepository
                .findByUserUserIdOrderByMeasureDateDesc(user.getUserId());

        // 4. Konvertuj u DTO listu
        return measurements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Pomoćna metoda za konverziju
    private BodyMeasurementResponseDTO convertToDTO(BodyMeasurement measurement) {
        BodyMeasurementResponseDTO dto = new BodyMeasurementResponseDTO();
        dto.setMeasurementId(measurement.getMeasurementId());
        dto.setMeasureDate(measurement.getMeasureDate());
        dto.setWeight(measurement.getWeight());
        dto.setBodyFat(measurement.getBodyFat());
        dto.setMuscleMass(measurement.getMuscleMass());
        dto.setWaistCm(measurement.getWaistCm());
        dto.setChestCm(measurement.getChestCm());
        dto.setArmCm(measurement.getArmCm());
        dto.setLegCm(measurement.getLegCm());
        dto.setNote(measurement.getNote());
        return dto;
    }

    @Transactional
public BodyMeasurementResponseDTO updateMeasurement(Long measurementId, BodyMeasurementRequestDTO request) {
    // 1. Uzmi email trenutnog korisnika
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    // 2. Pronađi korisnika
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 3. Pronađi merenje
    BodyMeasurement measurement = bodyMeasurementRepository.findById(measurementId)
            .orElseThrow(() -> new RuntimeException("Measurement not found"));

    // 4. Proveri da li je vlasnik preko Email-a
if (!measurement.getUser().getEmail().equals(user.getEmail())) {
    throw new RuntimeException("You don't own this measurement");
        }

    // 5. Ažuriraj polja
    LocalDate measureDate = request.getMeasureDate() != null ? 
        request.getMeasureDate() : LocalDate.now();
    measurement.setMeasureDate(measureDate);
    measurement.setWeight(request.getWeight());
    measurement.setBodyFat(request.getBodyFat());
    measurement.setMuscleMass(request.getMuscleMass());
    measurement.setWaistCm(request.getWaistCm());
    measurement.setChestCm(request.getChestCm());
    measurement.setArmCm(request.getArmCm());
    measurement.setLegCm(request.getLegCm());
    measurement.setNote(request.getNote());

    // 6. Sačuvaj
    BodyMeasurement updated = bodyMeasurementRepository.save(measurement);
    return convertToDTO(updated);
}

@Transactional
public void deleteMeasurement(Long measurementId) {
    // 1. Uzmi email trenutnog korisnika
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    // 2. Pronađi korisnika
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 3. Pronađi merenje
    BodyMeasurement measurement = bodyMeasurementRepository.findById(measurementId)
            .orElseThrow(() -> new RuntimeException("Measurement not found"));

    // 4. Proveri da li je vlasnik  PREKO EMAIL-a
    if (!measurement.getUser().getEmail().equals(user.getEmail())) {
    throw new RuntimeException("You don't own this measurement");
        }

    // 5. Obriši
    bodyMeasurementRepository.delete(measurement);
}


}