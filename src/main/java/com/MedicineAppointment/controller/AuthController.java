package com.MedicineAppointment.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.MedicineAppointment.entity.User;
import com.MedicineAppointment.repository.UserRepository;


@Controller
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    @GetMapping("/register")
    public String registerForm(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        System.out.println("Registering user with role: " + user.getRole());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    
    @GetMapping("/dashboard/doctor")
    public String doctorDashboard() {
        return "doctor_dashboard"; // Make doctor_dashboard.html
    }

    @GetMapping("/dashboard/patient")
    public String patientDashboard() {
        return "patient_dashboard"; // Make patient_dashboard.html
    }
    @GetMapping("/back")
    public String goBack(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }



}