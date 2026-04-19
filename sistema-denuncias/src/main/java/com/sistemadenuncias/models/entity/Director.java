package com.sistemadenuncias.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "director")

public class Director {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDirector;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(name = "foto_perfil")
private String foto_perfil;


    @Column(length = 20)
    private String estado;

    // Relación con colegio (un director pertenece a un colegio)
    @OneToOne
    @JoinColumn(name = "id_colegio")
    private Colegio colegio;

    // Relación con usuario (para login y roles)
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}

