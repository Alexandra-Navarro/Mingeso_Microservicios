import React, { useState, useEffect } from "react";
import EstudianteService from "../services/EstudianteService";
import { Link, useNavigate } from 'react-router-dom';
import '../styles/lista.css'




function ListarEstudiantesComponent() {

    const navigate = useNavigate();

    const [estudianteEntity, setEstudianteEntity] = useState([]);
    //const [input, setInput] = useState(initialState);

    useEffect(() => {
        EstudianteService.getEstudiantes().then((res) => {
            console.log("Response data Estudiante:", res.data);
            setEstudianteEntity(res.data);
            //setInput({ ...input, estudianteEntity: res.data });
        });
    }, []);

    const verCuotas = (rut) => {
        console.log(`Ver cuotas del estudiante con rut: ${rut}`);
        // Asegúrate de que 'navigate' sea una función antes de llamarla
        if (typeof navigate === 'function') {
          navigate(`/listar_cuotas/${rut}`);
        }
      };


    return (
        <div className="container">
            <h1 className="Title"><b>Lista de Estudiantes</b></h1>
            <table className="table table-striped table-bordered custom-table">
                <thead>
                    <tr>
                    <th>RUT</th>
                    <th>Nombres</th>
                    <th>Apellidos</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Nombre de Colegio</th>
                    <th>Tipo de Colegio</th>
                    <th>Años desde egreso</th>
                    <th>Forma de pago</th>
                    <th>Cantidad de cuotas</th>
                    <th>Estado matrícula</th>
                    <th>Ver Cuotas</th>
                    </tr>
                </thead>
              <tbody>
                {estudianteEntity.map((estudiante) => (
                  <tr key={estudiante.rut}>
                        <td>{estudiante.rut}</td>
                        <td>{estudiante.nombre}</td>
                        <td>{estudiante.apellidos}</td>
                        <td>{estudiante.fecha_nacimiento}</td>
                        <td>{estudiante.nombre_colegio}</td>
                        <td>{estudiante.tipo_colegio_procedencia}</td>
                        <td>{estudiante.anio_egreso_colegio}</td>
                        <td>{estudiante.forma_pago}</td>
                        <td>{estudiante.cantidad_cuotase}</td>
                        <td>{estudiante.estado_matricula}</td>
                        <td>
                            <Link to={`/listar_cuotas/${estudiante.rut}`}>
                                <button type="button" onClick={() => verCuotas(estudiante.rut)}>Ver Cuotas</button>
                            </Link>
                        </td>
                  </tr>
                ))}
              </tbody>
            </table>
          
        </div>
      );
    };
export default ListarEstudiantesComponent;




