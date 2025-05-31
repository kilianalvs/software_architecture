package com.esgi.notfound.infrastructure.repositories;

import com.esgi.notfound.domain.enums.ParkingSpotType;
import com.esgi.notfound.infrastructure.entities.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {
    
    // Trouver par numéro de place
    Optional<ParkingSpotEntity> findByNumber(String number);
    
    // Trouver places disponibles
    List<ParkingSpotEntity> findByAvailable(boolean available);
    
    // Trouver par type et disponibilité
    List<ParkingSpotEntity> findByTypeAndAvailable(ParkingSpotType type, boolean available);
    
    // Trouver par rangée, ordonnées par position
    List<ParkingSpotEntity> findByRowNameOrderByPositionInRow(String rowName);
    
    // Compter places disponibles par type
    long countByTypeAndAvailable(ParkingSpotType type, boolean available);
    
    // Compter places disponibles
    long countByAvailable(boolean available);
    
    // Trouver places avec recharge électrique
    List<ParkingSpotEntity> findByHasElectricChargingAndAvailable(boolean hasElectricCharging, boolean available);
    
    // Requête personnalisée pour places disponibles par type
    @Query("SELECT p FROM ParkingSpotEntity p WHERE p.type = :type AND p.available = true ORDER BY p.number")
    List<ParkingSpotEntity> findAvailableSpotsByType(@Param("type") ParkingSpotType type);
}