package com.softplan.desafiofullstack.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "processo", schema = "gerproc")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
