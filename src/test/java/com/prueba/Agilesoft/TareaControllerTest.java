/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.Agilesoft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.prueba.Agilesoft.UsuarioControllerTest.PASSWORD_SETUP;
import static com.prueba.Agilesoft.UsuarioControllerTest.USERNAME_SETUP;
import com.prueba.Agilesoft.controller.AuthenticationRequest;
import com.prueba.Agilesoft.entity.Tarea;
import com.prueba.Agilesoft.entity.Usuario;
import com.prueba.Agilesoft.repository.UsuarioRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ander
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TareaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String jwtToken;
    public static final String NOMBRE_SETUP = "Anderson1";
    public static final String USERNAME_SETUP = "xlreconlx1";
    public static final String PASSWORD_SETUP = "12345";

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        usuarioRepository.deleteAll();

        Usuario newUser = new Usuario();
        newUser.setUsername(USERNAME_SETUP);
        newUser.setPassword(passwordEncoder.encode(PASSWORD_SETUP));
        newUser.setNombre(NOMBRE_SETUP);
        usuarioRepository.save(newUser);

        Usuario usuario = new Usuario();
        usuario.setUsername(USERNAME_SETUP);
        usuario.setPassword(PASSWORD_SETUP);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAuthRequest = objectMapper.writeValueAsString(usuario);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonAuthRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/login", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        jwtToken = response.getBody();
        assertNotNull(jwtToken);
    }

    @Test
    public void testGetTareasByUsuario() throws Exception {
        Tarea tarea = new Tarea(null, "Tarea 1", "Descripción 1", "No Resuelto");
        restTemplate.postForEntity("/api/tarea",
                new HttpEntity<>(tarea, getAuthHeaders()), Tarea.class);

        ResponseEntity<List<Tarea>> response = restTemplate.exchange("/api/tarea",
                HttpMethod.GET,
                new HttpEntity<>(getAuthHeaders()),
                new ParameterizedTypeReference<List<Tarea>>() {
        }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Tarea> tareas = response.getBody();
        assertNotNull(tareas);
        assertEquals(1, tareas.size());
        assertEquals("Tarea 1", tareas.get(0).getNombre());
    }

    @Test
    public void testAddTarea() throws Exception {
        Tarea tarea = new Tarea(null, "Nueva Tarea", "Nueva Descripción", "PENDIENTE");

        ResponseEntity<Tarea> response = restTemplate.postForEntity("/api/tarea",
                new HttpEntity<>(tarea, getAuthHeaders()),
                Tarea.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Tarea createdTarea = response.getBody();
        assertNotNull(createdTarea);
        assertEquals("Nueva Tarea", createdTarea.getNombre());
    }
    
    @Test
    public void testUpdateTarea() throws Exception {
        Tarea tarea = new Tarea(null, "Tarea Existente", "Descripción Existente", "PENDIENTE");
        ResponseEntity<Tarea> createdResponse = restTemplate.postForEntity("/api/tarea",
                new HttpEntity<>(tarea, getAuthHeaders()),
                Tarea.class
        );

        Long tareaId = createdResponse.getBody().getIdTarea();
        Tarea updatedTarea = new Tarea(tareaId, "Tarea Actualizada", "Descripción Actualizada", "COMPLETA");

        ResponseEntity<Tarea> updateResponse = restTemplate.exchange("/api/tarea/" + tareaId,
                HttpMethod.PUT,
                new HttpEntity<>(updatedTarea, getAuthHeaders()),
                Tarea.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Tarea resultTarea = updateResponse.getBody();
        assertNotNull(resultTarea);
        assertEquals("Tarea Actualizada", resultTarea.getNombre());
    }
    
    @Test
    public void testDeleteTarea() throws Exception {
        Tarea tarea = new Tarea(null, "Tarea a Borrar", "Descripción a Borrar", "PENDIENTE");
        ResponseEntity<Tarea> createdResponse = restTemplate.postForEntity("/api/tarea",
                new HttpEntity<>(tarea, getAuthHeaders()),
                Tarea.class
        );

        Long tareaId = createdResponse.getBody().getIdTarea();

        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/tarea/" + tareaId,
                HttpMethod.DELETE,
                new HttpEntity<>(getAuthHeaders()),
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        return headers;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
