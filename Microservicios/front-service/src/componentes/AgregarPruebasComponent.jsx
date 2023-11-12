import React, { useState } from 'react';
import PruebasService from '../services/PruebasService';// Ajusta la ruta según tu estructura de archivos
import Swal from 'sweetalert2';


export const AgregarPruebasComponent = () => {
        const [rut_estudiante, setRut] = useState('');
        const [asignatura_examen, setAsignaturaExamen] = useState('');
        const [fecha_examen, setFechaExamen] = useState('');
        const [puntaje_obtenido, setPuntajeObtenido] = useState('');
      
        const createPrueba = async () => {
          Swal.fire({
            title: "¿Desea registrar la prueba?",
            text: "No podra cambiarse en caso de equivocación",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
          }).then((result) => {
            if (result.isConfirmed) {
              const newPrueba = {
                rut_estudiante,
                asignatura_examen,
                fecha_examen,
                puntaje_obtenido,
              };
        
              console.log(newPrueba);
              PruebasService.createPrueba(newPrueba);
              
        
              Swal.fire({
                title: "Enviado",
                timer: 2000,
                icon: "success",
                timerProgressBar: true,
                didOpen: () => {
                  Swal.showLoading();
                },
              });
              // Clear the input fields
              setRut('');
              setAsignaturaExamen('');
              setFechaExamen('');
              setPuntajeObtenido('');
              
            }
          });
        };
        return (
            <>
              <div className="container text-center mt-4">
                <h1 className='Title'>Registro de pruebas</h1>
              </div>
              <div className="container d-flex justify-content-center mt-4">
                <div className="col-lg-6 col-md-6 col-sm-6 card">
                  <div className="card-body">
                    <table className="container">
                      <tbody>
                        <tr>
                          <td>Rut estudiante:</td>
                          <td><input type="text" className="form-control" value={rut_estudiante} onChange={(e) => setRut(e.target.value)} /></td>
                        </tr>

                        <tr>
                          <td>Asignatura:</td>
                          <td>
                            <select className="form-select" value={asignatura_examen} onChange={(e) => setAsignaturaExamen(e.target.value)}>
                              <option value="">Seleccionar...</option>
                              <option value="Matemáticas">Matematicas</option>
                              <option value="Lenguaje">Lenguaje</option>
                              <option value="Ciencias">Ciencias</option>
                              <option value="Historia">Historia</option>
                            </select>
                          </td>
                        </tr>

                        <tr>
                          <td>Fecha de Examen:</td>
                          <td><input type="date" className="form-control" value={fecha_examen} onChange={(e) => setFechaExamen(e.target.value)} /></td>
                        </tr>
                        
                        <tr>
                          <td>Puntaje Obtenido:</td>
                          <td><input type="number" className="form-control" value={puntaje_obtenido} onChange={(e) => setPuntajeObtenido(e.target.value)} /></td>
                        </tr>
                        
                      </tbody>
                    </table>
                    <br></br>
                    <button className="btn btn-primary" onClick={createPrueba}>
                      Registrar prueba
                    </button>
                  </div>
                </div>
                <div style={{ marginBottom: '20px' }}></div>
              </div>
              
            </>
          );
    };



