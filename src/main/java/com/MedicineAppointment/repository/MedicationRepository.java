package com.MedicineAppointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MedicineAppointment.entity.Medication;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByPatientId(Long id);
    List<Medication> findByDoctorId(Long id);
}
