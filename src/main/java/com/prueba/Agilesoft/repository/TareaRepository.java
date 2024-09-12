/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.prueba.Agilesoft.repository;

import com.prueba.Agilesoft.entity.Tarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ander
 */
public interface TareaRepository extends JpaRepository<Tarea, Long>{
    public List<Tarea> findByIdUsuario(int idUsuario);
    public Tarea findByIdTarea(int idTarea);
}
