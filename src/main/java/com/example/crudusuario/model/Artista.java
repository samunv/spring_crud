package com.example.crudusuario.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "artista")  // Relación de uno a muchos con Cancion mediante el atributo "artista" en Cancion.java
    private List<Cancion> canciones;  // Lista de canciones que pertenecen a este artista

    // Constructor sin parámetros
    public Artista() {}

    // Constructor con parámetros
    public Artista(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    // Método para agregar una canción al artista
    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
        cancion.setArtista(this);  // Establece la relación con este artista
    }

    // Método para eliminar una canción del artista
    public void eliminarCancion(Cancion cancion) {
        canciones.remove(cancion);
        cancion.setArtista(null);  // Elimina la relación con este artista
    }
}
