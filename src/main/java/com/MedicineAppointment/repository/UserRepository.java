package com.MedicineAppointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MedicineAppointment.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
