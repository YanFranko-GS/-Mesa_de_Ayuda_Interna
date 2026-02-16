package com.lab.SoporteApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.lab.SoporteApp.DTO.SolicitudDTO;
import com.lab.SoporteApp.model.solicitud;
import com.lab.SoporteApp.model.users;
import com.lab.SoporteApp.model.enums.Eestado;
import com.lab.SoporteApp.model.enums.Eprioridad;
import com.lab.SoporteApp.repository.usersRepository;
import com.lab.SoporteApp.service.solicitudService;

import jakarta.validation.Valid;  // AÑADIDO

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin("*")
public class solicitudController {

    @Autowired
    private solicitudService solicitudService;

    @Autowired
    private usersRepository usersRepository;

    @PreAuthorize("hasAnyRole(\'OPERADOR\', \'USUARIO\')")
    @GetMapping
    public ResponseEntity<List<SolicitudDTO>> listarSolicitudes(Authentication auth) {
        String emailUsuario = auth.getName();
        boolean esOperador = auth.getAuthorities().stream()
                .anyMatch(rol -> rol.getAuthority().equals("OPERADOR") || 
                                 rol.getAuthority().equals("ROLE_OPERADOR"));

        if (esOperador) {
            System.out.println("Solicitud de OPERADOR: Devolviendo todas las solicitudes.");
            return ResponseEntity.ok(solicitudService.listar()); 
        } else {
            System.out.println("Solicitud de USUARIO (" + emailUsuario + "): Devolviendo solo las suyas.");
            return ResponseEntity.ok(solicitudService.listarSolicitudesPorUsuario(emailUsuario));
        }
    }

    @PreAuthorize("hasRole(\'USUARIO\')")
    @PostMapping
    public ResponseEntity<solicitud> crear(@Valid @RequestBody solicitud sol, Authentication auth) {  // AÑADIDO @Valid
        String email = auth.getName();
        return ResponseEntity.ok(solicitudService.crear(sol, email));
    }

    @PreAuthorize("hasAnyRole(\'OPERADOR\', \'USUARIO\')")
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDTO> obtener(@PathVariable Long id, Authentication auth) {
        String emailUsuario = auth.getName();
        boolean esOperador = auth.getAuthorities().stream()
                .anyMatch(rol -> rol.getAuthority().equals("OPERADOR") || 
                                 rol.getAuthority().equals("ROLE_OPERADOR"));

        SolicitudDTO solicitud = solicitudService.obtenerPorId(id);

        if (solicitud == null) {
            return ResponseEntity.notFound().build();
        }

        if (esOperador) {
            return ResponseEntity.ok(solicitud);
        } else {
            
            users user = usersRepository.findByEmail(emailUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            if (solicitud.getUsuarioId().equals(user.getId())) {
                return ResponseEntity.ok(solicitud);
            } else {
                return ResponseEntity.status(403).build(); 
            }
        }
    }

    @PreAuthorize("hasRole(\'USUARIO\')")
    @PutMapping("/{id}")
    public ResponseEntity<solicitud> actualizar(@PathVariable Long id, @Valid @RequestBody solicitud sol, Authentication auth) {  // AÑADIDO @Valid
        String emailUsuario = auth.getName();
        SolicitudDTO existingSolicitud = solicitudService.obtenerPorId(id);
        users user = usersRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!existingSolicitud.getUsuarioId().equals(user.getId())) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        return ResponseEntity.ok(solicitudService.actualizar(id, sol));
    }

    @PreAuthorize("hasRole(\'OPERADOR\')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole(\'OPERADOR\')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<solicitud>> buscarEstado(@PathVariable Eestado estado) {
        return ResponseEntity.ok(solicitudService.buscarEstado(estado));
    }

    @PreAuthorize("hasRole(\'OPERADOR\')")
    @GetMapping("/filtro")
    public ResponseEntity<List<solicitud>> buscarEstadoPrioridad(
            @RequestParam Eestado estado,
            @RequestParam Eprioridad prioridad) {

        return ResponseEntity.ok(solicitudService.buscarEstadoyPrioridad(estado, prioridad));
    }
}