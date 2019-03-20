package com.gft.gimmecash.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Operacao {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "id_solicitante")
	private Solicitante idSolicitante;
	
	@OneToOne
	@JoinColumn(name = "id_credor")
	private Credor idCredor;
	
	private Double valor;

	public Operacao(Long id, Solicitante solicitante, Credor credor, Double valor) {
		this.id = id;
		this.idSolicitante = solicitante;
		this.idCredor = credor;
		this.valor = valor;
	}
	

}
