/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.Agilesoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.Agilesoft.entity.Usuario;
import com.prueba.Agilesoft.repository.UsuarioRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
class UsuarioControllerTest {

    public static final String NOMBRE = "Anderson";
    public static final String USERNAME = "xlreconlx";
    public static final String PASSWORD = "1234";

    public static final String NOMBRE_SETUP = "Anderson1";
    public static final String USERNAME_SETUP = "xlreconlx1";
    public static final String PASSWORD_SETUP = "12345";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        usuarioRepository.deleteAll();

        Usuario newUser = new Usuario();
        newUser.setUsername(USERNAME_SETUP);
        newUser.setPassword(passwordEncoder.encode(PASSWORD_SETUP));
        newUser.setNombre(NOMBRE_SETUP);
        usuarioRepository.save(newUser);
    }

    @Test
    public void testCreateCreateUsuario() throws Exception {
        Usuario newUser = new Usuario();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);
        newUser.setNombre(NOMBRE);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUser = objectMapper.writeValueAsString(newUser);

        ResponseEntity<Usuario> response = restTemplate.postForEntity("/api/usuario/create", new HttpEntity<>(jsonUser, createJsonHeaders()), Usuario.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(USERNAME, response.getBody().getUsername());
    }

    @Test
    public void testLoginAndReturnToken() throws Exception {
        Usuario newUser = new Usuario();
        newUser.setUsername(USERNAME_SETUP);
        newUser.setPassword(PASSWORD_SETUP);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAuthRequest = objectMapper.writeValueAsString(newUser);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonAuthRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/login", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String token = response.getBody();
        assertNotNull(token);
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
