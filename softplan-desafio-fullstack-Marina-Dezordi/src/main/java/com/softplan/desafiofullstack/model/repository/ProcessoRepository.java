package com.softplan.desafiofullstack.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softplan.desafiofullstack.model.entity.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long>{

}
