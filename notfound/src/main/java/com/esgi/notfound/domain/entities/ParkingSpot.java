package com.esgi.notfound.domain.entities;

import com.esgi.notfound.domain.enums.ParkingSpotType;

public class ParkingSpot {
    private Long id;
    private String number;
    private ParkingSpotType type;
    private boolean available;
    
    public ParkingSpot(String number, ParkingSpotType type) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de la place ne peut pas être vide");
        }
        if (type == null) {
            throw new IllegalArgumentException("Le type de place ne peut pas être null");
        }
        
        this.number = number.trim().toUpperCase();
        this.type = type;
        this.available = true;
    }
    
    public void reserve() {
        if (!available) {
            throw new IllegalStateException("La place n'est pas disponible");
        }
        this.available = false;
    }
    
    public void free() {
        this.available = true;
    }
    
    public boolean hasElectricCharging() {
        return type == ParkingSpotType.ELECTRIC_CHARGING;
    }
    
    // Getters et setters complets
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getNumber() { 
        return number; 
    }
    
    public void setNumber(String number) { 
        this.number = number; 
    }
    
    public ParkingSpotType getType() { 
        return type; 
    }
    
    public void setType(ParkingSpotType type) { 
        this.type = type; 
    }
    
    public boolean isAvailable() { 
        return available; 
    }
    
    public void setAvailable(boolean available) { 
        this.available = available; 
    }
}
