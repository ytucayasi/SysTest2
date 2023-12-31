/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.upeu.asistencia.repositories;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pe.edu.upeu.asistencia.models.Periodo;

/**
 *
 * @author EP-Ing_Sist.-CALIDAD
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PeriodoRepositoryTest {

    @Autowired
    PeriodoRepository periodoRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    public void setUp() {        
        List<Periodo> local = periodoRepository.findAll();  
       if(local.isEmpty()){
       Periodo periodo = Periodo.builder()
                .nombre("2021-2")              
                .estado("Inactivo")
                .build();
        testEntityManager.persist(periodo);        
        } 
    }

    @AfterEach
    public void tearDown() {
    }


    @Order(1)
    @DisplayName("Test para guardar un periodo")
    @Test
    public void testGuardarPeriodo() {
        //given - dado o condición previa o configuración
        Periodo periodo1 = Periodo.builder()
                .nombre("2021-2")
                .estado("Inactivo")
                .build();
        //when - acción o el comportamiento que vamos a probar
        Periodo periodoGuardado = periodoRepository.save(periodo1);
        //then - verificar la salida
        assertThat(periodoGuardado).isNotNull();
        assertEquals(periodoGuardado.getNombre(),periodo1.getNombre());
       
    }
    
    @Order(2)
    @Test
    public void findLocalByNameIgnoreCaseNotFound() {
        //when
        Optional<Periodo> local = periodoRepository.findByNombre("2021-2");
        //assertEquals(local, Optional.empty());
        assertEquals(local.get().getNombre(),"2021-2");
        
        //fail("The test case is a prototype.");
    }  
    
    @Order(3)
    @Test
    public void testListarPeriodo() {
        //when
        List<Periodo> lista=periodoRepository.findAll();               
        //then 
        assertEquals(lista.size(), 0);
    }
    
    @Order(4)
    @Test
    public void testActualizarPeriodo() { 
        //when
        Optional<Periodo> periodox = periodoRepository.findByNombre("2021-2");
        periodox.get().setEstado("Activo");
        Periodo periodoGuardado = periodoRepository.save(periodox.get());
        //then 
        assertEquals(periodoGuardado.getEstado(),"Activo");
        
    }    
    
    @Order(5)
    @Test
    public void testEliminarPeriodo() {  
        //when
        Optional<Periodo> periodox = periodoRepository.findByNombre("2021-2");        
        periodoRepository.delete(periodox.get());
        List<Periodo> lista=periodoRepository.findAll();  
        //then
        assertEquals(lista.size(),1);
        
    }     

}
