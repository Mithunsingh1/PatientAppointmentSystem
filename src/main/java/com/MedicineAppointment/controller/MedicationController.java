package com.MedicineAppointment.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.MedicineAppointment.entity.Medication;
import com.MedicineAppointment.entity.User;
import com.MedicineAppointment.repository.MedicationRepository;
import com.MedicineAppointment.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepo;

    @Autowired
    private UserRepository userRepo;

    // Show form to assign medication (for doctors)
    @GetMapping("/assign")
    public String assignForm(Model model) {
        List<User> patients = userRepo.findAll()
                .stream().filter(user -> user.getRole().equals("PATIENT")).toList();

        model.addAttribute("medication", new Medication());
        model.addAttribute("patients", patients);
        return "medication_form";
    }

    // Save medication
    @PostMapping("/assign")
    public String assignMedication(@ModelAttribute Medication medication,
                                   @RequestParam Long patientId,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        User doctor = userRepo.findByUsername(userDetails.getUsername());
        User patient = userRepo.findById(patientId).orElse(null);

        medication.setPatient(patient);
        medication.setDoctor(doctor);
        medicationRepo.save(medication);

        return "redirect:/medications/doctor";
    }
    

    // View medications (for patients)
    @GetMapping("/my")
    public String viewMyMeds(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User patient = userRepo.findByUsername(userDetails.getUsername());
        List<Medication> list = medicationRepo.findByPatientId(patient.getId());
        model.addAttribute("medications", list);
        return "medications";
    }

    // View  assigned by doctor
    @GetMapping("/doctor")
    public String doctorMeds(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User doctor = userRepo.findByUsername(userDetails.getUsername());
        List<Medication> list = medicationRepo.findByDoctorId(doctor.getId());
        model.addAttribute("medications", list);
        return "medications";
    }
    @GetMapping("/back")
    public String goBack(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }


}