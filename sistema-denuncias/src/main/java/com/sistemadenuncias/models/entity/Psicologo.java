package com.sistemadenuncias.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "psicologo")

public class Psicologo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_psicologo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 50)
    private String telefono;

    @Column(length = 255)
    private String foto_perfil;

    @Column(length = 20)
    private String estado = "Activo";


    //  Constructores
    public Psicologo() {}

    public Psicologo(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;

    }

    //  Getters y Setters
    public Long getId_psicologo() {
        return id_psicologo;
    }

    public void setId_psicologo(Long id_psicologo) {
        this.id_psicologo = id_psicologo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Colegio getColegio() {
        return colegio;
    }

    public void setColegio(Colegio colegio) {
        this.colegio = colegio;
    }

    public Usuario getUsuario() {
    return usuario;
}

public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
}

        @ManyToOne
    @JoinColumn(name = "id_colegio")
    private Colegio colegio;

    @OneToOne
@JoinColumn(name = "id_usuario")
private Usuario usuario;
}

