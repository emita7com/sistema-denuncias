package com.sistemadenuncias.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistemadenuncias.models.entity.Colegio;
import com.sistemadenuncias.models.entity.Denuncia;


@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long>{
    
    List<Denuncia> findByColegio(Colegio colegio);
    List<Denuncia> findByColegioAndDerivadoA(Colegio colegio, String derivadoA);
    List<Denuncia> findByDerivadoA(String derivadoA);

    @Query("SELECT COUNT(d) FROM Denuncia d WHERE d.tipo_acoso = :tipo")
long contarPorTipo(@Param("tipo") String tipo);

}
