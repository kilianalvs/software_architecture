import { createReservation } from '../../service/reservationService';

export function submitReservation(
  matricule: string,
  parkingSpotId: number,
  dateDebut: string,
  dateFin: string
) {
  return createReservation(matricule, parkingSpotId, dateDebut, dateFin);
}
