package com.sistemadenuncias.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Psicologo;
import com.sistemadenuncias.models.entity.Usuario;

@Repository

public interface PsicologoRepository extends JpaRepository<Psicologo, Long> {
    
    Psicologo findByUsuario(Usuario usuario);
}
