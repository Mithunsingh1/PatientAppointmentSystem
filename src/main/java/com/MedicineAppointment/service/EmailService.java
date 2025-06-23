
package com.MedicineAppointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentConfirmation(String toEmail, String patientName, String appointmentTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Appointment Confirmed");
        message.setText("Hello " + patientName + ",\n\nYour appointment is confirmed for: " + appointmentTime + "\n\nThank you!");
        mailSender.send(message);
    }

    public void sendAppointmentCancellation(String toEmail, String patientName, String appointmentTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Appointment Cancelled");
        message.setText("Hello " + patientName + ",\n\nYour appointment on " + appointmentTime + " has been cancelled.\n\nThank you!");
        mailSender.send(message);
    }
}
