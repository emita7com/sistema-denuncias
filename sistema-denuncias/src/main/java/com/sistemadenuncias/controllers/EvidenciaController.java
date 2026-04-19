package com.sistemadenuncias.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemadenuncias.models.entity.Evidencia;
import com.sistemadenuncias.models.repository.EvidenciaRepository;

@Controller
@RequestMapping("/evidencias")
@CrossOrigin(origins = "*")

public class EvidenciaController {
    
    @Autowired
    private EvidenciaRepository evidenciaRepository;

    //  Listar todas las evidencias
    @GetMapping
    public List<Evidencia> listar() {
        return evidenciaRepository.findAll();
    }

    //  Buscar evidencia por ID
    @GetMapping("/{id}")
    public Evidencia obtenerPorId(@PathVariable Long id) {
        return evidenciaRepository.findById(id).orElse(null);
    }

    //  Guardar nueva evidencia
    @PostMapping
    public Evidencia guardar(@RequestBody Evidencia evidencia) {
        return evidenciaRepository.save(evidencia);
    }

    //  Actualizar evidencia
    @PutMapping("/{id}")
    public Evidencia actualizar(@PathVariable Long id, @RequestBody Evidencia datos) {
        Evidencia evidencia = evidenciaRepository.findById(id).orElse(null);
        if (evidencia != null) {
            evidencia.setNombre_archivo(datos.getNombre_archivo());
            evidencia.setRuta_archivo(datos.getRuta_archivo());
            evidencia.setTipo_archivo(datos.getTipo_archivo());
            evidencia.setDenuncia(datos.getDenuncia());
            return evidenciaRepository.save(evidencia);
        }
        return null;
    }

    // ✅ Eliminar evidencia
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        evidenciaRepository.deleteById(id);
    }

}
