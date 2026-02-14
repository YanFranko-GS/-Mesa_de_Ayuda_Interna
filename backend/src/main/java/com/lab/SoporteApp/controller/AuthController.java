package com.lab.SoporteApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.SoporteApp.DTO.AuthRequest;
import com.lab.SoporteApp.config.JwtService;
import com.lab.SoporteApp.model.users;
import com.lab.SoporteApp.model.enums.Erol;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.lab.SoporteApp.repository.usersRepository;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private usersRepository userRepo;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );
        
        return jwtService.generateToken(userRepo.findByEmail(request.getEmail()).get());
    }

    @PostMapping("/register")
    public users register(@RequestBody users user) {
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        if (user.getRoluser() == null) {
            user.setRoluser(Erol.USUARIO);
        }
        return userRepo.save(user);
    }
    
    
}
