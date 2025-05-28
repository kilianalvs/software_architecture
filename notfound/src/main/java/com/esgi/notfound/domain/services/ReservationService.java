package com.esgi.notfound.domain.services;

import com.esgi.notfound.domain.entities.ParkingSpot;
import com.esgi.notfound.domain.entities.Reservation;
import com.esgi.notfound.domain.entities.Vehicle;
import com.esgi.notfound.domain.enums.ReservationStatus;
import com.esgi.notfound.domain.ports.ParkingSpotRepository;
import com.esgi.notfound.domain.ports.ReservationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    
    private final ReservationRepository reservationRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    
    public ReservationService(ReservationRepository reservationRepository, 
                             ParkingSpotRepository parkingSpotRepository) {
        this.reservationRepository = reservationRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }
    
    public List<Reservation> getReservationsByVehicle(String matricule) {
        Vehicle vehicle = new Vehicle(matricule);
        return reservationRepository.findByVehicle(vehicle);
    }
    
    public Reservation createReservation(String matricule, Long parkingSpotId, 
                                       LocalDate startDate, LocalDate endDate) {
        Vehicle vehicle = new Vehicle(matricule);
        
        // Vérifier qu'il n'y a pas déjà une réservation active pour ce véhicule
        checkNoActiveReservation(vehicle);
        
        // Récupérer la place de parking
        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId)
            .orElseThrow(() -> new IllegalArgumentException("Place de parking introuvable"));
        
        // Vérifier qu'il n'y a pas de conflit de réservation
        if (reservationRepository.hasConflictingReservation(parkingSpotId, startDate, endDate)) {
            throw new IllegalArgumentException("Cette place est déjà réservée pour ces dates");
        }
        
        // Créer et sauvegarder la réservation
        Reservation reservation = new Reservation(vehicle, parkingSpot, startDate, endDate);
        return reservationRepository.save(reservation);
    }
    
    public Reservation checkInReservation(String matricule) {
        Reservation reservation = findActiveReservationByMatricule(matricule);
        
        // Vérifier que c'est le bon jour
        LocalDate today = LocalDate.now();
        if (today.isBefore(reservation.getStartDate()) || today.isAfter(reservation.getEndDate())) {
            throw new IllegalArgumentException("Le check-in ne peut se faire que pendant la période de réservation");
        }
        
        reservation.checkIn();
        return reservationRepository.save(reservation);
    }
    
    public void cancelReservation(String matricule) {
        Reservation reservation = findActiveReservationByMatricule(matricule);
        
        if (!reservation.isFuture()) {
            throw new IllegalArgumentException("Seules les réservations futures peuvent être annulées");
        }
        
        reservation.cancel();
        reservationRepository.save(reservation);
    }
    
    private void checkNoActiveReservation(Vehicle vehicle) {
        List<Reservation> activeReservations = reservationRepository.findByVehicle(vehicle)
            .stream()
            .filter(r -> r.getStatus() == ReservationStatus.ACTIVE || r.getStatus() == ReservationStatus.CHECKED_IN)
            .toList();
            
        if (!activeReservations.isEmpty()) {
            throw new IllegalArgumentException("Ce véhicule a déjà une réservation active");
        }
    }
    
    private Reservation findActiveReservationByMatricule(String matricule) {
        return reservationRepository.findByVehicleMatricule(matricule)
            .filter(Reservation::isActive)
            .orElseThrow(() -> new IllegalArgumentException("Aucune réservation active trouvée pour ce véhicule"));
    }
}