import './App.css';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import { Navbar } from './componentes/Navbar';
import { AgregarEstudianteComponent } from './componentes/AgregarEstudianteComponent';
import { Main } from './componentes/Main';
import ListarEstudiantesComponent from './componentes/ListarEstudiantesComponent';
import GenerarCuotasComponent from './componentes/GenerarCuotasComponent';
import { AgregarPruebasComponent } from './componentes/AgregarPruebasComponent';
import './styles/body.css';
import './styles/title.css'
import ListarPruebasComponent from './componentes/ListarPruebasComponent';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Navbar/>
        <Routes>
          <Route path='/' element={<Main/>}/>
          <Route path='/agregar_estudiante' element={<AgregarEstudianteComponent/>}/>
          <Route path='/listar_estudiante'element={<ListarEstudiantesComponent/>}/>
          <Route path='/listar_cuotas/:rut'element={<GenerarCuotasComponent/>}/>
          <Route path='/agregar_prueba' element={<AgregarPruebasComponent/>}/>
          <Route path='/listar_pruebas' element={<ListarPruebasComponent/>}/>
        </Routes>
      </BrowserRouter>
      
    </div>
  );
}

export default App;
