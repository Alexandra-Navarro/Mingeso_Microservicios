package backendestudiantesservice.backendestudiantesservice.service;

import backendestudiantesservice.backendestudiantesservice.entity.EstudianteEntity;
import backendestudiantesservice.backendestudiantesservice.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;


    public void save(EstudianteEntity estudianteEntity){
        estudianteRepository.save(estudianteEntity);
    }

    public List<EstudianteEntity> findAll(){
        return estudianteRepository.findAll();
    }

    public EstudianteEntity findByRut(String rut){
        return estudianteRepository.findByRut(rut);
    }
}