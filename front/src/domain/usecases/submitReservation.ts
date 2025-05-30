import { createReservation } from '../../service/reservationService';

export function submitReservation(
  matricule: string,
  parkingSpotId: string,
  dateDebut: string,
  dateFin: string
) {
  return createReservation(matricule, parkingSpotId, dateDebut, dateFin);
}
