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

import com.procadec.app.entity.Usuario;
import com.procadec.app.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	// Crear un nuevo Usuario
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
	}
	
	// Leer un Usuario
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Optional<Usuario> oUsuario = usuarioService.findById(id);
		
		if(!oUsuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oUsuario);
	}
	
	// Actualizar un Usuario
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Usuario usuarioDetails, @PathVariable(value = "id") Long id) {
		Optional<Usuario> oUsuario = usuarioService.findById(id);
		
		if(!oUsuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		oUsuario.get().setNombre(usuarioDetails.getNombre());
		oUsuario.get().setContrasenia(usuarioDetails.getContrasenia());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(oUsuario.get()));
	}

	// Eliminar un Usuario
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		if(!usuarioService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		usuarioService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	// Leer todos los Usuarios
	@GetMapping
	public List<Usuario> readAll() {
		List<Usuario> usuarios = StreamSupport
				.stream(usuarioService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return usuarios;
	}

}
