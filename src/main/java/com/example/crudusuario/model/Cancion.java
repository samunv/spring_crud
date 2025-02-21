package com.example.crudusuario.model;

import jakarta.persistence.*;

@Entity
@Table(name = "canciones")
public class Cancion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String genero;

	@ManyToOne
	@JoinColumn(name = "lista_id") // La columna que contiene la relación con la lista
	private Lista lista;

	public void setId(Long id) {
		this.id = id;
	}

	public Cancion() {
	}

	public Cancion(String titulo, String genero, Lista lista) {
		this.titulo = titulo;
		this.genero = genero;
		this.lista = lista;
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

	public Lista getLista() {
		return this.lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

}
