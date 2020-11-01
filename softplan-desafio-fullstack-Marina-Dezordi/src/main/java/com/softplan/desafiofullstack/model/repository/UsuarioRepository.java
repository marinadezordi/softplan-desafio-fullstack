package com.softplan.desafiofullstack.model.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softplan.desafiofullstack.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email);
	
}
