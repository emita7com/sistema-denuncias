package com.sistemadenuncias.models.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "colegio")
@Getter
@Setter
public class Colegio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idColegio;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 150)
    private String direccion;

    @Column(length = 100)
    private String distrito;

    @Column(length = 30)
    private String telefono;

    @Column(length = 100)
    private String correo;

    @Column(length = 20)
    private String estado = "Activo";

    // Constructores, getters y setters
    public Colegio() {}

    public Colegio(String nombre, String direccion, String distrito, String telefono, String correo, String director) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.distrito = distrito;
        this.telefono = telefono;
        this.correo = correo;
    
    }

    public Long getIdColegio() {
        return idColegio;
    }

    public void setIdColegio(Long idColegio) {
        this.idColegio = idColegio;
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

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    @OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL)
private List<Estudiante> estudiantes;

@OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL)
private List<Docente> docentes;

@OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL)
private List<Psicologo> psicologos;

@OneToOne(mappedBy = "colegio", cascade = CascadeType.ALL)
private Director director;

}
