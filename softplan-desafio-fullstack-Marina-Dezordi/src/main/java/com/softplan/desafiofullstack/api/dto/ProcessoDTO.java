package com.softplan.desafiofullstack.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessoDTO {
	
	private Long processoId;
	private String nome;
	private	String descricao;
}
