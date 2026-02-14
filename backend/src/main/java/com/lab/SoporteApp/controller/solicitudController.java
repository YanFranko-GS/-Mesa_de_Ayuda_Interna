package com.lab.SoporteApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lab.SoporteApp.model.solicitud;
import com.lab.SoporteApp.model.enums.Eestado;
import com.lab.SoporteApp.model.enums.Eprioridad;
import com.lab.SoporteApp.service.solicitudService;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin("*")
public class solicitudController {

    @Autowired
    private solicitudService solicitudService;

    @PostMapping
    public solicitud crear(@RequestBody solicitud sol) {
        return solicitudService.crear(sol);
    }

    @GetMapping
    public List<solicitud> listar() {
        return solicitudService.listar();
    }

    
    @GetMapping("/{id}")
    public solicitud obtener(@PathVariable Long id) {
        return solicitudService.obtenerPorId(id);
    }

    
    @PutMapping("/{id}")
    public solicitud actualizar(@PathVariable Long id, @RequestBody solicitud sol) {
        return solicitudService.actualizar(id, sol);
    }

    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
    }

    @GetMapping("/estado/{estado}")
    public List<solicitud> buscarEstado(@PathVariable Eestado estado) {
        return solicitudService.buscarEstado(estado);
    }

    @GetMapping("/filtro")
    public List<solicitud> buscarEstadoPrioridad(
            @RequestParam Eestado estado,
            @RequestParam Eprioridad prioridad) {

        return solicitudService.buscarEstadoyPrioridad(estado, prioridad);
    }
}
