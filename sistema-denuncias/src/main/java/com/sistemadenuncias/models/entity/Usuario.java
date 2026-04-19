package com.sistemadenuncias.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false, length = 50)
    private String rol; // ADMIN, ESTUDIANTE, DOCENTE, PSICOLOGO, DIRECTOR

    @Column(length = 20)
    private String estado; // Activo, Inactivo

    @ManyToOne
    @JoinColumn(name = "id_colegio")
    private Colegio colegio;
}
