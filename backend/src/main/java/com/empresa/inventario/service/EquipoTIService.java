package com.empresa.inventario.service;

import com.empresa.inventario.model.EquipoTI;
import com.empresa.inventario.repository.EquipoTIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Capa de Servicio. Aquí se concentra toda la lógica y reglas de negocio de la app.
@Service
public class EquipoTIService {

    @Autowired
    private EquipoTIRepository repository;

    // Obtener todos los registros
    public List<EquipoTI> listarTodos() {
        return repository.findAll();
    }

    // Buscar equipo por ID
    public Optional<EquipoTI> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    // Guardar nuevo equipo en la BD
    public EquipoTI guardar(EquipoTI equipo) {
        // Validación clave: Si el equipo es nuevo (id null) y el serie ya existe en la
        // BD, lanzamos error.
        if (equipo.getId() == null && repository.existsByNumeroSerie(equipo.getNumeroSerie())) {
            throw new IllegalArgumentException(
                    "Ya existe un equipo con el número de serie: " + equipo.getNumeroSerie());
        }
        return repository.save(equipo);
    }

    // Actualizar un equipo existente
    public EquipoTI actualizar(Long id, EquipoTI equipoActualizado) {
        return repository.findById(id).map(equipo -> {
            equipo.setTipoEquipo(equipoActualizado.getTipoEquipo());
            equipo.setMarca(equipoActualizado.getMarca());

            // Ojo: Si el usuario cambió el número de serie, validamos que el nuevo no
            // exista ya en la BD.
            if (!equipo.getNumeroSerie().equals(equipoActualizado.getNumeroSerie())
                    && repository.existsByNumeroSerie(equipoActualizado.getNumeroSerie())) {
                throw new IllegalArgumentException(
                        "Ya existe otro equipo con el número de serie: " + equipoActualizado.getNumeroSerie());
            }

            equipo.setNumeroSerie(equipoActualizado.getNumeroSerie());
            equipo.setEstado(equipoActualizado.getEstado());
            return repository.save(equipo);
        }).orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado con id: " + id));
    }

    // Eliminar por ID
    public void eliminar(Long id) {
        // Si no existe el ID en la BD, no podemos borrarlo, lanzamos excepción.
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Equipo no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
