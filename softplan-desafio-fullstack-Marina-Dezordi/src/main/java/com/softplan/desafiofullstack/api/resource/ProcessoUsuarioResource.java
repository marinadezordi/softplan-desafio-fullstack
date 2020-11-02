package com.softplan.desafiofullstack.api.resource;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softplan.desafiofullstack.api.dto.ProcessoDTO;
import com.softplan.desafiofullstack.api.dto.ProcessoUsuarioDTO;
import com.softplan.desafiofullstack.api.dto.UsuarioDTO;
import com.softplan.desafiofullstack.exception.RegraNegocioException;
import com.softplan.desafiofullstack.model.entity.Processo;
import com.softplan.desafiofullstack.model.entity.ProcessoUsuario;
import com.softplan.desafiofullstack.model.entity.ProcessoUsuarioId;
import com.softplan.desafiofullstack.model.entity.Usuario;
import com.softplan.desafiofullstack.model.enums.StatusProcesso;
import com.softplan.desafiofullstack.model.service.ProcessoService;
import com.softplan.desafiofullstack.model.service.ProcessoUsuarioService;
import com.softplan.desafiofullstack.model.service.UsuarioService;

@RestController
@RequestMapping("/api/processousuarios")
public class ProcessoUsuarioResource {
	
private UsuarioService usuarioTriadorService;
private UsuarioService usuarioFinalizadorService;
private ProcessoService processoService;
private ProcessoUsuarioService service;


	
	public ProcessoUsuarioResource(ProcessoUsuarioService processousuarioservice, ProcessoService processoservice, UsuarioService usuarioTriadorService, UsuarioService usuarioFinalizadorService) {
		this.service = processousuarioservice;
		this.usuarioFinalizadorService = usuarioFinalizadorService;
		this.usuarioTriadorService = usuarioTriadorService;
		this.processoService = processoservice;
	}
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "status", required = false) StatusProcesso status,
			@RequestParam(value = "parecer", required = false) String parecer,
			@RequestParam("usuarioTriador") Long usuarioTriador,
			@RequestParam("usuarioFinalizador") Long usuarioFinalizador,
			@RequestParam("processo") Long idprocesso)
		{
				ProcessoUsuario processousuarioFiltro = new ProcessoUsuario();
				processousuarioFiltro.setStatus(status);
				processousuarioFiltro.setParecer(parecer);
				
				Optional<Usuario> usuariot = usuarioTriadorService.obterPorId(usuarioTriador);
				Optional<Processo> processo = processoService.obterPorId(idprocesso);
				
				
				if( (!usuariot.isPresent()) || (!processo.isPresent()) ) {
					return ResponseEntity.badRequest().body("Não foi possível realizar a consullta. Processo/Usuário não encontrado na base de dados.");
				}else {
					processousuarioFiltro.setId(new ProcessoUsuarioId(idprocesso, usuarioTriador));
				}
				
				/*if(usuariot.isPresent() ) {
					return ResponseEntity.badRequest().body("Não foi possível realizar a consullta. Processo/Usuário não encontrado na base de dados.");
				}else {
					processousuarioFiltro.setId(usuariot);
				}
				
			
				
				if(processo.isPresent()) {
					return ResponseEntity.badRequest().body("Não foi possível realizar a consullta. Processo/Usuário não encontrado na base de dados.");
				}else {
					processousuarioFiltro.setProcesso(processo.get());
					//processousuarioFiltro.setId(processo.get());
				}	
				*/
				Optional<Usuario> usuariof = usuarioFinalizadorService.obterPorId(usuarioFinalizador);
				if(!usuariof.isPresent()) {
					return ResponseEntity.badRequest().body("Não foi possível realizar a consullta. Processo/Usuário não encontrado na base de dados.");
				}else {
					processousuarioFiltro.setUsuariofinalizador(usuariof.get());
				}
		
				List<ProcessoUsuario> processousuario = service.buscar(processousuarioFiltro);				
				return ResponseEntity.ok(processousuario);		
		}
	
	@PostMapping
	public ResponseEntity salvar (@RequestBody ProcessoUsuarioDTO dto) {
		try{
			ProcessoUsuario entidade = converter(dto);
			entidade = service.salvar(entidade);
			return ResponseEntity.ok(entidade);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	@PutMapping("{id}")
	public ResponseEntity atualizar (@PathVariable("id") ProcessoUsuarioId id,									
									@RequestBody ProcessoUsuarioDTO dto) {
		return service.obterPorId(id.getProcesso(), id.getUsuariotriador()).map(entity -> {
			try{
				ProcessoUsuario processoUsuario = converter(dto);
				processoUsuario.setId(entity.getId());
				service.atualizar(processoUsuario);
				return ResponseEntity.ok(processoUsuario);
				
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> 
			new ResponseEntity("Processo/Usuário não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
		
	}
	
	
	
/*	@PutMapping("{idProcesso}"/"{idUsuarioTriador}")
	public ResponseEntity atualizar (@PathVariable("idProcesso") Long idProcesso,
									@PathVariable("idUsuarioTriador") Long idUsuarioTriador,
									@RequestBody ProcessoUsuarioDTO dto) {
		return service.obterPorId(idProcesso, idUsuarioTriador).map(entity -> {
			try{
				ProcessoUsuario processoUsuario = converter(dto);
				processoUsuario.setId(entity.getId());
				service.atualizar(processoUsuario);
				return ResponseEntity.ok(processoUsuario);
				
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> 
			new ResponseEntity("Processo/Usuário não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
		
	}*/
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") ProcessoUsuarioId id) {
		return service.obterPorId(id.getProcesso(), id.getUsuariotriador()).map(entidade ->{
			service.deletar(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			}).orElseGet(() ->
			new ResponseEntity("Processo/Usuário não encontrado na base de dados.", HttpStatus.BAD_REQUEST) );
	}
	
	
	private ProcessoUsuario converter(ProcessoUsuarioDTO dto) {
		
		ProcessoUsuario processoUsuario = new ProcessoUsuario();
		//ProcessoUsuarioId processoUsuarioId = new ProcessoUsuarioId();
		
		Usuario usuariotriador = usuarioTriadorService
				.obterPorId(dto.getUsuarioTriador())
				.orElseThrow( () -> new RegraNegocioException("Usuário Triador não encontrado para Id informado"));
		
		Processo processo = processoService
				.obterPorId(dto.getProcesso())
				.orElseThrow( () -> new RegraNegocioException("Processo não encontrado para Id informado"));
		
		Usuario usuariofinalizador = usuarioFinalizadorService
				.obterPorId(dto.getUsuarioFinalizador())
				.orElseThrow( ()-> new RegraNegocioException("Usuário Finalizador não encontrado para Id informado"));
		
		processoUsuario.setId(new ProcessoUsuarioId(processo.getProcesso_id(), usuariotriador.getUsuario_id()));
		
		processoUsuario.setParecer(dto.getParecer());
		
		processoUsuario.setUsuariofinalizador(usuariofinalizador);
		
		if(dto.getStatus() != null) {
			processoUsuario.setStatus(StatusProcesso.valueOf(dto.getStatus()));
		}
		return processoUsuario;
	}

}
