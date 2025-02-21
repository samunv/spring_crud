package com.example.crudusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crudusuario.model.Cancion;

public interface CancionRepository extends JpaRepository<Cancion, Long> {}
