package com.procadec.app.service;

import java.util.Optional;

import com.procadec.app.entity.Categoria;

public interface CategoriaService {

	public Iterable<Categoria> findAll();

	public Optional<Categoria> findById(Long id);

	public Categoria save(Categoria categoria);

	public void deleteById(Long id);
}
