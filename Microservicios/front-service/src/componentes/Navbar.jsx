import React from 'react'
import { Link } from 'react-router-dom';

export const Navbar = () => {
  return (
    <>
    <nav className="navbar bg-dark border-bottom border-body" data-bs-theme="dark">
    <div className="container-fluid">
    <h1 className="navbar-brand">TopEducation</h1>
    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span className="navbar-toggler-icon"></span>
    </button>
    <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div className="navbar-nav">
        <Link className="nav-link active" aria-current="page" to='/'>Inicio</Link>
        <Link className="nav-link" to='/agregar_estudiante'>Registrar</Link>
        <Link className="nav-link" to='/listar_estudiante'>Lista estudiante</Link>
        <Link className="nav-link" to='/agregar_prueba'>Registar Prueba</Link>
        <Link className="nav-link" to='/listar_pruebas'>Listar Pruebas</Link>
        

      </div>
    </div>
  </div>
</nav>
    </>
  )
}
