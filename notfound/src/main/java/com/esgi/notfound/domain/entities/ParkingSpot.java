package com.esgi.notfound.domain.entities;

import com.esgi.notfound.domain.enums.ParkingSpotType;

public class ParkingSpot {
    private Long id;
    private String number;
    private ParkingSpotType type;
    private boolean available;
    
    public ParkingSpot(Long id, String number, ParkingSpotType type) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.available = true;
    }
    
    // Getters et setters
    public Long getId() { return id; }
    public String getNumber() { return number; }
    public ParkingSpotType getType() { return type; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    
    public boolean isElectricCharging() {
        return type == ParkingSpotType.ELECTRIC_CHARGING;
    }
}