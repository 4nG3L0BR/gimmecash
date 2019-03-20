package com.gft.gimmecash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gft.gimmecash.model.Credor;



public interface CredorRepository  extends JpaRepository<Credor, Long> {

	@Query("select sum(c.saldoAtual) from Credor c")
	Double valorSaldoSoma();
}
