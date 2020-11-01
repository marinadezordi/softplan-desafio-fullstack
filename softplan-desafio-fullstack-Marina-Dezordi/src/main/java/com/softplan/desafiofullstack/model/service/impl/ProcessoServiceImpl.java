package com.softplan.desafiofullstack.model.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softplan.desafiofullstack.exception.RegraNegocioException;
import com.softplan.desafiofullstack.model.entity.Processo;
import com.softplan.desafiofullstack.model.repository.ProcessoRepository;
import com.softplan.desafiofullstack.model.service.ProcessoService;

@Service
public class ProcessoServiceImpl implements ProcessoService{

	private ProcessoRepository repository;
	
	public ProcessoServiceImpl(ProcessoRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Processo salvar(Processo processo) {
		validar(processo);
		return repository.save(processo);
	}

	@Override
	@Transactional
	public Processo atualizar(Processo processo) {
		Objects.requireNonNull(processo.getProcesso_id());
		validar(processo);
		return repository.save(processo);
	}

	@Override
	@Transactional
	public void deletar(Processo processo) {
		Objects.requireNonNull(processo.getProcesso_id());
		repository.delete(processo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Processo> buscar(Processo processoFiltro) {
		Example<Processo> example = Example
				.of(processoFiltro, ExampleMatcher
						.matching()
						.withIgnoreCase()
						.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void validar(Processo processo) {
		if(processo.getDescricao() == null || processo.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descrição válida.");
		}
		
		if(processo.getNome() == null || processo.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descrição válida.");
		}
	}

}
