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
		
		// Comprobar que no existe el Usuario ya
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
	}

	// Leer un Usuario
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.findById(id);

		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(usuario);
	}

	// Actualizar un Usuario
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Usuario usuarioDetails, @PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		
		if(!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		usuario.get().setNombre(usuarioDetails.getNombre());
		usuario.get().setContrasenia(usuarioDetails.getContrasenia());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario.get()));
	}

	// Eliminar un Usuario
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!usuarioService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		usuarioService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	// Leer todos los Usuarios
	@GetMapping
	public List<Usuario> readAll() {
		List<Usuario> usuarios = StreamSupport.stream(usuarioService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		// List<Usuario> usuarios = (List<Usuario>) usuarioService.findAll();

		return usuarios;
	}

	// Iniciar sesión
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuarioDetails) {
		List<Usuario> usuarios = (List<Usuario>) usuarioService.findAll();
		
		for (Usuario usuario : usuarios) {
			if(usuario.getEmail().equals(usuarioDetails.getEmail()) && 
					usuario.getContrasenia().equals(usuarioDetails.getContrasenia())) {
				return ResponseEntity.ok().build();
			}
		}		

		return ResponseEntity.notFound().build();

	}

}
