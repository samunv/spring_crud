package com.example.crudusuario.model;

import jakarta.persistence.*;


import java.util.List;

@Entity
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "lista")  // Relación de uno a muchos con Cancion
    private List<Cancion> canciones;  // Lista de canciones que pertenecen a esta lista

    // Constructor sin parámetros
    public Lista() {}

    // Constructor con parámetros
    public Lista(String nombre) {
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

    // Método para agregar una canción a la lista
    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
        cancion.setLista(this);  // Establece la relación con esta lista
    }

    // Método para eliminar una canción de la lista
    public void eliminarCancion(Cancion cancion) {
        canciones.remove(cancion);
        cancion.setLista(null);  // Elimina la relación con esta lista
    }
}
