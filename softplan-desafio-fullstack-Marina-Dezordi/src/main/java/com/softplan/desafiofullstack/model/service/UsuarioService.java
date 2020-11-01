package com.softplan.desafiofullstack.model.service;

import com.softplan.desafiofullstack.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	Usuario salvarUsuario(Usuario usuario);
	void validarEmail(String email);

}
