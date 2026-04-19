package com.sistemadenuncias.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Estudiante;
import com.sistemadenuncias.models.entity.Usuario;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
     // 🔹 Este método busca un estudiante por su usuario asociado
    Estudiante findByUsuario(Usuario usuario);
}
