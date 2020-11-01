package com.softplan.desafiofullstack.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.softplan.desafiofullstack.exception.ErroAutenticaco;
import com.softplan.desafiofullstack.exception.RegraNegocioException;
import com.softplan.desafiofullstack.model.entity.Usuario;
import com.softplan.desafiofullstack.model.enums.TipoUsuario;
import com.softplan.desafiofullstack.model.repository.UsuarioRepository;
import com.softplan.desafiofullstack.model.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	@Test
	public void deveSalvarUmUsuario() {
		//cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder()
				.usuario_id(1l)
				.nome("Usuario")
				.email( "email@email.com")
				.senha("senha")
				.tipousuario(TipoUsuario.ADMINISTRADOR).build(); 
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		//acao
		Usuario usuariosalvo = service.salvarUsuario(new Usuario());
		
		//verificacao
		Assertions.assertThat(usuariosalvo).isNotNull();
		Assertions.assertThat(usuariosalvo.getUsuario_id()).isEqualTo(1l);
		Assertions.assertThat(usuariosalvo.getNome()).isEqualTo("Usuario");
		Assertions.assertThat(usuariosalvo.getEmail()).isEqualTo( "email@email.com");
		Assertions.assertThat(usuariosalvo.getSenha()).isEqualTo("senha");
		Assertions.assertThat(usuariosalvo.getTipousuario()).isEqualTo(TipoUsuario.ADMINISTRADOR);
	}
	
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		//cenario
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//acao
		service.salvarUsuario(usuario);
		
		//verificacao
		Mockito.verify(repository, Mockito.never()).save(usuario);
	}
	
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		//cenario
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).usuario_id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//acap
		Usuario result = service.autenticar(email, senha);
		
		//verificaco
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
	//cenario
	Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
	
	//acao
	Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "senha"));
	Assertions.assertThat(exception)
		.isInstanceOf(ErroAutenticaco.class)
		.hasMessage("Usuário não encontrado para o email informado.");	
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		//cenario
		Usuario usuario = Usuario.builder().email("email@email.com").senha("senha").build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//acao
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticaco.class).hasMessage("Senha inválida.");
	}
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		repository.deleteAll();
		//acao
		service.validarEmail("email@email.com");
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado(){
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

		//acao
		service.validarEmail("usuario@email.com");
	}
}
