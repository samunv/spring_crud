package com.example.crudusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crudusuario.model.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {}
