package com.esgi.notfound.infrastructure.adapters;

import com.esgi.notfound.domain.entities.ParkingSpot;
import com.esgi.notfound.domain.entities.Reservation;
import com.esgi.notfound.domain.entities.Vehicle;
import com.esgi.notfound.domain.ports.ReservationRepository;
import com.esgi.notfound.infrastructure.entities.ParkingSpotEntity;
import com.esgi.notfound.infrastructure.entities.ReservationEntity;
import com.esgi.notfound.infrastructure.repositories.JpaParkingSpotRepository;
import com.esgi.notfound.infrastructure.repositories.JpaReservationRepository;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationRepositoryImpl implements ReservationRepository {
    
    private final JpaReservationRepository jpaReservationRepository;
    private final JpaParkingSpotRepository jpaParkingSpotRepository;
    
    public ReservationRepositoryImpl(JpaReservationRepository jpaReservationRepository,
                                   JpaParkingSpotRepository jpaParkingSpotRepository) {
        this.jpaReservationRepository = jpaReservationRepository;
        this.jpaParkingSpotRepository = jpaParkingSpotRepository;
    }
    
    @Override
    public List<Reservation> findByVehicle(Vehicle vehicle) {
        return jpaReservationRepository.findByVehicleMatriculeOrderByCreatedAtDesc(vehicle.getMatricule())
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    @Override
    public List<Reservation> findByVehicleAndDateRange(Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        return jpaReservationRepository.findByVehicleAndDateRange(vehicle.getMatricule(), startDate, endDate)
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    @Override
    public Optional<Reservation> findByVehicleMatricule(String matricule) {
        return jpaReservationRepository.findActiveReservationByVehicleMatricule(matricule)
                .map(this::toDomain);
    }
    
    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = toEntity(reservation);
        ReservationEntity savedEntity = jpaReservationRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    @Override
    public void delete(Reservation reservation) {
        if (reservation.getId() != null) {
            jpaReservationRepository.deleteById(reservation.getId());
        }
    }
    
    @Override
    public boolean hasConflictingReservation(Long parkingSpotId, LocalDate startDate, LocalDate endDate) {
        return jpaReservationRepository.hasConflictingReservation(parkingSpotId, startDate, endDate);
    }
    
    // Méthodes de mapping
    private Reservation toDomain(ReservationEntity entity) {
        Vehicle vehicle = new Vehicle(entity.getVehicleMatricule());
        ParkingSpot parkingSpot = new ParkingSpot(entity.getParkingSpot().getNumber(), entity.getParkingSpot().getType());
        parkingSpot.setId(entity.getParkingSpot().getId());
        
        Reservation reservation = new Reservation(vehicle, parkingSpot, entity.getStartDate(), entity.getEndDate());
        reservation.setId(entity.getId());
        
        return reservation;
    }
    
    private ReservationEntity toEntity(Reservation domain) {
        ParkingSpotEntity parkingSpotEntity = jpaParkingSpotRepository.findById(domain.getParkingSpot().getId())
                .orElseThrow(() -> new IllegalArgumentException("Place de parking non trouvée"));
        
        ReservationEntity entity = new ReservationEntity();
        entity.setId(domain.getId());
        entity.setVehicleMatricule(domain.getVehicle().getMatricule());
        entity.setParkingSpot(parkingSpotEntity);
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setCheckedInAt(domain.getCheckedInAt());
        
        return entity;
    }
}