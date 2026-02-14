package com.lab.SoporteApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lab.SoporteApp.model.users;
import com.lab.SoporteApp.model.enums.Erol;
import com.lab.SoporteApp.service.usersService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*") 
public class usersController {

    @Autowired
    private usersService usersService;

    
    @PostMapping
    public users crear(@RequestBody users user) {
        return usersService.crear(user);
    }

    @GetMapping
    public List<users> listar() {
        return usersService.listar();
    }

    @GetMapping("/{id}")
    public users obtener(@PathVariable Long id) {
        return usersService.buscarPorId(id);
    }

    @GetMapping("/email/{email}")
    public users buscarPorEmail(@PathVariable String email) {
        return usersService.buscarPorEmail(email);
    }

    @GetMapping("/rol/{rol}")
    public List<users> buscarPorRol(@PathVariable Erol roluser) {
        return usersService.buscarPorRol(roluser);
    }

    @PutMapping("/{id}")
    public users actualizar(@PathVariable Long id, @RequestBody users user) {
        return usersService.actualizar(id, user);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usersService.eliminar(id);
    }
}
