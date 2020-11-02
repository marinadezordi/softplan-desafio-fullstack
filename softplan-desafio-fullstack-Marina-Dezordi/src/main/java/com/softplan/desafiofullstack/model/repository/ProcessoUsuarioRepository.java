package com.softplan.desafiofullstack.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softplan.desafiofullstack.model.entity.ProcessoUsuario;
import com.softplan.desafiofullstack.model.entity.ProcessoUsuarioId;

public interface ProcessoUsuarioRepository extends JpaRepository<ProcessoUsuario, ProcessoUsuarioId>{
	//List<ProcessoUsuario> findByIdProcessoAndIdUsuarioTriador(Long processo, Long usuariotriador);
}
