/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.Agilesoft.service;

import com.prueba.Agilesoft.entity.Tarea;
import com.prueba.Agilesoft.repository.TareaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ander
 */
@Service
public class TareaService {

    @Autowired    
    private TareaRepository tareaRepository;

    public List<Tarea> getTareaByIdUsuario(int idUsuario) {
        return tareaRepository.findByIdUsuario(idUsuario);
    }
    
    public Tarea getTareaById(int idTarea) {
        return tareaRepository.findByIdTarea(idTarea);
    }

    public Tarea addTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Tarea updateTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void deleteTarea(Tarea tarea) {
        tareaRepository.delete(tarea);
    }
}
