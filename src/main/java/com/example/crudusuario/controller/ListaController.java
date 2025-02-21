package com.example.crudusuario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.crudusuario.model.Lista;
import com.example.crudusuario.service.ListaService;
import com.example.crudusuario.service.CancionService;

@Controller
@RequestMapping("/api/listas")
public class ListaController {
	private final ListaService listaService;
	private final CancionService cancionService; // Corregir el nombre de la variable

	// Inyectar ambos servicios
	public ListaController(ListaService listaService, CancionService cancionService) {
		this.listaService = listaService;
		this.cancionService = cancionService; // Inicializar cancionService
	}

	@GetMapping("/crear")
	public String crearForm(Model model) {
		model.addAttribute("lista", new Lista());
		model.addAttribute("canciones", cancionService.listarCanciones()); // Pasando las canciones al modelo
		return "lista/crear";
	}

	@PostMapping
	public String guardar(@ModelAttribute Lista lista) {
		listaService.agregarLista(lista);
		return "redirect:/api/listas/listar";
	}

	@GetMapping("/editar/{id}")
	public String editarForm(@PathVariable Long id, Model model) {
		Lista lista = listaService.obtenerListaPorId(id);
		model.addAttribute("canciones", cancionService.listarCanciones());
		model.addAttribute("lista", lista);
		return "lista/editar";
	}

	@PostMapping("/actualizar/{id}")
	public String actualizar(@PathVariable Long id, @ModelAttribute Lista lista) {
		listaService.actualizarLista(id, lista);
		return "redirect:/api/listas/listar";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		listaService.eliminarLista(id);
		return "redirect:/api/listas/listar";
	}

	// Listar todas las listas
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("listas", listaService.listarListas());
		return "lista/index"; // Vista de la lista de canciones
	}
}
