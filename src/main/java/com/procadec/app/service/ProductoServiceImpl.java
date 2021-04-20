package com.procadec.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.procadec.app.entity.Producto;
import com.procadec.app.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Producto> findAll() {
		return productoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findById(Long id) {		
		return productoRepository.findById(id);
	}
		
	@Override
	@Transactional
	public Producto save(Producto producto) {		
		return productoRepository.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productoRepository.deleteById(id);
		
	}

}
