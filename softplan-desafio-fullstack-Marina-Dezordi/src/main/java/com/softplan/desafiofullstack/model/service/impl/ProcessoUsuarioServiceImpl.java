package com.softplan.desafiofullstack.model.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softplan.desafiofullstack.exception.RegraNegocioException;
import com.softplan.desafiofullstack.model.entity.ProcessoUsuario;
import com.softplan.desafiofullstack.model.enums.StatusProcesso;
import com.softplan.desafiofullstack.model.repository.ProcessoUsuarioRepository;
import com.softplan.desafiofullstack.model.service.ProcessoUsuarioService;

@Service
public class ProcessoUsuarioServiceImpl implements ProcessoUsuarioService {

	private ProcessoUsuarioRepository repository;
	
	public ProcessoUsuarioServiceImpl(ProcessoUsuarioRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public ProcessoUsuario salvar(ProcessoUsuario processoUsuario) {
		validar(processoUsuario);
		processoUsuario.setStatus(StatusProcesso.CRIADO);
		return repository.save(processoUsuario);
	}

	@Override
	@Transactional
	public ProcessoUsuario atualizar(ProcessoUsuario processoUsuario) {
		Objects.requireNonNull(processoUsuario.getId());
		validar(processoUsuario);
		return repository.save(processoUsuario);
	}

	@Override
	@Transactional
	public void deletar(ProcessoUsuario processoUsuario) {
		Objects.requireNonNull(processoUsuario.getId());		
		repository.delete(processoUsuario);	
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProcessoUsuario> buscar(ProcessoUsuario processoUsuarioFiltro) {
		Example<ProcessoUsuario> example = Example
				.of(processoUsuarioFiltro, ExampleMatcher
						.matching()
						.withIgnoreCase()
						.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(ProcessoUsuario processoUsuario, StatusProcesso status) {
		processoUsuario.setStatus(status);
		atualizar(processoUsuario);
	}

	@Override
	public void atualizarParecer(ProcessoUsuario processoUsuario, String parecer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validar(ProcessoUsuario processoUsuario) {
		if(processoUsuario.getParecer() == null || processoUsuario.getParecer().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descrição válida.");
		}
		
		if(processoUsuario.getUsuariofinalizador() == null || processoUsuario.getUsuariofinalizador().getUsuario_id() == null){
			throw new RegraNegocioException("Informe um Usuário Finalizador.");
		}
		
		if(processoUsuario.getId().getProcesso() == null) {
			throw new RegraNegocioException("Informe um Processo.");
		}
		if(processoUsuario.getId().getUsuariotriador() == null) {
			throw new RegraNegocioException("Informe um Usuário Triador.");
		}
		if(processoUsuario.getStatus() == null) {
			throw new RegraNegocioException("Informe um Status.");
		}
}


}
