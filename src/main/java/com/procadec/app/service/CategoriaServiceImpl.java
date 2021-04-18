package com.procadec.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.procadec.app.entity.Categoria;
import com.procadec.app.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}

	@Override
	@Transactional
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		categoriaRepository.deleteById(id);
	}

}
