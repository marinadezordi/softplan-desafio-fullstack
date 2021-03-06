package com.softplan.desafiofullstack.api.resource;

import java.util.List;
import java.util.Optional;

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
import com.softplan.desafiofullstack.exception.RegraNegocioException;
import com.softplan.desafiofullstack.model.entity.Processo;
import com.softplan.desafiofullstack.model.service.ProcessoService;
import com.softplan.desafiofullstack.model.service.UsuarioService;

@RestController
@RequestMapping("/api/processos")
public class ProcessoResource {
	
	private ProcessoService service;
	
	public ProcessoResource(ProcessoService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity buscar(
		@RequestParam(value = "descricao", required = false) String descricao,
		@RequestParam(value = "nome", required = false) String nome
		)
	{
			Processo processoFiltro = new Processo();
			processoFiltro.setDescricao(descricao);
			processoFiltro.setNome(nome);
			
			List<Processo> processo = service.buscar(processoFiltro);
			
			return ResponseEntity.ok(processo);		
	}
	
	@PostMapping
	public ResponseEntity salvar (@RequestBody ProcessoDTO dto) {
		
		try{
			Processo entidade = converter(dto);
		
		entidade = service.salvar(entidade);
		return new ResponseEntity(entidade, HttpStatus.CREATED);
		}catch(RegraNegocioException e ) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProcessoDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try{
				Processo processo = converter(dto);
				processo.setProcesso_id(entity.getProcesso_id());
				service.atualizar(processo);
				return ResponseEntity.ok(processo);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> 
			new ResponseEntity("Processo não encontrado na base de dados.", HttpStatus.BAD_REQUEST) );		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id) {
		return service.obterPorId(id).map(entidade ->{
			service.deletar(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			}).orElseGet(() ->
			new ResponseEntity("Processo não encontrado na base de dados.", HttpStatus.BAD_REQUEST) );
	}
	
	
	private Processo converter(ProcessoDTO dto) {
		Processo processo = new Processo();
		processo.setDescricao(dto.getDescricao());
		processo.setNome(dto.getNome());
		return processo;
	}

}
