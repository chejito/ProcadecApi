package com.procadec.app.service;

import java.util.List;
import java.util.Optional;

import com.procadec.app.entity.Producto;

public interface ProductoService {

	public Iterable<Producto> findAll();
	
	public Optional<Producto> findById(Long id);
	
	List<Producto> findByCategoria(Long categoria);
	
	public Producto save(Producto producto);
	
	public void deleteById(Long id);
	
}
