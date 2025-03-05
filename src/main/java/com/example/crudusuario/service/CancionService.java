package com.example.crudusuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.crudusuario.model.Artista;
import com.example.crudusuario.model.Cancion;
import com.example.crudusuario.repository.ArtistaRepository;
import com.example.crudusuario.repository.CancionRepository;

@Service
public class CancionService {
    private final CancionRepository cancionRepository;
    private final ArtistaRepository artistaRepository;

    public CancionService(CancionRepository cancionRepository, ArtistaRepository artistaRepository) {
        this.cancionRepository = cancionRepository;
        this.artistaRepository = artistaRepository;
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
        cancion.setArtista(cancionActualizada.getArtista());
        cancionRepository.save(cancion);
    }

    public void eliminarCancion(Long id) {
        cancionRepository.deleteById(id);
    }

    public void agregarCancion(Cancion cancion) {
        if (cancion.getArtista() == null || cancion.getArtista().getId() == null) {
            throw new RuntimeException("El artista asociado no es válido o no existe");
        }

        // Verificar que el artista realmente existe en la base de datos
        Optional<Artista> artistaOpt = artistaRepository.findById(cancion.getArtista().getId());
        if (artistaOpt.isEmpty()) {
            throw new RuntimeException("No se puede agregar la canción: el artista no existe en la base de datos.");
        }

        cancion.setArtista(artistaOpt.get()); // Asegurar que la relación es válida
        cancionRepository.save(cancion);
    }

}
