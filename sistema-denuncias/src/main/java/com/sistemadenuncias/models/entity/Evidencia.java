package com.sistemadenuncias.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "evidencia")

public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evidencia;

    @Column(nullable = false, length = 255)
    private String nombre_archivo;

    @Column(length = 255)
    private String ruta_archivo;

    @Column(length = 100)
    private String tipo_archivo;

    @ManyToOne
    @JoinColumn(name = "id_denuncia")
    private Denuncia denuncia;

    //  Constructores
    public Evidencia() {}

    public Evidencia(String nombre_archivo, String ruta_archivo, String tipo_archivo) {
        this.nombre_archivo = nombre_archivo;
        this.ruta_archivo = ruta_archivo;
        this.tipo_archivo = tipo_archivo;
    }

    //  Getters y Setters
    public Long getId_evidencia() {
        return id_evidencia;
    }

    public void setId_evidencia(Long id_evidencia) {
        this.id_evidencia = id_evidencia;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getRuta_archivo() {
        return ruta_archivo;
    }

    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    public String getTipo_archivo() {
        return tipo_archivo;
    }

    public void setTipo_archivo(String tipo_archivo) {
        this.tipo_archivo = tipo_archivo;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }
}
