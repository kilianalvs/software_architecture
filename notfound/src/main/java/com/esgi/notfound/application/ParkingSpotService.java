package com.esgi.notfound.application;

import com.esgi.notfound.domain.enums.ParkingSpotType;
import com.esgi.notfound.infrastructure.repositories.ParkingSpotRepository;
import com.esgi.notfound.infrastructure.entities.ParkingSpotEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;


    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    /**
     * Récupère toutes les places de parking
     */
    @Transactional(readOnly = true)
    public List<ParkingSpotEntity> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }

    /**
     * Récupère une place par ID
     */
    @Transactional(readOnly = true)
    public Optional<ParkingSpotEntity> getParkingSpotById(Long id) {
        return parkingSpotRepository.findById(id);
    }

    /**
     * Récupère les places disponibles
     */
    @Transactional(readOnly = true)
    public List<ParkingSpotEntity> getAvailableParkingSpots() {
        return parkingSpotRepository.findByAvailable(true);
    }

    /**
     * Récupère les places par type (STANDARD, HANDICAPPED, ELECTRIC)
     */
    @Transactional(readOnly = true)
    public List<ParkingSpotEntity> getAvailableParkingSpotsByType(ParkingSpotType type) {
        return parkingSpotRepository.findByTypeAndAvailable(type, true);
    }

    /**
     * Récupère une place par son numéro
     */
    @Transactional(readOnly = true)
    public Optional<ParkingSpotEntity> getParkingSpotByNumber(String number) {
        return parkingSpotRepository.findByNumber(number);
    }

    /**
     * Récupère les places d'une rangée
     */
    @Transactional(readOnly = true)
    public List<ParkingSpotEntity> getParkingSpotsByRow(String rowName) {
        return parkingSpotRepository.findByRowNameOrderByPositionInRow(rowName);
    }

    /**
     * Récupère les places avec recharge électrique disponibles
     */
    @Transactional(readOnly = true)
    public List<ParkingSpotEntity> getAvailableElectricSpots() {
        return parkingSpotRepository.findByHasElectricChargingAndAvailable(true, true);
    }

    /**
     * Crée une nouvelle place de parking
     */
    public ParkingSpotEntity createParkingSpot(String number, ParkingSpotType type, 
                                              String rowName, int positionInRow, 
                                              boolean hasElectricCharging) {
        // Vérifier que le numéro n'existe pas déjà
        if (parkingSpotRepository.findByNumber(number).isPresent()) {
            throw new IllegalArgumentException("Une place avec ce numéro existe déjà");
        }

        ParkingSpotEntity parkingSpot = new ParkingSpotEntity();
        parkingSpot.setNumber(number);
        parkingSpot.setType(type);
        parkingSpot.setRowName(rowName);
        parkingSpot.setPositionInRow(positionInRow);
        parkingSpot.setAvailable(true); // Nouvellement créée, donc disponible
        parkingSpot.setHasElectricCharging(hasElectricCharging);

        return parkingSpotRepository.save(parkingSpot);
    }

    /**
     * Met à jour une place de parking
     */
    public Optional<ParkingSpotEntity> updateParkingSpot(Long id, String number, ParkingSpotType type,
                                                        String rowName, int positionInRow,
                                                        boolean hasElectricCharging) {
        return parkingSpotRepository.findById(id)
                .map(spot -> {
                    // Vérifier que le nouveau numéro n'est pas utilisé par une autre place
                    Optional<ParkingSpotEntity> existingSpot = parkingSpotRepository.findByNumber(number);
                    if (existingSpot.isPresent() && !existingSpot.get().getId().equals(id)) {
                        throw new IllegalArgumentException("Ce numéro est déjà utilisé par une autre place");
                    }

                    spot.setNumber(number);
                    spot.setType(type);
                    spot.setRowName(rowName);
                    spot.setPositionInRow(positionInRow);
                    spot.setHasElectricCharging(hasElectricCharging);
                    return parkingSpotRepository.save(spot);
                });
    }

    /**
     * Réserve une place (la marque comme indisponible)
     */
    public boolean reserveSpot(Long spotId) {
        return parkingSpotRepository.findById(spotId)
                .filter(ParkingSpotEntity::isAvailable)
                .map(spot -> {
                    spot.setAvailable(false);
                    parkingSpotRepository.save(spot);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Libère une place (la marque comme disponible)
     */
    public boolean releaseSpot(Long spotId) {
        return parkingSpotRepository.findById(spotId)
                .map(spot -> {
                    spot.setAvailable(true);
                    parkingSpotRepository.save(spot);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Supprime une place de parking
     */
    public boolean deleteParkingSpot(Long id) {
        if (parkingSpotRepository.existsById(id)) {
            parkingSpotRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Compte les places disponibles par type
     */
    @Transactional(readOnly = true)
    public long countAvailableSpotsByType(ParkingSpotType type) {
        return parkingSpotRepository.countByTypeAndAvailable(type, true);
    }

    /**
     * Compte toutes les places disponibles
     */
    @Transactional(readOnly = true)
    public long countAvailableSpots() {
        return parkingSpotRepository.countByAvailable(true);
    }

    /**
     * Compte le total des places
     */
    @Transactional(readOnly = true)
    public long countTotalSpots() {
        return parkingSpotRepository.count();
    }

    /**
     * Vérifie si une place est disponible
     */
    @Transactional(readOnly = true)
    public boolean isSpotAvailable(Long spotId) {
        return parkingSpotRepository.findById(spotId)
                .map(ParkingSpotEntity::isAvailable)
                .orElse(false);
    }
}