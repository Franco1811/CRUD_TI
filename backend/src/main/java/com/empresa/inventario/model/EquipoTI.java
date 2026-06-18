package com.empresa.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "equipos_ti")
public class EquipoTI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de equipo es obligatorio")
    @Column(nullable = false)
    private String tipoEquipo;

    @NotBlank(message = "La marca es obligatoria")
    @Column(nullable = false)
    private String marca;

    @NotBlank(message = "El número de serie es obligatorio")
    @Column(nullable = false, unique = true)
    private String numeroSerie;

    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false)
    private String estado;

    // Constructores
    public EquipoTI() {}

    public EquipoTI(String tipoEquipo, String marca, String numeroSerie, String estado) {
        this.tipoEquipo = tipoEquipo;
        this.marca = marca;
        this.numeroSerie = numeroSerie;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
