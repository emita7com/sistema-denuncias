package com.sistemadenuncias.models.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Defensoria;
import com.sistemadenuncias.models.entity.Usuario;

@Repository
public interface DefensoriaRepository extends JpaRepository<Defensoria, Long>{

    Defensoria findByUsuario(Usuario usuario);
    

}
