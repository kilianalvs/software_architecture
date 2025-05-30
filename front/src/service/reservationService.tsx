import axios from 'axios';
import { config } from '../config';


export const createReservation = async (
  matricule: string,
  parkingSpotId:string,
  startDate: string,
  endDate: string
) => {
  const response = await axios.post(config.RESERVATION_CREATE, null, {
    params: {
      matricule,
      parkingSpotId,
      startDate,
      endDate,
    },
  });
  return response.data;
};
