package com.sistemadenuncias.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemadenuncias.models.entity.Colegio;
import com.sistemadenuncias.models.repository.ColegioRepository;

@Controller
@RequestMapping("/colegios")
@CrossOrigin(origins = "*")

public class ColegioController {
    
    @Autowired
    private ColegioRepository colegioRepository;

    // Obtener todos los colegios
    @GetMapping
    public List<Colegio> listar() {
        return colegioRepository.findAll();
    }

    // Crear un nuevo colegio
    @PostMapping
    public Colegio guardar(@RequestBody Colegio colegio) {
        return colegioRepository.save(colegio);
    }

    // Buscar colegio por ID
    @GetMapping("/{id}")
    public Colegio obtenerPorId(@PathVariable Long id) {
        return colegioRepository.findById(id).orElse(null);
    }

    // Eliminar colegio por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        colegioRepository.deleteById(id);
    }
}

