package com.empresa.inventario.repository;

import com.empresa.inventario.model.EquipoTI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipoTIRepository extends JpaRepository<EquipoTI, Long> {
    Optional<EquipoTI> findByNumeroSerie(String numeroSerie);
    boolean existsByNumeroSerie(String numeroSerie);
}
