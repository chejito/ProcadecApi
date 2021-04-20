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

import com.procadec.app.entity.Producto;
import com.procadec.app.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	// Crear nuevo Producto
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Producto producto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
	}
	
	// Leer un Producto	
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Optional<Producto> oProducto = productoService.findById(id);
		
		if(!oProducto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oProducto);
	}
	
	// Actualizar un Producto
		@PutMapping("/{id}")
		public ResponseEntity<?> update(@RequestBody Producto productoDetails, @PathVariable(value = "id") Long id) {
			Optional<Producto> oProducto = productoService.findById(id);
			
			if(!oProducto.isPresent()) {
				return ResponseEntity.notFound().build();
			}			
			
			oProducto.get().setNombre(productoDetails.getNombre());
			oProducto.get().setDescripcion(productoDetails.getDescripcion());
			oProducto.get().setCategoria(productoDetails.getCategoria());
			oProducto.get().setCantidad(productoDetails.getCantidad());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(oProducto.get()));		
			
		}
		
		// Eliminar un Producto
		@DeleteMapping("/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
				
			if(!productoService.findById(id).isPresent()) {
				return ResponseEntity.notFound().build();
			}
				
			productoService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		// Leer todos los Productos
		@GetMapping
		public List<Producto> readAll() {
			List<Producto> productos = StreamSupport
					.stream(productoService.findAll().spliterator(), false)
					.collect(Collectors.toList());
			
			return productos;
		}
				
}
