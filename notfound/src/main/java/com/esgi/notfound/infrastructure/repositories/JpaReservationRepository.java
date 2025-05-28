package com.esgi.notfound.infrastructure.repositories;

import com.esgi.notfound.infrastructure.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReservationRepository extends JpaRepository<ReservationEntity, Long> {
    
    List<ReservationEntity> findByVehicleMatriculeOrderByCreatedAtDesc(String vehicleMatricule);
    
    @Query("""
        SELECT r FROM ReservationEntity r 
        WHERE r.vehicleMatricule = :matricule 
        AND r.startDate <= :endDate 
        AND r.endDate >= :startDate
        AND r.status IN ('ACTIVE', 'CHECKED_IN')
        ORDER BY r.createdAt DESC
        """)
    List<ReservationEntity> findByVehicleAndDateRange(@Param("matricule") String matricule,
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);
    
    @Query("""
        SELECT r FROM ReservationEntity r 
        WHERE r.vehicleMatricule = :matricule 
        AND r.status = 'ACTIVE'
        ORDER BY r.createdAt DESC
        """)
    Optional<ReservationEntity> findActiveReservationByVehicleMatricule(@Param("matricule") String matricule);
    
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM ReservationEntity r 
        WHERE r.parkingSpot.id = :parkingSpotId 
        AND r.status IN ('ACTIVE', 'CHECKED_IN')
        AND (
            (r.startDate <= :endDate AND r.endDate >= :startDate)
        )
        """)
    boolean hasConflictingReservation(@Param("parkingSpotId") Long parkingSpotId,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);
    
    @Query("""
        SELECT COUNT(r) FROM ReservationEntity r 
        WHERE r.vehicleMatricule = :matricule 
        AND r.status IN ('ACTIVE', 'CHECKED_IN')
        """)
    long countActiveReservationsByVehicle(@Param("matricule") String matricule);
}
