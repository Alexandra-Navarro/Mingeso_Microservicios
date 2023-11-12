import React, { useState } from 'react';
import EstudianteService from '../services/EstudianteService';
import Swal from 'sweetalert2';



export const AgregarEstudianteComponent = () => {
  const [rut, setRut] = useState('');
  const [nombre, setNombre] = useState('');
  const [apellidos, setApellidos] = useState('');
  const [fecha_nacimiento, setFechaNacimiento] = useState('');
  const [nombre_colegio, setNombreColegio] = useState('');
  const [tipo_colegio_procedencia, setTipoColegio] = useState('');
  const [anio_egreso_colegio, setAnoEgreso] = useState('');
  const [forma_pago, setFormaPago] = useState('');
  const [cantidad_cuotase, setCantidadCuotas] = useState('');
  const [estado_matricula, setEstadoMatricula] = useState('');

  const createEstudiante = async () => {
    Swal.fire({
      title: "¿Desea registrar el estudiante?",
      text: "No podra cambiarse en caso de equivocación",
      icon: "question",
      showDenyButton: true,
      confirmButtonText: "Confirmar",
      confirmButtonColor: "rgb(68, 194, 68)",
      denyButtonText: "Cancelar",
      denyButtonColor: "rgb(190, 54, 54)",
    }).then((result) => {
      if (result.isConfirmed) {
        const newEstudiante = {
          rut,
          nombre,
          apellidos,
          fecha_nacimiento,
          nombre_colegio,
          tipo_colegio_procedencia,
          anio_egreso_colegio,
          forma_pago, // Add this field if necessary
          cantidad_cuotase, // Add this field if necessary
          estado_matricula, // Add this field if necessary
        };
  
        console.log(newEstudiante);
        EstudianteService.createEstudiante(newEstudiante);
        
  
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
        setNombre('');
        setApellidos('');
        setFechaNacimiento('');
        setNombreColegio('');
        setTipoColegio('');
        setAnoEgreso('');
        setFormaPago('');
        setCantidadCuotas('');
        setEstadoMatricula('');
      }
    });
  };
  
  return (
    <>
      <div className="container text-center mt-4">
        <h1 className='Title'>Formulario de Registro</h1>
      </div>
      <div className="container d-flex justify-content-center mt-4">
        <div className="col-lg-6 col-md-6 col-sm-6 card">
          <div className="card-body">
            <table className="container">
              <tbody>
                <tr>
                  <td>RUT:</td>
                  <td><input type="text" className="form-control" value={rut} onChange={(e) => setRut(e.target.value)} /></td>
                </tr>
                <tr>
                  <td>Nombre:</td>
                  <td><input type="text" className="form-control" value={nombre} onChange={(e) => setNombre(e.target.value)} /></td>
                </tr>
                <tr>
                  <td>Apellidos:</td>
                  <td><input type="text" className="form-control" value={apellidos} onChange={(e) => setApellidos(e.target.value)} /></td>
                </tr>
                <tr>
                  <td>Fecha de Nacimiento:</td>
                  <td><input type="date" className="form-control" value={fecha_nacimiento} onChange={(e) => setFechaNacimiento(e.target.value)} /></td>
                </tr>
                <tr>
                  <td>Nombre del Colegio:</td>
                  <td><input type="text" className="form-control" value={nombre_colegio} onChange={(e) => setNombreColegio(e.target.value)} /></td>
                </tr>
                <tr>
                  <td>Tipo de Colegio:</td>
                  <td>
                    <select className="form-select" value={tipo_colegio_procedencia} onChange={(e) => setTipoColegio(e.target.value)}>
                      <option value="">Seleccionar...</option>
                      <option value="Publico">Municipal</option>
                      <option value="Subvencionado">Subvencionado</option>
                      <option value="Privado">Privado</option>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td>Años de que Egresó:</td>
                  <td><input type="number" className="form-control" value={anio_egreso_colegio} onChange={(e) => setAnoEgreso(e.target.value)} /></td>
                </tr>
                <tr>
                  <td>Forma de Pago:</td>
                  <td>
                    <select className="form-select" value={forma_pago} onChange={(e) => setFormaPago(e.target.value)}>
                      <option value="">Seleccionar...</option>
                      <option value="Contado">Contado</option>
                      <option value="Cuotas">Cuotas</option>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td>Cantidad de Cuotas:</td>
                  <td><input type="number" className="form-control" value={cantidad_cuotase} onChange={(e) => setCantidadCuotas(e.target.value)} /></td>
                </tr>
                <tr>
                  <td>Estado de Matrícula:</td>
                  <td>
                    <select className="form-select" value={estado_matricula} onChange={(e) => setEstadoMatricula(e.target.value)}>
                      <option value="">Seleccionar...</option>
                      <option value="Pendiente">Pendiente</option>
                      <option value="Pagada">Pagada</option>
                    </select>
                  </td>
                </tr>
              </tbody>
            </table>
            <br></br>
            <button className="btn btn-primary" onClick={createEstudiante}>
              Registrar
            </button>
          </div>
        </div>
        <div style={{ marginBottom: '20px' }}></div>
      </div>
      
    </>
  );
}
    
