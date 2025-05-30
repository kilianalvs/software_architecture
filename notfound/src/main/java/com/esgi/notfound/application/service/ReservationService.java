package com.esgi.notfound.application.service;

import com.esgi.notfound.domain.entity.Reservation;
import com.esgi.notfound.domain.entity.Parking;
import com.esgi.notfound.domain.entity.User;
import com.esgi.notfound.domain.;;
import com.esgi.notfound.domain.repository.ParkingRepository;
import com.esgi.notfound.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.esgi.notfound.domain.repository.ReservationRepository;  // ← CELUI-CI MANQUE


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;
    private final ParkingService parkingService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                            ParkingRepository parkingRepository,
                            UserRepository userRepository,
                            ParkingService parkingService) {
        this.reservationRepository = reservationRepository;
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
        this.parkingService = parkingService;
    }

    /**
     * Récupère toutes les réservations
     */
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Récupère une réservation par ID
     */
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    /**
     * Récupère les réservations d'un utilisateur
     */
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    /**
     * Récupère les réservations actives d'un utilisateur
     */
    @Transactional(readOnly = true)
    public List<Reservation> getActiveReservationsByUser(Long userId) {
        return reservationRepository.findByUserIdAndEndTimeAfter(userId, LocalDateTime.now());
    }

    /**
     * Récupère les réservations d'un parking
     */
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByParking(Long parkingId) {
        return reservationRepository.findByParkingId(parkingId);
    }

    /**
     * Crée une nouvelle réservation
     */
    public Optional<Reservation> createReservation(Long userId, Long parkingId, 
                                                 LocalDateTime startTime, LocalDateTime endTime) {
        
        // Vérifications
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        }
        
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le passé");
        }

        // Récupération des entités
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
        
        if (parkingOpt.isEmpty()) {
            throw new IllegalArgumentException("Parking non trouvé");
        }

        Parking parking = parkingOpt.get();
        
        // Vérification de la disponibilité
        if (parking.getAvailableSpaces() <= 0) {
            throw new IllegalArgumentException("Aucune place disponible dans ce parking");
        }

        // Calcul du prix
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        if (hours == 0) hours = 1; // Minimum 1 heure
        double totalPrice = hours * parking.getHourlyRate();

        // Création de la réservation
        Reservation reservation = new Reservation();
        reservation.setUser(userOpt.get());
        reservation.setParking(parking);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setTotalPrice(totalPrice);

        // Réservation de la place
        if (parkingService.reserveSpace(parkingId)) {
            return Optional.of(reservationRepository.save(reservation));
        } else {
            throw new RuntimeException("Erreur lors de la réservation de la place");
        }
    }

    /**
     * Annule une réservation
     */
    public boolean cancelReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> {
                    // Libération de la place
                    parkingService.releaseSpace(reservation.getParking().getId());
                    
                    // Suppression de la réservation
                    reservationRepository.delete(reservation);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Termine une réservation (check-out)
     */
    public Optional<Reservation> endReservation(Long reservationId, LocalDateTime actualEndTime) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> {
                    // Mise à jour de l'heure de fin réelle
                    if (actualEndTime.isAfter(reservation.getEndTime())) {
                        // Recalcul du prix si dépassement
                        long totalHours = ChronoUnit.HOURS.between(
                            reservation.getStartTime(), actualEndTime);
                        if (totalHours == 0) totalHours = 1;
                        
                        double newPrice = totalHours * reservation.getParking().getHourlyRate();
                        reservation.setTotalPrice(newPrice);
                    }
                    
                    reservation.setEndTime(actualEndTime);
                    
                    // Libération de la place
                    parkingService.releaseSpace(reservation.getParking().getId());
                    
                    return reservationRepository.save(reservation);
                });
    }

    /**
     * Compte les réservations actives
     */
    @Transactional(readOnly = true)
    public long countActiveReservations() {
        return reservationRepository.countByEndTimeAfter(LocalDateTime.now());
    }
}