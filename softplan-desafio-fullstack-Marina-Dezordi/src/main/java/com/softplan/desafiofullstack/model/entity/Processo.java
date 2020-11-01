package com.softplan.desafiofullstack.model.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "processo", schema = "gerproc")
@Builder
@Data
public class Processo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "processo_id")
	private Long processo_id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;	
}
