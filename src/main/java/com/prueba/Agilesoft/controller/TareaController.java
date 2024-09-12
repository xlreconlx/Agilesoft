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
    public ResponseEntity<List<Tarea>> getTareasByUsuario(@RequestParam int idUsuario) {
        return ResponseEntity.ok(tareaService.getTareaByIdUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<Tarea> addTarea(@RequestBody Tarea task) {
        return ResponseEntity.ok(tareaService.addTarea(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> updateTarea(@PathVariable Long idTarea, @RequestBody Tarea tarea) {
        tarea.setIdTarea(idTarea);
        return ResponseEntity.ok(tareaService.updateTarea(tarea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long idTarea) {
        tareaService.deleteTarea(idTarea);
        return ResponseEntity.noContent().build();
    }

}
