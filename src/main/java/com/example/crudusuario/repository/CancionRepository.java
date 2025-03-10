package com.example.crudusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crudusuario.model.Cancion;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {}
