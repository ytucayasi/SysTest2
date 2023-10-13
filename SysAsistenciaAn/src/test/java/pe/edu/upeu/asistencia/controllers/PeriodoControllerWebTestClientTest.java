/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.upeu.asistencia.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.upeu.asistencia.dtos.CredencialesDto;
import pe.edu.upeu.asistencia.dtos.UsuarioCrearDto;
import pe.edu.upeu.asistencia.dtos.UsuarioDto;

/**
 *
 * @author DELL
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PeriodoControllerWebTestClientTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private String token;

    Logger logger = Logger.getLogger(PeriodoControllerWebTestClientTest.class.getName());

    @BeforeEach
    public void setUp() {
        System.out.println("Puerto x:" + this.port);
        UsuarioCrearDto udto = new UsuarioCrearDto("Elias", "Mamani Pari", "eliasmp@upeu.edu.pe",
                "Da12345*", "admin", "43631918", "upeu", "Activo", "SI");

        try {
            var dd = webTestClient.post()
                    .uri("/asis/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new CredencialesDto("eliasmp@upeu.edu.pe", "Da12345*"))//.toCharArray()
                    .exchange()
                    .expectBody(String.class)
                    .returnResult()
                    .getResponseBody();

            JSONObject jsonObj = new JSONObject(dd);
            if (jsonObj.length() > 1) {
                token = jsonObj.getString("token");
            } else {
                if (token == null) {
                    webTestClient.post()
                            .uri("/asis/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(udto)//.toCharArray()
                            .exchange()
                            .expectStatus().isCreated()
                            .expectBody(String.class)
                            .value(tokenx -> {
                                try {
                                    JSONObject jsonObjx = new JSONObject(tokenx);
                                    if (jsonObjx.length() > 1) {
                                        token = jsonObjx.getString("token");
                                    }
                                } catch (JSONException ex) { //JSONException|
                                    logger.log(Level.SEVERE, null, ex);
                                }
                            });
                }
            }

        } catch (JSONException e) {
            System.out.println("saliooooo:" +e.getMessage());
        }

        /* */
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @Order(1)
    public void testListarPeriodo() {
        System.out.println("ddd:" + token);
         webTestClient.get().uri("http://localhost:" + this.port + "/asis/periodo/list")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].nombre").isEqualTo("2023-2")
                .jsonPath("$[0].estado").isEqualTo("Activo")
                .jsonPath("$").isArray()
                .jsonPath("$").value(Matchers.hasSize(2));
    }

}
