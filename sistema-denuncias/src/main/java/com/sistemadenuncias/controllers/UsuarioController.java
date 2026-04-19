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

import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")

public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    //  Listar todos los usuarios
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    //  Buscar usuario por ID
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    //  Registrar nuevo usuario
    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //  Actualizar usuario
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario datos) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setCorreo(datos.getCorreo());
            usuario.setContrasena(datos.getContrasena());
            usuario.setRol(datos.getRol());
            usuario.setEstado(datos.getEstado());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    //  Eliminar usuario
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    //  Buscar por correo (para login)
    @GetMapping("/buscar/{correo}")
    public Usuario buscarPorCorreo(@PathVariable String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    //  Login (autenticación simple)
    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario datos) {
        Usuario usuario = usuarioRepository.findByCorreo(datos.getCorreo());
        if (usuario != null && usuario.getContrasena().equals(datos.getContrasena())) {
            return usuario; // Login correcto
        }
        return null; // Credenciales incorrectas
    }

}
