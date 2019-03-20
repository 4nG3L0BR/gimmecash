package com.gft.gimmecash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gft.gimmecash.model.Solicitante;

public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {

//	Solicitante findOne(Long idNovo);


	@Query("select sum(s.valorSolicitado) from Solicitante s")
	Double valorSolSoma();
}
