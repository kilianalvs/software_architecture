package com.esgi.notfound.exposition.dto;

import com.esgi.notfound.domain.enums.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDto {
    private Long id;
    private String vehicleMatricule;
    private Long parkingSpotId;
    private String parkingSpotNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime checkedInAt;
    
    // Constructeur complet
    public ReservationDto(Long id, String vehicleMatricule, Long parkingSpotId, 
                         String parkingSpotNumber, LocalDate startDate, LocalDate endDate,
                         ReservationStatus status, LocalDateTime createdAt, LocalDateTime checkedInAt) {
        this.id = id;
        this.vehicleMatricule = vehicleMatricule;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotNumber = parkingSpotNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.checkedInAt = checkedInAt;
    }
    
    // Getters et setters complets
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getVehicleMatricule() { return vehicleMatricule; }
    public void setVehicleMatricule(String vehicleMatricule) { this.vehicleMatricule = vehicleMatricule; }
    
    public Long getParkingSpotId() { return parkingSpotId; }
    public void setParkingSpotId(Long parkingSpotId) { this.parkingSpotId = parkingSpotId; }
    
    public String getParkingSpotNumber() { return parkingSpotNumber; }
    public void setParkingSpotNumber(String parkingSpotNumber) { this.parkingSpotNumber = parkingSpotNumber; }
    
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