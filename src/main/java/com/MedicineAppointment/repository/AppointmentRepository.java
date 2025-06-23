package com.MedicineAppointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MedicineAppointment.entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long id);
    List<Appointment> findByDoctorId(Long id);
}