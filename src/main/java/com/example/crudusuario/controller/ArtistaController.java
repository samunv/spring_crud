package com.example.crudusuario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.crudusuario.model.Artista;
import com.example.crudusuario.service.ArtistaService;
import com.example.crudusuario.service.CancionService;

@Controller
@RequestMapping("/api/artistas")
public class ArtistaController {
    private final ArtistaService artistaService;
    private final CancionService cancionService;

    public ArtistaController(ArtistaService artistaService, CancionService cancionService) {
        this.artistaService = artistaService;
        this.cancionService = cancionService;
    }

    @GetMapping("/crear")
    public String crearForm(Model model) {
        model.addAttribute("artista", new Artista());
        model.addAttribute("canciones", cancionService.listarCanciones());
        return "artista/crear";
    }

    @PostMapping
    public String guardar(@ModelAttribute Artista artista) {
        artistaService.agregarArtista(artista);
        return "redirect:/api/artistas/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Artista artista = artistaService.obtenerArtistaPorId(id);
        model.addAttribute("canciones", cancionService.listarCanciones());
        model.addAttribute("artista", artista);
        return "artista/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Artista artista) {
        artistaService.actualizarArtista(id, artista);
        return "redirect:/api/artistas/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        artistaService.eliminarArtista(id);
        return "redirect:/api/artistas/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("artistas", artistaService.listarArtistas());
        return "artista/index";
    }
}