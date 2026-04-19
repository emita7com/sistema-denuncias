package com.sistemadenuncias.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long>{
    
}
