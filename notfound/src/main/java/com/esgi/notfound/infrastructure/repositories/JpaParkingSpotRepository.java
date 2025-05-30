package com.esgi.notfound.infrastructure.repositories;

import com.esgi.notfound.domain.enums.ParkingSpotType;
import com.esgi.notfound.infrastructure.entities.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {
    
    @Query("""
        SELECT ps FROM ParkingSpotEntity ps 
        WHERE ps.id NOT IN (
            SELECT r.parkingSpot.id FROM ReservationEntity r 
            WHERE r.status IN ('ACTIVE', 'CHECKED_IN') 
            AND (
                (r.startDate <= :endDate AND r.endDate >= :startDate)
            )
        )
        ORDER BY ps.rowName, ps.positionInRow
        """)
    List<ParkingSpotEntity> findAvailableSpots(@Param("startDate") LocalDate startDate, 
                                              @Param("endDate") LocalDate endDate);
    
    @Query("""
        SELECT ps FROM ParkingSpotEntity ps 
        WHERE ps.type = :type
        AND ps.id NOT IN (
            SELECT r.parkingSpot.id FROM ReservationEntity r 
            WHERE r.status IN ('ACTIVE', 'CHECKED_IN') 
            AND (
                (r.startDate <= :endDate AND r.endDate >= :startDate)
            )
        )
        ORDER BY ps.rowName, ps.positionInRow
        """)
    List<ParkingSpotEntity> findAvailableSpotsByType(@Param("startDate") LocalDate startDate, 
                                                    @Param("endDate") LocalDate endDate,
                                                    @Param("type") ParkingSpotType type);
    
    List<ParkingSpotEntity> findByRowNameOrderByPositionInRow(String rowName);
    
    boolean existsByNumber(String number);
}