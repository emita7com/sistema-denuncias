package com.sistemadenuncias.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Seguimiento;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long>{

   // List<Seguimiento> findByDenunciaId(Long idDenuncia);
    List<Seguimiento> findByDenunciaIdDenuncia(Long idDenuncia);
    
}
