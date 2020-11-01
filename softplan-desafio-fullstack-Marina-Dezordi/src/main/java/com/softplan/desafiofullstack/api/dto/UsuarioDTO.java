package com.softplan.desafiofullstack.api.dto;

import com.softplan.desafiofullstack.model.enums.TipoUsuario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDTO {
	
	private String email;
	private	String nome;
	private String senha;
	private TipoUsuario tipousuario;

}
