/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.Agilesoft.repository;

import com.prueba.Agilesoft.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ander
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
    public Usuario findByUsername(String username);
}
