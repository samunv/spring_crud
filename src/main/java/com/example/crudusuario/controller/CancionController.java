package com.example.crudusuario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.crudusuario.model.Artista;
import com.example.crudusuario.model.Cancion;
import com.example.crudusuario.service.CancionService;
import com.example.crudusuario.service.ArtistaService;

@Controller
@RequestMapping("/api/canciones")
public class CancionController {

    private final CancionService cancionService;
    private final ArtistaService artistaService;

    public CancionController(CancionService cancionService, ArtistaService artistaService) {
        this.cancionService = cancionService;
        this.artistaService = artistaService;
    }

    @GetMapping("/crear")
    public String crearForm(Model model) {
        model.addAttribute("cancion", new Cancion());
        model.addAttribute("artistas", artistaService.listarArtistas());
        return "cancion/crear";
    }

    @PostMapping
    public String crearCancion(@RequestParam String titulo,
            @RequestParam String genero,
            @RequestParam Long artista_id,
            RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Título: " + titulo);
            System.out.println("Género: " + genero);
            System.out.println("ID del Artista: " + artista_id.longValue());

            Artista artista = artistaService.obtenerArtistaPorId(artista_id.longValue());

            Cancion cancion = new Cancion();
            cancion.setTitulo(titulo);
            cancion.setGenero(genero);
            cancion.setArtista(artista);

            cancionService.agregarCancion(cancion);

            return "redirect:/api/canciones/listar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Artista no encontrado");
            return "redirect:/api/canciones/crear";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Cancion cancion = cancionService.obtenerCancionPorId(id);
        if (cancion == null) {
            return "redirect:/api/canciones/listar";
        }

        model.addAttribute("cancion", cancion);
        model.addAttribute("artistas", artistaService.listarArtistas());
        return "cancion/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String genero,
            @RequestParam Long artista_id,
            RedirectAttributes redirectAttributes) {
        Cancion cancion = cancionService.obtenerCancionPorId(id);
        if (cancion == null) {
            redirectAttributes.addFlashAttribute("error", "Canción no encontrada");
            return "redirect:/api/canciones/listar";
        }

        Artista artista = artistaService.obtenerArtistaPorId(artista_id);
        if (artista == null) {
            redirectAttributes.addFlashAttribute("error", "Artista no encontrado");
            return "redirect:/api/canciones/editar/" + id;
        }

        cancion.setTitulo(titulo);
        cancion.setGenero(genero);
        cancion.setArtista(artista);

        cancionService.actualizarCancion(id, cancion);
        redirectAttributes.addFlashAttribute("success", "Canción actualizada exitosamente");

        return "redirect:/api/canciones/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        cancionService.eliminarCancion(id);
        redirectAttributes.addFlashAttribute("success", "Canción eliminada exitosamente");
        return "redirect:/api/canciones/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("canciones", cancionService.listarCanciones());
        return "cancion/index";
    }
}