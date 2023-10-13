/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.upeu.asistencia.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upeu.asistencia.models.Periodo;
import pe.edu.upeu.asistencia.repositories.PeriodoRepository;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;


/**
 *
 * @author DELL
 */
@ExtendWith(MockitoExtension.class)
public class PeriodoServiceImpTest {

    @Mock
    private PeriodoRepository periodoRepository;

    @InjectMocks
    private PeriodoServiceImp periodoService;

    Periodo periodo;

    @BeforeEach
    public void setUp() {
        periodo = Periodo.builder()
                .id(1L)
                .nombre("2024-1")
                .estado("Activo")
                .build();
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName("Test para guardar un periodo")
    @Test
    public void testSave() {
        System.out.println("save");
        //given       
        given(periodoRepository.save(periodo)).willReturn(periodo);
        //when
        Periodo periodox = periodoService.save(periodo);
        //then
        assertThat(periodox.getNombre()).isNotNull();
        assertThat(periodox.getNombre()).isEqualTo(periodo.getNombre());
    }

    @Test
    public void testFindAll() {
        //given
        Periodo empleado1 = Periodo.builder()
                .id(2L)
                .nombre("2024-2")
                .estado("Activo")
                .build();
        given(periodoRepository.findAll()).willReturn(List.of(periodo, empleado1));
        //when
        List<Periodo> empleados = periodoService.findAll();
        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @Test
    public void testGetPeriodoById() {
        System.out.println("getPeriodoById");
        //given        
        given(periodoRepository.findById(1L)).willReturn(Optional.of(periodo));
        //when
        Periodo periodox = periodoService.getPeriodoById(periodo.getId());
        //then
        assertThat(periodox).isNotNull();
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        // Mock           
        given(periodoRepository.findById(1L)).willReturn(Optional.of(periodo));
        // Llamada a método a testear
        Map<String, Boolean> response = periodoService.delete(1L);
        // Asserts
        assertThat(response.get("deleted")).isEqualTo(true);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        //given
        given(periodoRepository.save(periodo)).willReturn(periodo);
        given(periodoRepository.findById(1L)).willReturn(Optional.of(periodo));

        periodo.setEstado("Inactivo");
        periodo.setNombre("2025-1");
        //when
        Periodo periodoActualizado = periodoService.update(periodo, 1L);
        //then
        assertThat(periodoActualizado.getNombre()).isEqualTo("2025-1");
        assertThat(periodoActualizado.getEstado()).isEqualTo("Inactivo");

    }

}
