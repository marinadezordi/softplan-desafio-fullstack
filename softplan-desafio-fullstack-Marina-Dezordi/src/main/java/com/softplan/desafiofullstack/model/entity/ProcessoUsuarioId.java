package com.softplan.desafiofullstack.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoUsuarioId implements Serializable{

	//@Column(name = "processo")
	private Long processo;
	
//	@Column(name="usuariotriador")
	private Long usuariotriador;

}
