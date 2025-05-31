// src/test/java/com/esgi/notfound/domain/services/ReservationServiceTest.java

package com.esgi.notfound.domain.services;

import com.esgi.notfound.application.ReservationService;
import com.esgi.notfound.domain.entities.ParkingSpot;
import com.esgi.notfound.domain.entities.Reservation;
import com.esgi.notfound.domain.entities.Vehicle;
import com.esgi.notfound.domain.enums.ParkingSpotType;
import com.esgi.notfound.domain.enums.ReservationStatus;
import com.esgi.notfound.domain.ports.ParkingSpotRepository;
import com.esgi.notfound.domain.ports.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReservationService Tests")
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    
    @Mock
    private ParkingSpotRepository parkingSpotRepository;
    
    @InjectMocks
    private ReservationService reservationService;
    
    private Vehicle testVehicle;
    private ParkingSpot testParkingSpot;
    private Reservation testReservation;
    private final String TEST_MATRICULE = "AB-123-CD";
    private final Long TEST_PARKING_SPOT_ID = 1L;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate TOMORROW = TODAY.plusDays(1);
    private final LocalDate END_DATE = TODAY.plusDays(3); // 3 jours au lieu de 7

    @BeforeEach
    void setUp() {
        testVehicle = new Vehicle(TEST_MATRICULE);
        testParkingSpot = new ParkingSpot("A1", ParkingSpotType.STANDARD);
        testReservation = new Reservation(testVehicle, testParkingSpot, TOMORROW, END_DATE);
    }

    @Nested
    @DisplayName("Get Reservations By Vehicle")
    class GetReservationsByVehicleTests {

        @Test
        @DisplayName("Should return reservations for existing vehicle")
        void shouldReturnReservationsForExistingVehicle() {
            // Given
            List<Reservation> expectedReservations = List.of(testReservation);
            when(reservationRepository.findByVehicle(any(Vehicle.class)))
                .thenReturn(expectedReservations);

            // When
            List<Reservation> result = reservationService.getReservationsByVehicle(TEST_MATRICULE);

            // Then
            assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(testReservation);
            
            verify(reservationRepository).findByVehicle(argThat(vehicle -> 
                vehicle.getMatricule().equals(TEST_MATRICULE)));
        }

        @Test
        @DisplayName("Should return empty list for vehicle with no reservations")
        void shouldReturnEmptyListForVehicleWithNoReservations() {
            // Given
            when(reservationRepository.findByVehicle(any(Vehicle.class)))
                .thenReturn(Collections.emptyList());

            // When
            List<Reservation> result = reservationService.getReservationsByVehicle(TEST_MATRICULE);

            // Then
            assertThat(result).isEmpty();
            verify(reservationRepository).findByVehicle(any(Vehicle.class));
        }
    }

    @Nested
    @DisplayName("Create Reservation")
    class CreateReservationTests {

        @Test
        @DisplayName("Should successfully create reservation when all conditions are met")
        void shouldCreateReservationSuccessfully() {
            // Given
            when(reservationRepository.findByVehicle(any(Vehicle.class)))
                .thenReturn(Collections.emptyList());
            when(parkingSpotRepository.findById(TEST_PARKING_SPOT_ID))
                .thenReturn(Optional.of(testParkingSpot));
            when(reservationRepository.hasConflictingReservation(TEST_PARKING_SPOT_ID, TOMORROW, END_DATE))
                .thenReturn(false);
            when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(testReservation);

            // When
            Reservation result = reservationService.createReservation(
                TEST_MATRICULE, TEST_PARKING_SPOT_ID, TOMORROW, END_DATE);

            // Then
            assertThat(result).isEqualTo(testReservation);
            verify(reservationRepository).save(any(Reservation.class));
        }

        @Test
        @DisplayName("Should throw exception when parking spot not found")
        void shouldThrowExceptionWhenParkingSpotNotFound() {
            // Given
            when(reservationRepository.findByVehicle(any(Vehicle.class)))
                .thenReturn(Collections.emptyList());
            when(parkingSpotRepository.findById(TEST_PARKING_SPOT_ID))
                .thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> reservationService.createReservation(
                TEST_MATRICULE, TEST_PARKING_SPOT_ID, TOMORROW, END_DATE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Place de parking introuvable");
        }

        @Test
        @DisplayName("Should throw exception when vehicle has active reservation")
        void shouldThrowExceptionWhenVehicleHasActiveReservation() {
            // Given
            Reservation activeReservation = spy(new Reservation(testVehicle, testParkingSpot, TODAY, TOMORROW));
            when(activeReservation.getStatus()).thenReturn(ReservationStatus.ACTIVE);
            
            when(reservationRepository.findByVehicle(any(Vehicle.class)))
                .thenReturn(List.of(activeReservation));

            // When & Then
            assertThatThrownBy(() -> reservationService.createReservation(
                TEST_MATRICULE, TEST_PARKING_SPOT_ID, TOMORROW, END_DATE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ce véhicule a déjà une réservation active");
        }

        @Test
        @DisplayName("Should throw exception when parking spot has conflicting reservation")
        void shouldThrowExceptionWhenParkingSpotHasConflictingReservation() {
            // Given
            when(reservationRepository.findByVehicle(any(Vehicle.class)))
                .thenReturn(Collections.emptyList());
            when(parkingSpotRepository.findById(TEST_PARKING_SPOT_ID))
                .thenReturn(Optional.of(testParkingSpot));
            when(reservationRepository.hasConflictingReservation(TEST_PARKING_SPOT_ID, TOMORROW, END_DATE))
                .thenReturn(true);

            // When & Then
            assertThatThrownBy(() -> reservationService.createReservation(
                TEST_MATRICULE, TEST_PARKING_SPOT_ID, TOMORROW, END_DATE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cette place est déjà réservée pour ces dates");
        }
    }

    @Nested
    @DisplayName("Check-in Reservation")
    class CheckInReservationTests {

        @Test
        @DisplayName("Should successfully check-in reservation on valid date")
        void shouldCheckInReservationSuccessfully() {
            // Given
            Reservation todayReservation = spy(new Reservation(testVehicle, testParkingSpot, TODAY, END_DATE));
            when(todayReservation.isActive()).thenReturn(true);
            
            when(reservationRepository.findByVehicleMatricule(TEST_MATRICULE))
                .thenReturn(Optional.of(todayReservation));
            when(reservationRepository.save(todayReservation))
                .thenReturn(todayReservation);

            // When
            Reservation result = reservationService.checkInReservation(TEST_MATRICULE);

            // Then
            assertThat(result).isEqualTo(todayReservation);
            verify(todayReservation).checkIn();
            verify(reservationRepository).save(todayReservation);
        }

        // @Test
        // @DisplayName("Should throw exception when check-in date is before start date")
        // void shouldThrowExceptionWhenCheckInDateIsBeforeStartDate() {
        //     // Given
        //     LocalDate futureStart = TODAY.plusDays(5);
        //     LocalDate futureEnd = TODAY.plusDays(7);
            
        //     Reservation futureReservation = spy(new Reservation(testVehicle, testParkingSpot, futureStart, futureEnd));
        //     when(futureReservation.isActive()).thenReturn(true);
        //     when(futureReservation.getStartDate()).thenReturn(futureStart);
        //     when(futureReservation.getEndDate()).thenReturn(futureEnd);  // ← MODIFIÉ
            
        //     when(reservationRepository.findByVehicleMatricule(TEST_MATRICULE))
        //         .thenReturn(Optional.of(futureReservation));

        //     // When & Then
        //     assertThatThrownBy(() -> reservationService.checkInReservation(TEST_MATRICULE))
        //         .isInstanceOf(IllegalArgumentException.class)
        //         .hasMessage("Le check-in ne peut se faire que pendant la période de réservation");
        // }


        @Test
        @DisplayName("Should throw exception when no active reservation found")
        void shouldThrowExceptionWhenNoActiveReservationFound() {
            // Given
            when(reservationRepository.findByVehicleMatricule(TEST_MATRICULE))
                .thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> reservationService.checkInReservation(TEST_MATRICULE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Aucune réservation active trouvée pour ce véhicule");
        }
    }

    @Nested
    @DisplayName("Cancel Reservation")
    class CancelReservationTests {

        @Test
        @DisplayName("Should successfully cancel future reservation")
        void shouldCancelFutureReservationSuccessfully() {
            // Given
            Reservation futureReservation = spy(new Reservation(testVehicle, testParkingSpot, TOMORROW, END_DATE));
            when(futureReservation.isActive()).thenReturn(true);
            when(futureReservation.isFuture()).thenReturn(true);
            
            when(reservationRepository.findByVehicleMatricule(TEST_MATRICULE))
                .thenReturn(Optional.of(futureReservation));

            // When
            reservationService.cancelReservation(TEST_MATRICULE);

            // Then
            verify(futureReservation).cancel();
            verify(reservationRepository).save(futureReservation);
        }

        @Test
        @DisplayName("Should throw exception when trying to cancel non-future reservation")
        void shouldThrowExceptionWhenCancellingNonFutureReservation() {
            // Given
            Reservation currentReservation = spy(new Reservation(testVehicle, testParkingSpot, TODAY.minusDays(1), TODAY));
            when(currentReservation.isActive()).thenReturn(true);
            when(currentReservation.isFuture()).thenReturn(false);
            
            when(reservationRepository.findByVehicleMatricule(TEST_MATRICULE))
                .thenReturn(Optional.of(currentReservation));

            // When & Then
            assertThatThrownBy(() -> reservationService.cancelReservation(TEST_MATRICULE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Seules les réservations futures peuvent être annulées");
        }

        @Test
        @DisplayName("Should throw exception when no active reservation found for cancellation")
        void shouldThrowExceptionWhenNoActiveReservationFoundForCancellation() {
            // Given
            when(reservationRepository.findByVehicleMatricule(TEST_MATRICULE))
                .thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> reservationService.cancelReservation(TEST_MATRICULE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Aucune réservation active trouvée pour ce véhicule");
        }
    }
}