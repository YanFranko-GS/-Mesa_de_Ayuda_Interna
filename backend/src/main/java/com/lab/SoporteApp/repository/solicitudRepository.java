package com.lab.SoporteApp.repository;

import java.time.LocalDate;  // CAMBIADO de LocalDateTime
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.SoporteApp.model.solicitud;
import com.lab.SoporteApp.model.enums.Eestado;
import com.lab.SoporteApp.model.enums.Eprioridad;

public interface solicitudRepository extends JpaRepository<solicitud, Long> {

    List<solicitud> findByEstado(Eestado estado);

    List<solicitud> findByPrioridad(Eprioridad prioridad);

    List<solicitud> findByEstadoAndPrioridad(Eestado estado, Eprioridad prioridad);

    // CORREGIDO: Cambiado de LocalDateTime a LocalDate
    List<solicitud> findByFechaCreacion(LocalDate fechaCreacion);

    // CORREGIDO: Cambiado de LocalDateTime a LocalDate
    List<solicitud> findByFechaActualizacion(LocalDate fechaActualizacion);

    List<solicitud> findByUsuarioEmail(String email);

    List<solicitud> findByUsuarioEmailIgnoreCase(String email);

}