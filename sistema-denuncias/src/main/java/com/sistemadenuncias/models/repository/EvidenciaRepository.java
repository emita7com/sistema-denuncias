package com.sistemadenuncias.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Evidencia;

@Repository
public interface EvidenciaRepository extends JpaRepository<Evidencia, Long>{
    
}
