package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for Patient Management")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a patient by id")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable("id") UUID id){
        PatientResponseDTO patientResponseDTO = patientService.getPatientById(id);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    @Transactional
    public ResponseEntity<PatientResponseDTO>createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @Transactional
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") UUID id, @Validated(Default.class) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing patient")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();

    }
}
