package com.lab.SoporteApp.DTO;

import com.lab.SoporteApp.model.enums.Eestado;
import com.lab.SoporteApp.model.enums.Eprioridad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SolicitudDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Eprioridad prioridad;
    private Eestado estado;
    private Long usuarioId;
    private String usuarioNombre;
    private String usuarioEmail;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
}