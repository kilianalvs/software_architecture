package com.esgi.notfound.domain.entities;

import com.esgi.notfound.domain.enums.ReservationStatus;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime checkedInAt;
    
    public Reservation(Vehicle vehicle, ParkingSpot parkingSpot, LocalDate startDate, LocalDate endDate) {
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ReservationStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        
        validateReservationPeriod();
    }
    
    private void validateReservationPeriod() {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin");
        }
        
        if (countBusinessDays() > 5) {
            throw new IllegalArgumentException("Une réservation ne peut pas dépasser 5 jours ouvrés");
        }
    }
    
    private long countBusinessDays() {
        long businessDays = 0;
        LocalDate date = startDate;
        
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                businessDays++;
            }
            date = date.plusDays(1);
        }
        
        return businessDays;
    }
    
    public void checkIn() {
        if (status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Seules les réservations actives peuvent être confirmées");
        }
        this.status = ReservationStatus.CHECKED_IN;
        this.checkedInAt = LocalDateTime.now();
    }
    
    public void cancel() {
        if (status == ReservationStatus.CANCELLED || status == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("Cette réservation ne peut plus être annulée");
        }
        this.status = ReservationStatus.CANCELLED;
    }
    
    public boolean isActive() {
        return status == ReservationStatus.ACTIVE;
    }
    
    public boolean isFuture() {
        return startDate.isAfter(LocalDate.now());
    }
    
    // Getters complets
     public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Vehicle getVehicle() { 
        return vehicle; 
    }
    
    public void setVehicle(Vehicle vehicle) { 
        this.vehicle = vehicle; 
    }
    
    public ParkingSpot getParkingSpot() { 
        return parkingSpot; 
    }
    
    public void setParkingSpot(ParkingSpot parkingSpot) { 
        this.parkingSpot = parkingSpot; 
    }
    
    public LocalDate getStartDate() { 
        return startDate; 
    }
    
    public void setStartDate(LocalDate startDate) { 
        this.startDate = startDate; 
    }
    
    public LocalDate getEndDate() { 
        return endDate; 
    }
    
    public void setEndDate(LocalDate endDate) { 
        this.endDate = endDate; 
    }
    
    public ReservationStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(ReservationStatus status) { 
        this.status = status; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public LocalDateTime getCheckedInAt() { 
        return checkedInAt; 
    }
    
    public void setCheckedInAt(LocalDateTime checkedInAt) { 
        this.checkedInAt = checkedInAt; 
    }
}