package com.example.crudusuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crudusuario.model.Lista;
import com.example.crudusuario.repository.ListaRepository;

@Service
public class ListaService {
    private final ListaRepository listaRepository;

    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public List<Lista> listarListas() {
        return listaRepository.findAll();
    }

    public Lista obtenerListaPorId(Long id) {
        return listaRepository.findById(id).orElseThrow(() -> new RuntimeException("Lista no encontrada"));
    }

    public void actualizarLista(Long id, Lista listaActualizada) {
        Lista lista = obtenerListaPorId(id);
        lista.setNombre(listaActualizada.getNombre());
        listaRepository.save(lista);
    }

    public void eliminarLista(Long id) {
        listaRepository.deleteById(id);
    }
    
    public void agregarLista(Lista lista) {
        if (lista.getId() == null)
            listaRepository.save(lista);
    }
}
