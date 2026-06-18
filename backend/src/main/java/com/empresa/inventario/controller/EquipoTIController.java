package com.empresa.inventario.controller;

import com.empresa.inventario.model.EquipoTI;
import com.empresa.inventario.service.EquipoTIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Esta clase es la puerta de entrada (API). Mapea los requests HTTP a los métodos de Java.
@RestController
@RequestMapping("/api/equipos")
// Habilita que el puerto de React (5173) pueda consumir esta API sin bloqueo de
// CORS.
@CrossOrigin(origins = "http://localhost:5173")
public class EquipoTIController {

    @Autowired
    private EquipoTIService service;

    // Obtener todos los equipos de la BD
    @GetMapping
    public List<EquipoTI> listar() {
        return service.listarTodos();
    }

    // Buscar uno específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<EquipoTI> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Registrar un nuevo equipo
    // @Valid activa las anotaciones de validación (como @NotBlank) definidas en el
    // modelo EquipoTI
    @PostMapping
    public ResponseEntity<EquipoTI> crear(@Valid @RequestBody EquipoTI equipo) {
        EquipoTI nuevoEquipo = service.guardar(equipo);
        return new ResponseEntity<>(nuevoEquipo, HttpStatus.CREATED);
    }

    // Actualizar datos de un equipo existente
    @PutMapping("/{id}")
    public ResponseEntity<EquipoTI> actualizar(@PathVariable Long id, @Valid @RequestBody EquipoTI equipo) {
        EquipoTI equipoActualizado = service.actualizar(id, equipo);
        return ResponseEntity.ok(equipoActualizado);
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
