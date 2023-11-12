import React, { useState, useEffect } from "react";
import PruebasService from "../services/PruebasService";
import '../styles/lista.css'




function ListarPruebasComponent() {
    const [pruebasEntity, setPruebasEntity] = useState([]);
    const [selectedDate, setSelectedDate] = useState("");

    useEffect(() => {
      // Corrige aquí, agrega paréntesis para llamar a la función
      PruebasService.getListarPruebas().then((res) => {
          console.log("Response data Estudiante:", res.data);
          setPruebasEntity(res.data);
      });
  }, []);

  const exportToExcel = async () => {
    try {
        const response = await PruebasService.createExcel(selectedDate);
        const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);

        // Crear un enlace temporal y hacer clic en él para iniciar la descarga
        const a = document.createElement('a');
        a.href = url;
        a.download = `pruebas_${selectedDate}.xlsx`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    } catch (error) {
        console.error("Error al exportar a Excel:", error);
    }
};

  

  

    return (
      <div>
          <div className="container">

              <h1 className="Title"><b>Lista de Pruebas</b></h1>
              <br />
              <table className="table table-striped table-bordered custom-table">
                  <thead>
                      <tr>
                          <th>Rut estudiante</th>
                          <th>Asignatura del examen</th>
                          <th>Fecha de examen</th>
                          <th>Puntaje Obtenido</th>
                      </tr>
                  </thead>
                  <tbody>
                      {pruebasEntity.map((prueba) => (
                          <tr key={prueba.rut_estudiante}>
                              <td>{prueba.rut_estudiante}</td>
                              <td>{prueba.asignatura_examen}</td>
                              <td>{prueba.fecha_examen}</td>
                              <td>{prueba.puntaje_obtenido}</td>
                          </tr>
                      ))}
                  </tbody>
              </table>
          </div>
          <div className="container">
            <h1 className="Title">Seleccionar Fecha para Exportar a Excel</h1>
            <div className="form-group">
              <br />
                <input
                    type="date"
                    id="fecha"
                    name="fecha"
                    className="form-control"
                    required
                    value={selectedDate}
                    onChange={(e) => setSelectedDate(e.target.value)}
                />
            </div>
            <br />
            <button className="btn btn-success" onClick={exportToExcel}>Generar Excel</button>
        </div>
    </div>
  );
}

export default ListarPruebasComponent;


