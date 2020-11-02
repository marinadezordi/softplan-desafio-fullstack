package com.softplan.desafiofullstack.model.entity;
import javax.persistence.*;

import com.softplan.desafiofullstack.model.enums.StatusProcesso;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "processousuario", schema= "gerproc")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoUsuario {
	
	@EmbeddedId
	private ProcessoUsuarioId id;
	

	/*//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ManyToOne
	@JoinColumn(name = "processo")
	private Processo processo;
	

	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ManyToOne
	@JoinColumn(name = "usuariotriador")
	private Usuario usuariotriador;*/
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusProcesso status;
	
	@Column(name = "parecer")
	private String parecer;

	@ManyToOne
	@JoinColumn(name = "usuariofinalizador")
	private Usuario usuariofinalizador;
	
	
}