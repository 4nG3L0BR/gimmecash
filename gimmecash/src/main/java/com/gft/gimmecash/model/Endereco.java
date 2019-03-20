package com.gft.gimmecash.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String rua;
	private String cidade;
	private String estado;
	private String pais;
	public Endereco(String rua, String cidade, String estado, String pais) {
		this.rua = rua;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
	}
	
	
	
	
}
