import axios from 'axios';
import { config } from '../config';

export const postConnexoin = async (
  login: string,
  password: string,
) => {
  const response = await axios.get(config.LOGIN, {
    params: {
      start: login,
      end: password,
    },
  });
  
  console.log("👉 PostConnection:", config.LOGIN);
  return response.data;
};


export const testpostConnexoin = async (
  login: string,
  password: string,
) => {
  // Simulation d'une connexion backend
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (login === "secretaire" && password === "ch@ch0u") {
        resolve({ token: "fake-jwt-token", role: "SECRETARY" });
      } else {
        reject(new Error("Identifiants invalides"));
      }
    }, 500); 
  });
};

