package com.esgi.notfound.infrastructure.config;

import com.esgi.notfound.application.ParkingApplicationService;
import com.esgi.notfound.application.ReservationApplicationService;
import com.esgi.notfound.domain.ports.ParkingSpotRepository;
import com.esgi.notfound.domain.ports.ReservationRepository;
import com.esgi.notfound.domain.ports.VehicleRepository;
import com.esgi.notfound.domain.services.ParkingAvailabilityService;
import com.esgi.notfound.domain.services.ReservationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public ParkingAvailabilityService parkingAvailabilityService(
            ParkingSpotRepository parkingSpotRepository,
            ReservationRepository reservationRepository) {
        return new ParkingAvailabilityService(parkingSpotRepository, reservationRepository);
    }
    
    @Bean
    public ReservationService reservationService(
            ReservationRepository reservationRepository,
            ParkingSpotRepository parkingSpotRepository,
            VehicleRepository vehicleRepository) {
        return new ReservationService(reservationRepository, parkingSpotRepository, vehicleRepository);
    }
    
    @Bean
    public ParkingApplicationService parkingApplicationService(
            ParkingAvailabilityService parkingAvailabilityService) {
        return new ParkingApplicationService(parkingAvailabilityService);
    }
    
    @Bean
    public ReservationApplicationService reservationApplicationService(
            ReservationService reservationService) {
        return new ReservationApplicationService(reservationService);
    }
}