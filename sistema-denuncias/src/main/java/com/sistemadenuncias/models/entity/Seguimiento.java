package com.sistemadenuncias.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento")
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(length = 30, nullable = false)
    private String autorRol; // PSICOLOGO, DEFENSORIA, DIRECTOR, etc.

    @ManyToOne
    @JoinColumn(name = "id_denuncia", nullable = false)
    private Denuncia denuncia;

    public Seguimiento() {}

    public Seguimiento(String comentario, String autorRol, Denuncia denuncia) {
        this.comentario = comentario;
        this.autorRol = autorRol;
        this.denuncia = denuncia;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getAutorRol() { return autorRol; }
    public void setAutorRol(String autorRol) { this.autorRol = autorRol; }

    public Denuncia getDenuncia() { return denuncia; }
    public void setDenuncia(Denuncia denuncia) { this.denuncia = denuncia; }
}
