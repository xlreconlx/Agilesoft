/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.Agilesoft.service;

import com.prueba.Agilesoft.entity.Tarea;
import com.prueba.Agilesoft.entity.Usuario;
import com.prueba.Agilesoft.repository.TareaRepository;
import com.prueba.Agilesoft.repository.UsuarioRepository;
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
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Tarea> getTareasByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return tareaRepository.findByIdUsuario(usuario.getIdUsuario());
    }
    
    public Tarea getTareaById(Long idTarea) {
        return tareaRepository.findByIdTarea(idTarea);
    }

    public Tarea addTarea(Tarea tarea, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        tarea.setIdUsuario(usuario.getIdUsuario());
        return tareaRepository.save(tarea);
    }

    public Tarea updateTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void deleteTarea(Long idTarea) {
        tareaRepository.deleteById(idTarea);
    }
    
    public Tarea getTareaByIdTareaAndUsername(Long idTarea, String username){
        Usuario usuario = usuarioRepository.findByUsername(username);
        return tareaRepository.findByIdTareaAndIdUsuario(idTarea, usuario.getIdUsuario());
    }
}
