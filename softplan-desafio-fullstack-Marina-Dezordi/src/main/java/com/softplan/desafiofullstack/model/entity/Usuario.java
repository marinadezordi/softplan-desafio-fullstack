package com.softplan.desafiofullstack.model.entity;

import javax.persistence.*;

import com.softplan.desafiofullstack.model.enums.TipoUsuario;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
@Entity
@Table(name = "usuario", schema = "gerproc")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	@Column(name = "usuario_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuario_id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "senha")
	private String senha;

	@Column(name = "tipousuario")
	@Enumerated(value = EnumType.STRING)
	private TipoUsuario tipousuario;
	
	public static void main(String[] args) {
		Usuario.builder().email("marinadezordi@gmail.com").nome("Marina").senha("123").tipousuario(TipoUsuario.ADMINISTRADOR).build();
	}
}
