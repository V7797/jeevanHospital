package com.projects.JeevanHospital.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "doctor_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "appointments")
public class DoctorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;
    
    @NotBlank(message = "First Name is Required")
    @Size(max = 25)
    @Column(name = "first_name", length = 25, nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last Name is Required")
    @Size(max = 100)
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
    
    @NotBlank(message = "Specialty is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String specialty;
    
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;
    
    @Email(message = "Invalid email format")
    @Column(name = "email", unique = true, length = 100)
    private String email;
    
    @NotBlank(message = "Room or cabin number is required")
    @Column(name = "room_number", nullable = false, length = 20)
    private String roomNumber;

    @NotNull(message = "Consultation fee is required")
    @PositiveOrZero(message = "Consultation fee cannot be negative")
    @Column(name = "consultation_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;
    
    @NotBlank(message = "Availability schedule summary is required")
    @Column(name = "availability_schedule", nullable = false)
    private String availabilitySchedule;

    // ✅ FIXED: Changed mappedBy to match the exact field name 'doctor' inside AppointmentDetail
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Stops JSON infinite serialization recursion during API calls
    private List<AppointmentDetail> appointments;
}
