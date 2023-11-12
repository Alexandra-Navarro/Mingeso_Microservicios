import axios from 'axios';

const PRUEBA_API_URL = "http://localhost:8085/pruebas/";


class EstudianteService {

    getListarPruebas(){
        return axios.get(PRUEBA_API_URL);
    }

    createPrueba(prueba){
        return axios.post(PRUEBA_API_URL,prueba);
    }
    createExcel(fecha) {
        return axios.get(`${PRUEBA_API_URL}fecha?fecha=${fecha}`, { responseType: 'blob' });
    }
}

const PruebasService = new EstudianteService(); // Crear una instancia de la clase

export default PruebasService; 