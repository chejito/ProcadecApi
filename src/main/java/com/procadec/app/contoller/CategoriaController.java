package com.procadec.app.contoller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.procadec.app.entity.Categoria;
import com.procadec.app.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	// Crear nueva Categoría
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoria));
	}

	// Leer una Categoría
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);

		if (!categoria.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(categoria);
	}

	// Actualizar una Categoría
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Categoria categoriaDetails, @PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);

		if (!categoria.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		categoria.get().setNombre(categoriaDetails.getNombre());

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoria.get()));

	}

	// Eliminar una Categoría
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!categoriaService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		categoriaService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	// Leer todas las Categorías
	@GetMapping
	public List<Categoria> readAll() {
		List<Categoria> categorias = StreamSupport.stream(categoriaService.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return categorias;
	}
}
