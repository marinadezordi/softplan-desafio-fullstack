package com.softplan.desafiofullstack.model.entity;
import javax.persistence.*;

import com.softplan.desafiofullstack.model.enums.StatusProcesso;

import lombok.Data;
import lombok.Builder;

@Entity
@Table(name = "processousuario", schema= "gerproc")
@Builder
@Data
public class ProcessoUsuario {
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ManyToOne
	@JoinColumn(name = "processo")
	private Long processo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ManyToOne
	@JoinColumn(name = "usuariotriador")
	private Long usuariotriador;
	*/
	
	@ManyToOne
	@JoinColumn(name = "usuariofinalizador")
	private Usuario usuariofinalizador;
	
	
	@EmbeddedId
	private ProcessoUsuarioId id;
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusProcesso status;
	
	@Column(name = "parecer")
	private String parecer;

}