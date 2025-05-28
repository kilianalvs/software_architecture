package com.esgi.notfound.domain.ports;

import com.esgi.notfound.domain.entities.Reservation;
import com.esgi.notfound.domain.entities.Vehicle;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Reservation> findByVehicle(Vehicle vehicle);
    List<Reservation> findByVehicleAndDateRange(Vehicle vehicle, LocalDate startDate, LocalDate endDate);
    Optional<Reservation> findByVehicleMatricule(String matricule);
    Reservation save(Reservation reservation);
    void delete(Reservation reservation);
    boolean hasConflictingReservation(Long parkingSpotId, LocalDate startDate, LocalDate endDate);
}