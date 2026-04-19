package com.sistemadenuncias.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.UsuarioRepository;


@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario u) {
        return usuarioRepository.save(u);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    // login simple (plaintext por ahora)
    public boolean validarCredenciales(String correo, String contrasena) {
        Usuario u = usuarioRepository.findByCorreo(correo);
        if (u == null) return false;
        return u.getContrasena().equals(contrasena);
    }
}

