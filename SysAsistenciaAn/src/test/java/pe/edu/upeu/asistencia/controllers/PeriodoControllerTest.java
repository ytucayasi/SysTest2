/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.upeu.asistencia.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import pe.edu.upeu.asistencia.models.Periodo;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import static org.mockito.BDDMockito.given;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import pe.edu.upeu.asistencia.services.PeriodoServiceImp;

/**
 *
 * @author DELL
 */
@ExtendWith(MockitoExtension.class)
class PeriodoControllerTest {

    @Mock
    private PeriodoServiceImp periodoService;

    @InjectMocks
    private PeriodoController controller;

    Periodo periodo;
    List<Periodo> periodos;

    @BeforeEach
    public void setUp() {
        periodo = Periodo.builder()
                .id(1L)
                .nombre("2024-1")
                .estado("Activo")
                .build();
        periodos = Arrays.asList(
                periodo,
                Periodo.builder()
                        .id(2L)
                        .nombre("2024-2")
                        .estado("Activo")
                        .build()
        );
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testListPeriodo() {
        //given
        given(periodoService.findAll()).willReturn(periodos);
        //when
        ResponseEntity<List<Periodo>> response = controller.listPeriodo();
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(periodos, response.getBody());
    }

    @Test
    public void testCreatePeriodo() {
        //given
        given(periodoService.save(periodo)).willReturn(periodo);
        //when
        ResponseEntity<Periodo> response = controller.createPeriodo(periodo);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), periodo);
    }

    @Test
    public void testGetPeriodoById() {
        given(periodoService.getPeriodoById(1L)).willReturn(periodo);
        ResponseEntity<Periodo> response = controller.getPeriodoById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), periodo);
    }

    @Test
    public void testDeletePeriodo() {
        // Mock
        //given
        given(periodoService.getPeriodoById(1L)).willReturn(periodo);
        Map<String, Boolean> respuestaEsperada = new HashMap<>();
        respuestaEsperada.put("deleted", true);
        given(periodoService.delete(1L)).willReturn(respuestaEsperada);
        //when
        ResponseEntity<Map<String, Boolean>> respuesta = controller.deletePeriodo(1L);
        //then
        // Asserts
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(respuestaEsperada, respuesta.getBody());
        Mockito.verify(periodoService).getPeriodoById(1L);
        Mockito.verify(periodoService).delete(1L);
    }

    @Test
    public void testUpdatePeriodo() {
        System.out.println("update");
        //given        
        given(periodoService.update(periodo, 1L)).willReturn(periodo);        
        periodo.setEstado("Inactivo");
        periodo.setNombre("2025-1");        
        //when
        ResponseEntity<Periodo> periodoActualizado = controller.updatePeriodo(1L, periodo);
        //then
        assertEquals(HttpStatus.OK, periodoActualizado.getStatusCode());
        assertEquals(periodo, periodoActualizado.getBody());        
    }

}
