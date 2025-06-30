🏥 Medicine Appointment System:::::::::::::::::


A complete Spring Boot application for managing patient registrations, appointments, and medicines. It provides role-based access (admin, patient, doctor), appointment scheduling, medication management, and API documentation with Swagger.

✨ Features:

1. Patient registration and login

2. Admin management dashboard

  Book, view, update, and cancel appointments

3. Manage medication records

4. Role-based authentication and authorization

Pastel-themed frontend UI (Thymeleaf + Tailwind CSS)

API Documentation with Swagger UI

Unit and integration testing

🛠️ Tech Stack:

Backend:-
 Spring Boot, Spring Security, Spring Data JPA

Frontend:- 
Thymeleaf, Tailwind CSS

Database:-
MySQL

API Documentation: Swagger (Springfox)

Testing:-
JUnit, Mockito

📂 Project Structure:-

src/main/java/com/medicineappointmentsystem

├── controller

│   ├── AppointmentController.java

│   ├── AuthController.java

│   └── MedicationController.java

├── model

│   ├── Appointment.java

│   ├── Medication.java

│   └── User.java

├── repository

│   ├── AppointmentRepository.java

│   ├── MedicationRepository.java

│   └── UserRepository.java

├── service

│   ├── AppointmentService.java

│   ├── MedicationService.java

│   └── UserService.java

├── config

│   └── SecurityConfig.java

└── MedicineAppointmentSystemApplication.java



Frontend templates:

src/main/resources/templates

├── login.html

├── register.html

├── dashboard.html

├── appointment_list.html

├── appointment_form.html

├── medication_list.html

├── medication_form.html


🚀 Getting Started:
Prerequisites
Java 17+

Maven:

MySQL Server

Database Setup
Create a database named:

CREATE DATABASE medicine_appointment_system;
Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/medicine_appointment_system
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
springdoc.api-docs.enabled=true
Run the App
# Build the project
mvn clean install

# Run the project
mvn spring-boot:run
Visit:
Frontend:  http://localhost:8080/

Swagger API Docs:http://localhost:8080/swagger-ui/index.html

🔒 Authentication:

Roles: ADMIN, PATIENT, DOCTOR

JWT Authentication (optional depending on config)

Passwords are securely encoded using BCrypt

📃 API Documentation:

Auto-generated with Swagger.

Available at: http://localhost:8080/swagger-ui/index.html
