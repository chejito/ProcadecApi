package com.procadec.app.service;

import java.util.Optional;

import com.procadec.app.entity.Usuario;

public interface UsuarioService {
	
	public Iterable<Usuario> findAll();
	
	public Optional<Usuario> findById(Long id);
	
	public Usuario save(Usuario usuario);
	
	public void deleteById(Long id);
	
	//Optional<Usuario> findByEmail(String email);

}
