import React, { useState, useEffect } from 'react';
import CuotasService from '../services/CuotasService';
import { useParams} from 'react-router-dom';
import '../styles/contenedor.css'





const GenerarCuotasComponent = ({ match }) => {
    const { rut } = useParams();
    const rutEstudiante = rut;
    console.log(match);
    
  

  const [estudiante, setEstudiante] = useState({});
  const [cuotas, setCuotas] = useState([]);
  const [estados_cuotas_final, setEstadosCuotasFinal] = useState([]);
  const [fechas_limite, setFechasLimite] = useState([]);
  const [meses_atraso, setMesesAtraso] = useState([]);
  const [cuotas_finales, setCuotasFinales] = useState([]);

  useEffect(() => {
    // Llama al servicio para obtener los datos del estudiante y las cuotas
    const fetchData = async () => {
      try {
        const { data } = await CuotasService.obtenerInformacionCuotas(rutEstudiante);
        setEstudiante(data.estudiante);
        setCuotas(data.cuotas);
        setEstadosCuotasFinal(data.estados_cuotas_final);
        setFechasLimite(data.fechas_limite);
        setMesesAtraso(data.meses_atraso);
        setCuotasFinales(data.cuotas_finales);
      } catch (error) {
        console.error('Error al obtener datos:', error);
      }
    };

    fetchData();
  }, [rutEstudiante]);

  const handlePagarCuota = async (cuotaIndex) => {
    try {
      await CuotasService.pagarCuota(rutEstudiante, cuotaIndex);
      // Recarga la página actual
      window.location.reload();
    } catch (error) {
      console.error('Error al pagar cuota:', error);
    }
  };
  
  return (
    <div className="container">
      <h1 className='Title'>Información de Cuotas del Estudiante</h1>
      <div className='card-container'>
        <p>Rut: {estudiante.rut}</p>
        <p>Nombre: {estudiante.nombre}</p>
        <p>Apellidos: {estudiante.apellidos}</p>
      </div>

      <table className="table table-striped table-bordered ">
        <thead className="table1">
          <tr>
            <th>Número de Cuota</th>
            <th>Valor de Cuota</th>
            <th>Estado de Cuota</th>
            <th>Fecha Límite de Pago</th>
            <th>Meses de Atraso</th>
            <th>Valor Cuota Final</th>
            <th>Editar</th>
          </tr>
        </thead>
        <tbody>
          {cuotas.map((cuota, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{cuota}</td>
              <td>{estados_cuotas_final[index]}</td>
              <td>{fechas_limite[index]}</td>
              <td>{meses_atraso[index]}</td>
              <td>{cuotas_finales[index]}</td>
              <td>
                <button
                  type="button"
                  className="btn btn-primary"
                  onClick={() => handlePagarCuota(index)}
                >
                  PAGAR
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default GenerarCuotasComponent;
