package com.esgi.notfound.exposition.dto;

import com.esgi.notfound.domain.enums.ParkingSpotType;

public class ParkingSpotAvailabilityDto {
    private Long id;
    private String number;
    private ParkingSpotType type;
    private boolean isElectricCharging;
    
    public ParkingSpotAvailabilityDto(Long id, String number, ParkingSpotType type, boolean isElectricCharging) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.isElectricCharging = isElectricCharging;
    }
    
    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    
    public ParkingSpotType getType() { return type; }
    public void setType(ParkingSpotType type) { this.type = type; }
    
    public boolean isElectricCharging() { return isElectricCharging; }
    public void setElectricCharging(boolean electricCharging) { isElectricCharging = electricCharging; }
}
