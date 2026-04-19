package com.sistemadenuncias.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Colegio;

@Repository

public interface ColegioRepository extends JpaRepository<Colegio, Long>{
    
}
