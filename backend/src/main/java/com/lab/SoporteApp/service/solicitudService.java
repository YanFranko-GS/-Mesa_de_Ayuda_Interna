package com.lab.SoporteApp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab.SoporteApp.model.solicitud;
import com.lab.SoporteApp.model.enums.Eestado;
import com.lab.SoporteApp.model.enums.Eprioridad;
import com.lab.SoporteApp.repository.solicitudRepository;

@Service
public class solicitudService {

    @Autowired
    private solicitudRepository solicitudRepo;

    public solicitud crear(solicitud sol) {
        sol.setFechaCreacion(LocalDate.now());
        sol.setFechaActualizacion(LocalDate.now());
        sol.setEstado(Eestado.NUEVO);
        return solicitudRepo.save(sol);
    }

    public List<solicitud> listar() {
        return solicitudRepo.findAll();
    }

    public solicitud obtenerPorId(Long id) {
        return solicitudRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    public solicitud actualizar(Long id, solicitud nueva) {

        solicitud actual = obtenerPorId(id);

        
        if (actual.getEstado() == Eestado.CERRADO) {
            throw new RuntimeException("No se puede modificar una solicitud cerrada");
        }

        actual.setTitulo(nueva.getTitulo());
        actual.setDescripcion(nueva.getDescripcion());
        actual.setPrioridad(nueva.getPrioridad());
        actual.setEstado(nueva.getEstado());
        actual.setFechaActualizacion(LocalDate.now());

        return solicitudRepo.save(actual);
    }

    public void eliminar(Long id) {
        solicitudRepo.deleteById(id);
    }

    public List<solicitud> buscarFechaActualizacion(LocalDateTime fechaActualizacion) {
        return solicitudRepo.findByFechaActualizacion(fechaActualizacion);
    }

    public List<solicitud> buscarEstado(Eestado estado) {
        return solicitudRepo.findByEstado(estado);
    }

    public List<solicitud> buscarEstadoyPrioridad(Eestado estado, Eprioridad prioridad) {
        return solicitudRepo.findByEstadoAndPrioridad(estado, prioridad);
    }
}
