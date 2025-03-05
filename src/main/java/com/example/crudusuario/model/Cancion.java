package com.example.crudusuario.model;

import jakarta.persistence.*;

@Entity
@Table(name = "canciones")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String genero;

    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false) // La columna que contiene la relación con el artista
    private Artista artista;

    public void setId(Long id) {
        this.id = id;
    }

    public Cancion() {
    }

    public Cancion(String titulo, String genero, Artista artista) {
        this.titulo = titulo;
        this.genero = genero;
        this.artista = artista;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Artista getArtista() {
        return this.artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
}
