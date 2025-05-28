package com.esgi.notfound.infrastructure.entities;

import com.esgi.notfound.domain.enums.ParkingSpotType;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_spots")
public class ParkingSpotEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "spot_number", nullable = false, unique = true, length = 10)
    private String number;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "spot_type", nullable = false)
    private ParkingSpotType type;
    
    @Column(name = "row_name", nullable = false, length = 2)
    private String rowName; // A, B, C, D, E, F
    
    @Column(name = "position_in_row", nullable = false)
    private Integer positionInRow; // 1 à 10
    
    @Column(name = "is_available", nullable = false)
    private boolean available = true;
    
    @Column(name = "has_electric_charging", nullable = false)
    private boolean hasElectricCharging = false;
    
    // Constructeurs
    public ParkingSpotEntity() {}
    
    public ParkingSpotEntity(String number, ParkingSpotType type, String rowName, 
                           Integer positionInRow, boolean hasElectricCharging) {
        this.number = number;
        this.type = type;
        this.rowName = rowName;
        this.positionInRow = positionInRow;
        this.hasElectricCharging = hasElectricCharging;
        this.available = true;
    }
    
    // Getters et setters complets
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    
    public ParkingSpotType getType() { return type; }
    public void setType(ParkingSpotType type) { this.type = type; }
    
    public String getRowName() { return rowName; }
    public void setRowName(String rowName) { this.rowName = rowName; }
    
    public Integer getPositionInRow() { return positionInRow; }
    public void setPositionInRow(Integer positionInRow) { this.positionInRow = positionInRow; }
    
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    
    public boolean hasElectricCharging() { return hasElectricCharging; }
    public void setHasElectricCharging(boolean hasElectricCharging) { this.hasElectricCharging = hasElectricCharging; }
}
