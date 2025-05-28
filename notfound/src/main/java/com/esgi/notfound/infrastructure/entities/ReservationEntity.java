package com.esgi.notfound.infrastructure.entities;

import com.esgi.notfound.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class ReservationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "vehicle_matricule", nullable = false, length = 15)
    private String vehicleMatricule;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_spot_id", nullable = false)
    private ParkingSpotEntity parkingSpot;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;
    
    // Constructeurs
    public ReservationEntity() {}
    
    public ReservationEntity(String vehicleMatricule, ParkingSpotEntity parkingSpot, 
                           LocalDate startDate, LocalDate endDate) {
        this.vehicleMatricule = vehicleMatricule;
        this.parkingSpot = parkingSpot;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ReservationStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters et setters complets
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getVehicleMatricule() { return vehicleMatricule; }
    public void setVehicleMatricule(String vehicleMatricule) { this.vehicleMatricule = vehicleMatricule; }
    
    public ParkingSpotEntity getParkingSpot() { return parkingSpot; }
    public void setParkingSpot(ParkingSpotEntity parkingSpot) { this.parkingSpot = parkingSpot; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getCheckedInAt() { return checkedInAt; }
    public void setCheckedInAt(LocalDateTime checkedInAt) { this.checkedInAt = checkedInAt; }
}