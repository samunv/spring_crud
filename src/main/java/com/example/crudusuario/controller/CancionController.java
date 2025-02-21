package com.example.crudusuario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.crudusuario.model.Cancion;
import com.example.crudusuario.model.Lista;
import com.example.crudusuario.service.CancionService;
import com.example.crudusuario.service.ListaService;

@Controller
@RequestMapping("/api/canciones")
public class CancionController {

    private final CancionService cancionService;
    private final ListaService listaService;

    public CancionController(CancionService cancionService, ListaService listaService) {
        this.cancionService = cancionService;
        this.listaService = listaService;
    }

    // 📌 Mostrar formulario para crear una nueva canción
    @GetMapping("/crear")
    public String crearForm(Model model) {
        model.addAttribute("cancion", new Cancion());
        model.addAttribute("listas", listaService.listarListas());
        return "cancion/crear";
    }

    @PostMapping
    public String crearCancion(@RequestParam String titulo,
                               @RequestParam String genero,
                               @RequestParam Long lista_id,
                               RedirectAttributes redirectAttributes) {
        Lista lista = listaService.obtenerListaPorId(lista_id);
        if (lista == null) {
            redirectAttributes.addFlashAttribute("error", "Lista no encontrada");
            return "redirect:/api/canciones/crear";
        }

        Cancion cancion = new Cancion();
        cancion.setTitulo(titulo);
        cancion.setGenero(genero);
        cancion.setLista(lista); 

        cancionService.agregarCancion(cancion);
        
        return "redirect:/api/canciones/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Cancion cancion = cancionService.obtenerCancionPorId(id);
        if (cancion == null) {
            return "redirect:/api/canciones/listar"; 
        }

        model.addAttribute("cancion", cancion);
        model.addAttribute("listas", listaService.listarListas()); 
        return "cancion/editar";
    }

 
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, 
                             @RequestParam String titulo, 
                             @RequestParam String genero, 
                             @RequestParam Long lista_id,
                             RedirectAttributes redirectAttributes) {
        Cancion cancion = cancionService.obtenerCancionPorId(id);
        if (cancion == null) {
            redirectAttributes.addFlashAttribute("error", "Canción no encontrada");
            return "redirect:/api/canciones/listar";
        }

        Lista lista = listaService.obtenerListaPorId(lista_id);
        if (lista == null) {
            redirectAttributes.addFlashAttribute("error", "Lista no encontrada");
            return "redirect:/api/canciones/editar/" + id;
        }

    
        cancion.setTitulo(titulo);
        cancion.setGenero(genero);
        cancion.setLista(lista);

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
