package com.sistemadenuncias.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemadenuncias.models.entity.Director;
import com.sistemadenuncias.models.entity.Usuario;

public interface DirectorRepository extends JpaRepository<Director, Long>{
    
     // Puedes agregar consultas personalizadas 
    Director findByUsuario(Usuario usuario);
}
