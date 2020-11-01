package com.softplan.desafiofullstack.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
@Embeddable
public class ProcessoUsuarioId implements Serializable{


	private Long processo;
	private Long usuariotriador;

}
