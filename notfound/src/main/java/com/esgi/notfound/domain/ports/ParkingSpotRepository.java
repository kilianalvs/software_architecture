package com.esgi.notfound.domain.ports;

import com.esgi.notfound.domain.entities.ParkingSpot;
import com.esgi.notfound.domain.enums.ParkingSpotType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository {
    List<ParkingSpot> findAvailableSpots(LocalDate startDate, LocalDate endDate);
    List<ParkingSpot> findAvailableSpotsByType(LocalDate startDate, LocalDate endDate, ParkingSpotType type);
    Optional<ParkingSpot> findById(Long id);
    List<ParkingSpot> findAll();
    ParkingSpot save(ParkingSpot parkingSpot);
}