package com.lab.SoporteApp.model;

import java.util.List;

import com.lab.SoporteApp.model.enums.Erol;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "usuarios")
public class users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false , length = 100 , unique = false)
    @NotBlank(message = "El nombre es OBLIGATORIO")
    @Size(min = 3 , max = 100 , message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @Column(nullable = false , length = 100 , unique = true)
    @NotBlank(message = "El email es OBLIGATORIO")
    @Size(max = 150)
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña es OBLIGATORIA")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "El rol es obligatorio")
    @Column(nullable = false)
     private Erol roluser;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<solicitud> solicitudes;

    
}
