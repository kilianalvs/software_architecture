package com.esgi.notfound.exposition.dto;

import java.time.LocalDate;

public class CreateReservationDto {
    private Long parkingSpotId;
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Constructeurs
    public CreateReservationDto() {}
    
    public CreateReservationDto(Long parkingSpotId, LocalDate startDate, LocalDate endDate) {
        this.parkingSpotId = parkingSpotId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    // Getters et setters
    public Long getParkingSpotId() { return parkingSpotId; }
    public void setParkingSpotId(Long parkingSpotId) { this.parkingSpotId = parkingSpotId; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}