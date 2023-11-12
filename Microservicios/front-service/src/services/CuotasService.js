import axios from 'axios';

const CUOTAS_API_URL = "http://localhost:8080/cuotas/";

class CuotasService {
  obtenerInformacionCuotas(rut) {
    return axios.get(CUOTAS_API_URL + rut);
  }

  pagarCuota(rut, cuotaIndex) {
    return axios.post(`${CUOTAS_API_URL}${rut}/${cuotaIndex}`);
  }

  eliminarEstudiante(rut) {
    return axios.delete(`${CUOTAS_API_URL}${rut}`);
  }
}

const cuotasService = new CuotasService();

export default cuotasService;