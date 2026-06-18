package com.empresa.inventario.controller;

import com.empresa.inventario.model.EquipoTI;
import com.empresa.inventario.service.EquipoTIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "http://localhost:5173") // Permitir peticiones desde React Vite
public class EquipoTIController {

    @Autowired
    private EquipoTIService service;

    @GetMapping
    public List<EquipoTI> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoTI> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EquipoTI> crear(@Valid @RequestBody EquipoTI equipo) {
        EquipoTI nuevoEquipo = service.guardar(equipo);
        return new ResponseEntity<>(nuevoEquipo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoTI> actualizar(@PathVariable Long id, @Valid @RequestBody EquipoTI equipo) {
        EquipoTI equipoActualizado = service.actualizar(id, equipo);
        return ResponseEntity.ok(equipoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
