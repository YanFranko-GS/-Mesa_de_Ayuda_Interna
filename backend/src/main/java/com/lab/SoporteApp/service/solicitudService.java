package com.lab.SoporteApp.service;

import java.time.LocalDate;  // CAMBIADO de LocalDateTime
// import java.time.LocalDateTime;  // ELIMINADO
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab.SoporteApp.DTO.SolicitudDTO;
import com.lab.SoporteApp.model.solicitud;
import com.lab.SoporteApp.model.users;
import com.lab.SoporteApp.model.enums.Eestado;
import com.lab.SoporteApp.model.enums.Eprioridad;
import com.lab.SoporteApp.repository.solicitudRepository;
import com.lab.SoporteApp.repository.usersRepository;

@Service
public class solicitudService {

    @Autowired
    private usersRepository usersRepo;

    @Autowired
    private solicitudRepository solicitudRepo;

    private SolicitudDTO convertToDTO(solicitud solicitud) {
        SolicitudDTO dto = new SolicitudDTO();
        dto.setId(solicitud.getId());
        dto.setTitulo(solicitud.getTitulo());
        dto.setDescripcion(solicitud.getDescripcion());
        dto.setPrioridad(solicitud.getPrioridad());
        dto.setEstado(solicitud.getEstado());
        dto.setUsuarioId(solicitud.getUsuario() != null ? solicitud.getUsuario().getId() : null);
        dto.setFechaCreacion(solicitud.getFechaCreacion());
        dto.setFechaActualizacion(solicitud.getFechaActualizacion());
        return dto;
    }

    public solicitud crear(solicitud sol, String email) {

        users usuario = usersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        sol.setUsuario(usuario);

        sol.setFechaCreacion(LocalDate.now());
        sol.setFechaActualizacion(LocalDate.now());
        sol.setEstado(Eestado.NUEVO);

        return solicitudRepo.save(sol);
    }

    public List<SolicitudDTO> listar() {
        return solicitudRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SolicitudDTO obtenerPorId(Long id) {
        solicitud sol = solicitudRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        return convertToDTO(sol);
    }

    public solicitud actualizar(Long id, solicitud nueva) {
        solicitud actual = solicitudRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

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

    // CORREGIDO: Cambiado de LocalDateTime a LocalDate
    public List<solicitud> buscarFechaActualizacion(LocalDate fechaActualizacion) {
        return solicitudRepo.findByFechaActualizacion(fechaActualizacion);
    }

    public List<solicitud> buscarEstado(Eestado estado) {
        return solicitudRepo.findByEstado(estado);
    }

    public List<solicitud> buscarEstadoyPrioridad(Eestado estado, Eprioridad prioridad) {
        return solicitudRepo.findByEstadoAndPrioridad(estado, prioridad);
    }

    public List<SolicitudDTO> listarSolicitudesPorUsuario(String email) {
        return solicitudRepo.findByUsuarioEmailIgnoreCase(email).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}