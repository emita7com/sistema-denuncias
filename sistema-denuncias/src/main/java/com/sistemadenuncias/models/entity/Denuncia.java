package com.sistemadenuncias.models.entity;

import java.time.LocalDateTime;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "denuncia")

public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDenuncia;

    @Column(nullable = false, length = 100)
    private String tipo_acoso;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 255)
    private String evidencia;

    @Column(nullable = false)
    private boolean confidencial = false;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(length = 30)
    private String estado = "Pendiente";

    @Column(name = "derivado_a", length = 50)
private String derivadoA;

@Column(length = 500)
private String mensajeDerivacion;

    //  Relaciones con otras entidades
    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "id_psicologo")
    private Psicologo psicologo;

    @ManyToOne
    @JoinColumn(name = "id_defensoria")
    private Defensoria defensoria;

    @ManyToOne
    @JoinColumn(name = "id_colegio")
    private Colegio colegio;

    @OneToMany(mappedBy = "denuncia", cascade = CascadeType.ALL)
    private List<Seguimiento> seguimientos;

    @OneToMany(mappedBy = "denuncia", cascade = CascadeType.ALL)
    private List<Evidencia> evidencias;
    

    //  Constructores
    public Denuncia() {}

    public Denuncia(String tipo_acoso, String descripcion, boolean confidencial) {
        this.tipo_acoso = tipo_acoso;
        this.descripcion = descripcion;
        this.confidencial = confidencial;
        this.fecha = LocalDateTime.now();
    }

    //  Getters y Setters
    public Long getId_denuncia() {
        return idDenuncia;
    }

    public void setId_denuncia(Long idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public String getTipo_acoso() {
        return tipo_acoso;
    }

    public void setTipo_acoso(String tipo_acoso) {
        this.tipo_acoso = tipo_acoso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public boolean isConfidencial() {
        return confidencial;
    }

    public void setConfidencial(boolean confidencial) {
        this.confidencial = confidencial;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Psicologo getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(Psicologo psicologo) {
        this.psicologo = psicologo;
    }

    public Defensoria getDefensoria() {
        return defensoria;
    }

    public void setDefensoria(Defensoria defensoria) {
        this.defensoria = defensoria;
    }

    public Colegio getColegio() {
        return colegio;
    }

    public void setColegio(Colegio colegio) {
        this.colegio = colegio;
    }

    public List<Seguimiento> getSeguimientos() {
        return seguimientos;
    }

    public void setSeguimientos(List<Seguimiento> seguimientos) {
        this.seguimientos = seguimientos;
    }

    public List<Evidencia> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(List<Evidencia> evidencias) {
        this.evidencias = evidencias;
    }

    public String getDerivadoA() {
    return derivadoA;
}

public void setDerivadoA(String derivadoA) {
    this.derivadoA = derivadoA;
}
    
}

