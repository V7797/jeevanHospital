package com.projects.JeevanHospital.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ward_rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "patients") // Prevents infinite recursion logs
public class WardRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ward_room_id")
    private Long id;

    @NotBlank(message = "Room number is required")
    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    @Column(name = "room_type", nullable = false) // e.g., "ICU", "GENERAL_WARD", "PRIVATE_ROOM"
    private String roomType;

    @NotNull(message = "Price per day is required")
    @Column(name = "price_per_day", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerDay;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied = false;

    // ✅ CORRECT: One WardRoom can have a List of multiple PatientProfiles
    @OneToMany(mappedBy = "wardRoom", fetch = FetchType.LAZY)
    private List<PatientProfile> patients; 
}
