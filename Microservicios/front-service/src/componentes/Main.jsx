import React from 'react';
import { Link } from 'react-router-dom';
import Estudiantes from "../images/estudiantes.png"
import Lista from "../images/lista.png"
import Prueba from "../images/prueba.png"
import ListaP from "../images/listap.png"


const imageStyle = {
    width: '130px', // Establece el ancho deseado en píxeles
    height: '130px', // Establece la altura deseada en píxeles
    margin: 'auto', // Centra horizontalmente
    display: 'block', // Permite el centrado horizontal
  };

export const Main = () => {
  return (

    
    <div className='container'>
    <h1 className='Title'>Bienvenido a TopEducation</h1>
      <div className="row mt-5">

        <div className="col-md-6">
          <div className="card">
          <img src={Estudiantes} alt="Imagen 1" className="card-img-top" style={imageStyle} />
            <div className="card-body">
              <p className="card-text">Haz clic aquí para registrar estudiantes.</p>
              <Link to="/agregar_estudiante" className="btn btn-primary">Registrar Estudiantes</Link>
            </div>
          </div>
        </div>

        <div className="col-md-6">
          <div className="card">
          <img src={Lista} alt="Imagen 1" className="card-img-top" style={imageStyle} />
            <div className="card-body">
              <p className="card-text">Haz clic aquí para ver la lista de estudiantes.</p>
              <Link to="/listar_estudiante" className="btn btn-primary">Lista de Estudiantes</Link>
            </div>
          </div>
        </div>

        <div className="col-md-6">
          <div className="card">
          <img src={Prueba} alt="Imagen 1" className="card-img-top" style={imageStyle} />
            <div className="card-body">
              <p className="card-text">Haz clic aquí para registrar pruebas.</p>
              <Link to="/agregar_prueba" className="btn btn-primary">Registrar Pruebas</Link>
            </div>
          </div>
        </div>

        <div className="col-md-6">
          <div className="card">
          <img src={ListaP} alt="Imagen 1" className="card-img-top" style={imageStyle} />
            <div className="card-body">
              <p className="card-text">Haz clic aquí para ver lista de pruebas.</p>
              <Link to="/listar_pruebas" className="btn btn-primary">Lista de pruebas</Link>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

