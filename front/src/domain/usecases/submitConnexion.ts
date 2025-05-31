import { postConnexoin } from "../../service/connexionService";
import { testpostConnexoin } from "../../service/connexionService";

export function submitConnexion(
  login: string,
  password: string,
) {
  return testpostConnexoin(login,password);
}
