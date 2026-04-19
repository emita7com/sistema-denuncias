package com.sistemadenuncias.models.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "defensoria")
@Getter
@Setter
public class Defensoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_defensoria;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 150)
    private String direccion;

    @Column(length = 30)
    private String telefono;

    @Column(length = 100)
    private String responsable;

    @Column(length = 20)
    private String estado = "Activo";

    @OneToMany(mappedBy = "defensoria", cascade = CascadeType.ALL)
    private List<Denuncia> denuncias;

@OneToOne
@JoinColumn(name = "id_usuario")
private Usuario usuario;

@ManyToOne
@JoinColumn(name = "id_colegio")
private Colegio colegio;


    // 🔹 Constructores
    public Defensoria() {}

    public Defensoria(String nombre, String direccion, String telefono, String responsable) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.responsable = responsable;
    }

    // 🔹 Getters y Setters
    public Long getId_defensoria() {
        return id_defensoria;
    }

    public void setId_defensoria(Long id_defensoria) {
        this.id_defensoria = id_defensoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Denuncia> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
    }
}

