package com.esgi.notfound.infrastructure.repositories;

import com.esgi.notfound.domain.enums.ReservationStatus;
import com.esgi.notfound.infrastructure.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    
    // Réservations par immatriculation
    List<ReservationEntity> findByVehicleMatriculeOrderByStartDateDesc(String vehicleMatricule);
    
    // Réservations actives (en cours)
    @Query("SELECT r FROM ReservationEntity r WHERE r.status = :status")
    List<ReservationEntity> findByStatus(@Param("status") ReservationStatus status);
    
    // Réservations pour une place spécifique
    List<ReservationEntity> findByParkingSpotIdOrderByStartDateDesc(Long parkingSpotId);
    
    // Vérifier conflit de dates pour une place
    @Query("SELECT r FROM ReservationEntity r WHERE r.parkingSpot.id = :spotId " +
           "AND r.status IN ('CONFIRMED', 'ACTIVE') " +
           "AND ((r.startDate <= :endDate) AND (r.endDate >= :startDate))")
    List<ReservationEntity> findConflictingReservations(
            @Param("spotId") Long spotId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    
    // Réservations par période
    List<ReservationEntity> findByStartDateBetween(LocalDate start, LocalDate end);
    
    // Compter réservations actives
    long countByStatus(ReservationStatus status);
}