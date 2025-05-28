package com.esgi.notfound.domain.services;

import com.esgi.notfound.domain.entities.ParkingSpot;
import com.esgi.notfound.domain.enums.ParkingSpotType;
import com.esgi.notfound.domain.ports.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ParkingService {
    
    private final ParkingSpotRepository parkingSpotRepository;
    
    public ParkingService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }
    
    public List<ParkingSpot> getAvailableSpots(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return parkingSpotRepository.findAvailableSpots(startDate, endDate);
    }
    
    public List<ParkingSpot> getAvailableElectricSpots(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return parkingSpotRepository.findAvailableSpotsByType(startDate, endDate, ParkingSpotType.ELECTRIC_CHARGING);
    }
    
    public List<ParkingSpot> getAvailableStandardSpots(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return parkingSpotRepository.findAvailableSpotsByType(startDate, endDate, ParkingSpotType.STANDARD);
    }
    
    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le passé");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin");
        }
    }
}