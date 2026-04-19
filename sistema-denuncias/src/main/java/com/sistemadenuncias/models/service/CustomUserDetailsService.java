package com.sistemadenuncias.models.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{ 
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + correo);
        }

        return new User(
                usuario.getCorreo(),
                usuario.getContrasena(),
                Collections.singleton(() -> "ROLE_" + usuario.getRol())
        );
    }

}
