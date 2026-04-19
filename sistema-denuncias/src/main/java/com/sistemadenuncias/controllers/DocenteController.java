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

import com.sistemadenuncias.models.entity.Docente;
import com.sistemadenuncias.models.repository.DocenteRepository;

@Controller
@RequestMapping("/docentes")
@CrossOrigin(origins = "*")

public class DocenteController {
    
    @Autowired
    private DocenteRepository docenteRepository;

    // ✅ Listar todos los docentes
    @GetMapping
    public List<Docente> listar() {
        return docenteRepository.findAll();
    }

    // ✅ Buscar docente por ID
    @GetMapping("/{id}")
    public Docente obtenerPorId(@PathVariable Long id) {
        return docenteRepository.findById(id).orElse(null);
    }

    // ✅ Crear nuevo docente
    @PostMapping
    public Docente guardar(@RequestBody Docente docente) {
        return docenteRepository.save(docente);
    }

    // ✅ Actualizar un docente
    @PutMapping("/{id}")
    public Docente actualizar(@PathVariable Long id, @RequestBody Docente datos) {
        Docente docente = docenteRepository.findById(id).orElse(null);
        if (docente != null) {
            docente.setNombre(datos.getNombre());
            docente.setApellido(datos.getApellido());
            docente.setCorreo(datos.getCorreo());
            docente.setContrasena(datos.getContrasena());
            docente.setEstado(datos.getEstado());
            docente.setColegio(datos.getColegio());
            return docenteRepository.save(docente);
        }
        return null;
    }

    // ✅ Eliminar docente
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        docenteRepository.deleteById(id);
    }

}
