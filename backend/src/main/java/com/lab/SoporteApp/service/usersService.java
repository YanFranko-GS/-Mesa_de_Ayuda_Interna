package com.lab.SoporteApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lab.SoporteApp.model.users;
import com.lab.SoporteApp.model.enums.Erol;
import com.lab.SoporteApp.repository.usersRepository;

@Service
public class usersService {

    @Autowired
    private usersRepository usersRepo;

    @Autowired
    private PasswordEncoder encoder;

    public users crear(users user) {

        if (user.getRoluser() == null) {
            user.setRoluser(Erol.USUARIO);
        }

        if (usersRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        return usersRepo.save(user);
    }

    public List<users> listar() {
        return usersRepo.findAll();
    }

    public users buscarPorId(Long id) {
        return usersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public users buscarPorEmail(String email) {
        return usersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Correo no encontrado"));
    }

    public List<users> buscarPorRol(Erol roluser) {
        return usersRepo.findByRoluser(roluser);
    }

    public users actualizar(Long id, users nuevo) {

        users actual = buscarPorId(id);

        actual.setNombre(nuevo.getNombre());
        actual.setEmail(nuevo.getEmail());
        actual.setRoluser(nuevo.getRoluser());
        actual.setPassword(encoder.encode(nuevo.getPassword()));

        return usersRepo.save(actual);
    }

    public void eliminar(Long id) {

        if (!usersRepo.existsById(id)) {
            throw new RuntimeException("Usuario no existe");
        }

        usersRepo.deleteById(id);
    }

    public users crearUsuario(users u) {
        u.setPassword(encoder.encode(u.getPassword()));
        return usersRepo.save(u);
    }

}
