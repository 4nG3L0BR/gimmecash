package com.gft.gimmecash.model;

import java.sql.Date;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Credor extends Usuario {

	private Double saldoInicial = 0.0;
	private Double saldoAtual = 0.0;
	private Double credito = 0.0;

	public Credor(Long id, String nome, String cpf, String rg, String sexo, String email, String telefone,
			Date dtNascimento, String empresa, Endereco endereco) {
		super(id, nome, cpf, rg, sexo, email, telefone, dtNascimento, empresa, endereco);
	}
	

}
