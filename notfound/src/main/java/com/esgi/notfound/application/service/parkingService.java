package com.esgi.notfound.application.service;

import com.esgi.notfound.domain.entity.Parking;
import com.esgi.notfound.domain.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    /**
     * Récupère tous les parkings
     */
    @Transactional(readOnly = true)
    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    /**
     * Récupère un parking par ID
     */
    @Transactional(readOnly = true)
    public Optional<Parking> getParkingById(Long id) {
        return parkingRepository.findById(id);
    }

    /**
     * Récupère les parkings par ville
     */
    @Transactional(readOnly = true)
    public List<Parking> getParkingsByCity(String city) {
        return parkingRepository.findByCity(city);
    }

    /**
     * Récupère les parkings disponibles (avec places libres)
     */
    @Transactional(readOnly = true)
    public List<Parking> getAvailableParkings() {
        return parkingRepository.findAvailableParkings();
    }

    /**
     * Crée un nouveau parking
     */
    public Parking createParking(String name, String address, String city, 
                                int totalSpaces, double hourlyRate) {
        Parking parking = new Parking();
        parking.setName(name);
        parking.setAddress(address);
        parking.setCity(city);
        parking.setTotalSpaces(totalSpaces);
        parking.setAvailableSpaces(totalSpaces); // Initialement toutes les places sont libres
        parking.setHourlyRate(hourlyRate);
        
        return parkingRepository.save(parking);
    }

    /**
     * Met à jour un parking
     */
    public Optional<Parking> updateParking(Long id, String name, String address, 
                                         String city, int totalSpaces, double hourlyRate) {
        return parkingRepository.findById(id)
                .map(parking -> {
                    parking.setName(name);
                    parking.setAddress(address);
                    parking.setCity(city);
                    parking.setTotalSpaces(totalSpaces);
                    parking.setHourlyRate(hourlyRate);
                    return parkingRepository.save(parking);
                });
    }

    /**
     * Supprime un parking
     */
    public boolean deleteParking(Long id) {
        if (parkingRepository.existsById(id)) {
            parkingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Réserve une place (diminue les places disponibles)
     */
    public boolean reserveSpace(Long parkingId) {
        return parkingRepository.findById(parkingId)
                .filter(parking -> parking.getAvailableSpaces() > 0)
                .map(parking -> {
                    parking.setAvailableSpaces(parking.getAvailableSpaces() - 1);
                    parkingRepository.save(parking);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Libère une place (augmente les places disponibles)
     */
    public boolean releaseSpace(Long parkingId) {
        return parkingRepository.findById(parkingId)
                .filter(parking -> parking.getAvailableSpaces() < parking.getTotalSpaces())
                .map(parking -> {
                    parking.setAvailableSpaces(parking.getAvailableSpaces() + 1);
                    parkingRepository.save(parking);
                    return true;
                })
                .orElse(false);
    }
}
