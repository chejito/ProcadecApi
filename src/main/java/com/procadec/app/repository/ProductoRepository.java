package com.procadec.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.procadec.app.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
		
}
