package pe.edu.upeu.asistencia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

     Long id;
     String nombres;
     String apellidos;
     String correo;
     String token;
     String dni;
     String perfilPrin;
     String estado;
     String offlinex;

}
