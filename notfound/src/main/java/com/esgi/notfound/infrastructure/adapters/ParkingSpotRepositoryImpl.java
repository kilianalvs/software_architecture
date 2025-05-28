package com.esgi.notfound.infrastructure.adapters;

import com.esgi.notfound.domain.entities.ParkingSpot;
import com.esgi.notfound.domain.enums.ParkingSpotType;
import com.esgi.notfound.domain.ports.ParkingSpotRepository;
import com.esgi.notfound.infrastructure.entities.ParkingSpotEntity;
import com.esgi.notfound.infrastructure.repositories.JpaParkingSpotRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ParkingSpotRepositoryImpl implements ParkingSpotRepository {
    
    private final JpaParkingSpotRepository jpaParkingSpotRepository;
    
    public ParkingSpotRepositoryImpl(JpaParkingSpotRepository jpaParkingSpotRepository) {
        this.jpaParkingSpotRepository = jpaParkingSpotRepository;
    }
    
    @Override
    public List<ParkingSpot> findAvailableSpots(LocalDate startDate, LocalDate endDate) {
        return jpaParkingSpotRepository.findAvailableSpots(startDate, endDate)
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    @Override
    public List<ParkingSpot> findAvailableSpotsByType(LocalDate startDate, LocalDate endDate, ParkingSpotType type) {
        return jpaParkingSpotRepository.findAvailableSpotsByType(startDate, endDate, type)
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    @Override
    public Optional<ParkingSpot> findById(Long id) {
        return jpaParkingSpotRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public List<ParkingSpot> findAll() {
        return jpaParkingSpotRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    @Override
    public ParkingSpot save(ParkingSpot parkingSpot) {
        ParkingSpotEntity entity = toEntity(parkingSpot);
        ParkingSpotEntity savedEntity = jpaParkingSpotRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    // Méthodes de mapping
    private ParkingSpot toDomain(ParkingSpotEntity entity) {
        ParkingSpot parkingSpot = new ParkingSpot(entity.getNumber(), entity.getType());
        parkingSpot.setId(entity.getId());
        parkingSpot.setAvailable(entity.isAvailable());
        return parkingSpot;
    }
    
    private ParkingSpotEntity toEntity(ParkingSpot domain) {
        ParkingSpotEntity entity = new ParkingSpotEntity();
        entity.setId(domain.getId());
        entity.setNumber(domain.getNumber());
        entity.setType(domain.getType());
        entity.setAvailable(domain.isAvailable());
        
        // Déterminer la rangée et position à partir du numéro
        if (domain.getNumber() != null) {
            String rowName = domain.getNumber().substring(0, 1);
            Integer position = Integer.parseInt(domain.getNumber().substring(1));
            entity.setRowName(rowName);
            entity.setPositionInRow(position);
            entity.setHasElectricCharging(domain.getType() == ParkingSpotType.ELECTRIC_CHARGING);
        }
        
        return entity;
    }
}