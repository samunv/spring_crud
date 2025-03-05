package com.example.crudusuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crudusuario.model.Artista;
import com.example.crudusuario.model.Cancion;
import com.example.crudusuario.repository.ArtistaRepository;

@Service
public class ArtistaService {
    private final ArtistaRepository artistaRepository;
    private final CancionService cancionService;

    public ArtistaService(ArtistaRepository artistaRepository, CancionService cancionService) {
        this.artistaRepository = artistaRepository;
        this.cancionService = cancionService;
    }

    public List<Artista> listarArtistas() {
        return artistaRepository.findAll();
    }

    public Artista obtenerArtistaPorId(Long id) {
        return artistaRepository.findById(id).orElseThrow(() -> new RuntimeException("Artista no encontrado"));
    }

    public void actualizarArtista(Long id, Artista artistaActualizado) {
        Artista artista = obtenerArtistaPorId(id);
        artista.setNombre(artistaActualizado.getNombre());
        artistaRepository.save(artista);
    }

    public void eliminarArtista(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));
        // Primero eliminar todas las canciones asociadas a este artista
        for (Cancion cancion : artista.getCanciones()) {
            cancionService.eliminarCancion(cancion.getId());
        }
        artistaRepository.deleteById(id);
    }

    public void agregarArtista(Artista artista) {
        if (artista.getId() == null)
            artistaRepository.save(artista);
    }
}