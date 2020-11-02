package com.softplan.desafiofullstack.api.dto;

import com.softplan.desafiofullstack.model.entity.ProcessoUsuarioId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessoUsuarioDTO {
	
	//private ProcessoUsuarioId id;
	private Long usuarioTriador;
	private Long processo;
	private Long usuarioFinalizador;
	private String parecer;
	private String status;
}
