/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.Agilesoft.controller;

import com.prueba.Agilesoft.entity.Tarea;
import com.prueba.Agilesoft.service.TareaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ander
 */
@RestController
@RequestMapping("/api/tarea")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<Tarea>> getTareasByUsuario(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(tareaService.getTareasByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Tarea> addTarea(@RequestBody Tarea tarea, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(tareaService.addTarea(tarea, username));
    }

    @PutMapping("/{idTarea}")
    public ResponseEntity<Tarea> updateTarea(@PathVariable Long idTarea, @RequestBody Tarea tarea, Authentication authentication) {
        String username = authentication.getName();
        Tarea oldTarea = tareaService.getTareaByIdTareaAndUsername(idTarea, username);
        if (oldTarea != null) {
            oldTarea.setDescripcion(tarea.getDescripcion());
            oldTarea.setEstado(tarea.getEstado());
            oldTarea.setNombre(tarea.getNombre());
            return ResponseEntity.ok(tareaService.updateTarea(oldTarea));
        } else {
            throw new RuntimeException("Tarea no encontrada o no pertenece al usuario autenticado");
        }
    }

    @DeleteMapping("/{idTarea}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long idTarea, Authentication authentication) {
        String username = authentication.getName();
        Tarea tarea = tareaService.getTareaByIdTareaAndUsername(idTarea, username);
        if (tarea != null) {
            tareaService.deleteTarea(tarea.getIdTarea());
            return ResponseEntity.noContent().build();
        } else {
            throw new RuntimeException("Tarea no encontrada o no pertenece al usuario autenticado");
        }

    }

}
