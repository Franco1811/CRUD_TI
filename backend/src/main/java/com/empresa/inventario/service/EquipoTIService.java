package com.empresa.inventario.service;

import com.empresa.inventario.model.EquipoTI;
import com.empresa.inventario.repository.EquipoTIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoTIService {

    @Autowired
    private EquipoTIRepository repository;

    public List<EquipoTI> listarTodos() {
        return repository.findAll();
    }

    public Optional<EquipoTI> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public EquipoTI guardar(EquipoTI equipo) {
        if (equipo.getId() == null && repository.existsByNumeroSerie(equipo.getNumeroSerie())) {
            throw new IllegalArgumentException("Ya existe un equipo con el número de serie: " + equipo.getNumeroSerie());
        }
        return repository.save(equipo);
    }

    public EquipoTI actualizar(Long id, EquipoTI equipoActualizado) {
        return repository.findById(id).map(equipo -> {
            equipo.setTipoEquipo(equipoActualizado.getTipoEquipo());
            equipo.setMarca(equipoActualizado.getMarca());
            
            // Validar si cambia el número de serie y si ya existe
            if (!equipo.getNumeroSerie().equals(equipoActualizado.getNumeroSerie()) 
                && repository.existsByNumeroSerie(equipoActualizado.getNumeroSerie())) {
                throw new IllegalArgumentException("Ya existe otro equipo con el número de serie: " + equipoActualizado.getNumeroSerie());
            }
            
            equipo.setNumeroSerie(equipoActualizado.getNumeroSerie());
            equipo.setEstado(equipoActualizado.getEstado());
            return repository.save(equipo);
        }).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Equipo no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
