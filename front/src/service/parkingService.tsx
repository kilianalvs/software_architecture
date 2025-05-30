import axios from 'axios';
import { config } from '../config';

export const getAvailableSpots = async (
  startDate: string,
  endDate: string,
  needsElectric: boolean
) => {
  const response = await axios.get(config.PARKING_AVAILABLE, {
    params: {
      start: startDate,
      end: endDate,
      needsElectric,
    },
  });
  
  console.log("👉 GET available spots from:", config.PARKING_AVAILABLE);
  return response.data;
};
