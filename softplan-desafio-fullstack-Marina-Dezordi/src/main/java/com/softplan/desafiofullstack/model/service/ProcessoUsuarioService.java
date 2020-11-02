package com.softplan.desafiofullstack.model.service;

import java.util.List;
import java.util.Optional;

import com.softplan.desafiofullstack.model.entity.Processo;
import com.softplan.desafiofullstack.model.entity.ProcessoUsuario;
import com.softplan.desafiofullstack.model.entity.Usuario;
import com.softplan.desafiofullstack.model.enums.StatusProcesso;

public interface ProcessoUsuarioService {
	
	ProcessoUsuario salvar(ProcessoUsuario processoUsuario);
	
	ProcessoUsuario atualizar(ProcessoUsuario processoUsuario);
	
	void deletar(ProcessoUsuario processoUsuario);
	
	List<ProcessoUsuario> buscar(ProcessoUsuario processoUsuarioFiltro);
	
	void atualizarStatus(ProcessoUsuario processoUsuario, StatusProcesso status);
	
	void atualizarParecer(ProcessoUsuario processoUsuario, String parecer);
	
	void validar(ProcessoUsuario processoUsuario);
	
	public Optional<ProcessoUsuario> obterPorId(Long processo, Long usuarioTriador);
	
}
