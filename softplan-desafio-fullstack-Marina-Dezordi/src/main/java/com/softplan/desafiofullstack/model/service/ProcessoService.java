package com.softplan.desafiofullstack.model.service;

import java.util.List;

import com.softplan.desafiofullstack.model.entity.Processo;


public interface ProcessoService {
	
	Processo salvar(Processo processo);
	
	Processo atualizar(Processo processo);
	
	void deletar(Processo processo);
	
	List<Processo> buscar(Processo processoFiltro);
	
	void validar(Processo processo);
	
}
