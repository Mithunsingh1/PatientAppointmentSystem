package com.MedicineAppointment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.MedicineAppointment.entity.Appointment;
import com.MedicineAppointment.entity.User;
import com.MedicineAppointment.repository.AppointmentRepository;
import com.MedicineAppointment.repository.UserRepository;
import com.MedicineAppointment.service.EmailService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    // Show form to book appointment
   
    
    @GetMapping("/book")
    public String bookForm(Model model) {
        List<User> doctors = userRepo.findAll()
                .stream().filter(user -> user.getRole().equals("DOCTOR")).toList();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctors);
        return "appointmentBooking";
    }

    // Save appointment and send confirmation email
    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment,
                                  @RequestParam Long doctorId,
                                  @RequestParam String datetime,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
         System.out.println(appointment.toString());
        User patient = userRepo.findByUsername(userDetails.getUsername());
        User doctor = userRepo.findById(doctorId).orElse(null);

        if (doctor == null || patient == null) {
            model.addAttribute("error", "Invalid doctor or patient.");
            return "appointmentBooking";
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(LocalDateTime.parse(datetime));
        appointment.setStatus("Pending");

        appointmentRepo.save(appointment);

        // ✅ Send confirmation email
        // emailService.sendAppointmentConfirmation(
        //         patient.getEmail(),
        //         patient.getUsername(),
        //         appointment.getAppointmentTime().toString()
        // );

        // ✅ Add success message and reset form
        model.addAttribute("successMessage", "✅ Appointment booked successfully and email sent!");
        model.addAttribute("appointment", new Appointment()); // Reset form
        List<User> doctors = userRepo.findAll()
                .stream().filter(u -> u.getRole().equals("DOCTOR")).toList();
        model.addAttribute("doctors", doctors);

        return "appointmentBooking";  // Don't redirect here to preserve message
    }

    // Patient's appointments
    @GetMapping("/my")
    public String viewMyAppointments(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User patient = userRepo.findByUsername(userDetails.getUsername());
        List<Appointment> list = appointmentRepo.findByPatientId(patient.getId());
        model.addAttribute("appointments", list);
        return "appointments";
    }

    // Doctor's appointments
    @GetMapping("/doctor")
    public String doctorAppointments(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User doctor = userRepo.findByUsername(userDetails.getUsername());
        List<Appointment> list = appointmentRepo.findByDoctorId(doctor.getId());
        model.addAttribute("appointments", list);
        return "appointments";
    }

    // Cancel appointment (patient)
    @PostMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Appointment appointment = appointmentRepo.findById(id).orElse(null);
        if (appointment != null && appointment.getPatient().getEmail().equals(userDetails.getUsername())) {
            appointment.setStatus("Cancelled");
            appointmentRepo.save(appointment);

            // ✅ Send cancellation email
            emailService.sendAppointmentCancellation(
                    appointment.getPatient().getEmail(),
                    appointment.getPatient().getUsername(),
                    appointment.getAppointmentTime().toString()
            );
        }
        return "redirect:/appointments/my";
    }

    @GetMapping("/back")
    public String goBack(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }
}
