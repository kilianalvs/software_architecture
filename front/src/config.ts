const apiUrl = 'http://localhost:8080/api';

export const config = {
  PARKING_AVAILABLE: `${apiUrl}/parking/available`,
  RESERVATION_CREATE: `${apiUrl}/reservations`,
  RESERVATION_BY_MATRICULE: (matricule: string) => `${apiUrl}/reservations/${matricule}`,

  LOGIN:`${apiUrl}/user/login`
};
