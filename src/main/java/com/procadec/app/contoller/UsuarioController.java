package com.procadec.app.contoller;

import java.util.List;
import java.util.Optional;

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

	// Los siguientes métodos correspondientes a endpoints estarían abiertos


	// Crear un nuevo Usuario
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Usuario usuarioDetails) {

		List<Usuario> usuarios = (List<Usuario>) usuarioService.findAll();

		// Comprobamos que existe el Usuario en la base de datos
		for (Usuario usuario : usuarios) {
			if(!(usuario.getEmail().equals(usuarioDetails.getEmail()))) {
				usuarioService.save(usuarioDetails);
				return ResponseEntity.ok(usuario);
			}
		}				

		return ResponseEntity.ok().build();
	}

	// Iniciar sesión
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuarioDetails) {
		List<Usuario> usuarios = (List<Usuario>) usuarioService.findAll();

		// Comprobamos que existe el Usuario en la base de datos
		for (Usuario usuario : usuarios) {
			if(usuario.getNombre().equals(usuarioDetails.getNombre()) && 
					usuario.getContrasenia().equals(usuarioDetails.getContrasenia())) {
				return ResponseEntity.ok(usuario);
			}
		}		

		return ResponseEntity.notFound().build();

	}

	/* Los siguientes métodos correspondientes a endpoints solo se deberían utilizar para administración
	 * En una versión futura habria que desdoblar el fichero y su ruta en /api/usuarios y /api/admin/usuarios
	 * De momento se mantiene así para hacer pruebas. */

	// Leer un Usuario. 
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.findById(id);

		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(usuario);
	}

	// Actualizar un Usuario. 
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

	// Eliminar un Usuario. 
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!usuarioService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		usuarioService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	// Leer todos los Usuarios.
	@GetMapping
	public List<Usuario> readAll() {

		List<Usuario> usuarios = (List<Usuario>) usuarioService.findAll();

		return usuarios;
	}	

}
