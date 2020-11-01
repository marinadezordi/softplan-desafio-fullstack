package com.softplan.desafiofullstack.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.softplan.desafiofullstack.model.entity.Usuario;
import com.softplan.desafiofullstack.model.enums.TipoUsuario;
import com.softplan.desafiofullstack.model.repository.UsuarioRepository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenario
		Usuario usuario = Usuario.builder().nome("Usuario").email("usuario@email.com").senha("123").tipousuario(TipoUsuario.ADMINISTRADOR).build();
		entityManager.persist(usuario);
		
		//acao
		boolean result = repository.existsByEmail("usuario@email.com");	
		
		//verificao
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void deveRetonarFalsoQuandoNaoHouverUsuarioCadatradoComEmail() {
		//cenario
		
		//acao
		boolean result = repository.existsByEmail("usuario@email.com");

		//verificaco
		Assertions.assertThat(result).isFalse();	
	}	
	
	@Test
	public void devePersistirUsuarioBaseDados() {
		//cenario
		Usuario usuario = criarUsuario();
		
		//verificacao
		Usuario usuariosalvo = repository.save(usuario);
		Assertions.assertThat(usuariosalvo.getUsuario_id()).isNotNull();
		
	}
	
	@Test
	public void deveBuscarUsuarioPorEmail() {
		
		//cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	
	@Test
	public void deveRetornarVzioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		
		//verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
	public static Usuario criarUsuario() {
		return Usuario
					.builder()
					.nome("Usuario")
					.email("usuario@email.com")
					.senha("123")
					.tipousuario(TipoUsuario.ADMINISTRADOR)
					.build();
	}
}
