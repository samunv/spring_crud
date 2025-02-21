package com.example.crudusuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crudusuario.model.Cancion;
import com.example.crudusuario.repository.CancionRepository;

@Service
public class CancionService {
    private final CancionRepository cancionRepository;

    public CancionService(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    public List<Cancion> listarCanciones() {
        return cancionRepository.findAll();
    }

    public Cancion obtenerCancionPorId(Long id) {
        return cancionRepository.findById(id).orElseThrow(() -> new RuntimeException("Canción no encontrada"));
    }

    public void actualizarCancion(Long id, Cancion cancionActualizada) {
        Cancion cancion = obtenerCancionPorId(id);
        cancion.setTitulo(cancionActualizada.getTitulo());
        cancion.setGenero(cancionActualizada.getGenero());
        cancionRepository.save(cancion);
    }

    public void eliminarCancion(Long id) {
        cancionRepository.deleteById(id);
    }
    
    public void agregarCancion(Cancion cancion) {
        if (cancion.getId() == null)
            cancionRepository.save(cancion);
    }
}
