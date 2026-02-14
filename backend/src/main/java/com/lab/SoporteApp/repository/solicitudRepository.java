package com.lab.SoporteApp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.SoporteApp.model.solicitud;
import com.lab.SoporteApp.model.enums.Eestado;
import com.lab.SoporteApp.model.enums.Eprioridad;


public interface solicitudRepository extends JpaRepository<solicitud, Long> {

    List<solicitud> findByEstado(Eestado estado);
    List<solicitud> findByPrioridad(Eprioridad prioridad);

    List<solicitud> findByEstadoAndPrioridad(Eestado estado, Eprioridad prioridad);

    List<solicitud> findByFechaCreacion(LocalDateTime fechaCreacion);

    List<solicitud> findByFechaActualizacion(LocalDateTime fechaActualizacion);
}
