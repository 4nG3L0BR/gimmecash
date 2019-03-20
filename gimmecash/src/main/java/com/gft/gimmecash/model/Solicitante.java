package com.gft.gimmecash.model;

import java.sql.Date;


import javax.persistence.Entity;



import com.gft.gimmecash.controller.Rating;
import com.gft.gimmecash.exceptions.UsuarioException;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Solicitante extends Usuario {
	private Double valorComJuros = 0.0;
	private Double renda = 0.0;
	private Integer rating;// enum
	private Double valorSolicitado = 0.0;
	private Integer numParcelas;
	private Date dataSolicitacao;

	

//	@Override
//	public String toString() {
//		return "Renda = " + renda;
//	}

	public void tomarEmprestimo(Double valorSolicitado, Double renda) {
		validarValorSolitado(valorSolicitado, renda);
		estipularNumParcelas(valorSolicitado);

	}

	public void setValorComJuros(Double valorComJuros) {
		this.valorComJuros = valorComJuros;
	}

	private Integer estipularNumParcelas(Double valorSolicitado) {
		Double limiteParcelaEmprestimo = renda * 0.3;

		return (int) (valorSolicitado / limiteParcelaEmprestimo);

	}

	private Double validarValorSolitado(Double valor, Double renda) {
		Double limiteParcelaEmprestimo = renda * 0.3;
		Integer numParcelas = (int) (valor / limiteParcelaEmprestimo);

		if (valor != null && valor > 0 && valor / numParcelas <= limiteParcelaEmprestimo) {

		}
		return limiteParcelaEmprestimo;
	}

	public Solicitante(Long id, String nome, String cpf, String rg, String sexo, String email, String telefone,
			Date dtNascimento, String empresa, Endereco endereco, Double renda, Integer rating, Date dataSolicitacao,
			Integer status) {
		super(id, nome, cpf, rg, sexo, email, telefone, dtNascimento, empresa, endereco);
		this.renda = renda;
		this.rating = rating;
		this.dataSolicitacao = dataSolicitacao;
	}

	// valida o juros em cada solicitante
	public Double validaJuros(Solicitante sol) throws UsuarioException {

		Double taxa = sol.getValorSolicitado() * Rating.get(sol.getRating()).getTaxaJuros();

		Double valorComJuros = sol.getValorSolicitado() + taxa;
		sol.setValorComJuros(valorComJuros);
		return valorComJuros;
	}

	// valida a quantidade mínima de parcelas
	public Integer validaQdeMinParcelas(Solicitante sol) throws UsuarioException {
		Double valorTotal = validaJuros(sol);
		Double vlrMaximoParcela = sol.getRenda() * 0.3; // 30 % da renda do solicitante
		Double d = sol.getRenda() / vlrMaximoParcela;
		Integer qtdeMinimaDeParcela = d.intValue();
		if (vlrMaximoParcela > valorTotal) {
			return 1;

		}
		if (!(sol.getRenda() % vlrMaximoParcela == 0)) {
			qtdeMinimaDeParcela = (int) Math.ceil(d);
		}
		return qtdeMinimaDeParcela;

	}

	// valida a quantidade máxima de parcelas
	public Integer validaQtdeMaxParcelas(Solicitante sol) throws UsuarioException {
		Double valor = validaJuros(sol);
		Double qtdeMax = valor / 300;
		Integer qtdeParcMax = qtdeMax.intValue();

		return qtdeParcMax;
	}

	// valida o valor mínimo em cada parcela
	public Double validaMinValorParc(Solicitante sol) throws UsuarioException {
		Double valor = validaJuros(sol);
		Double minParcelas = Double.valueOf(Math.round(valor / validaQtdeMaxParcelas(sol)));
		return minParcelas;
	}

	// valida o valor máximo em cada parcela
	public Double validaMaxValorParcela(Solicitante sol) throws UsuarioException {
		Double valor = validaJuros(sol);
		Double maxParcelas = valor / validaQdeMinParcelas(sol);
		return maxParcelas;
	}

	// valida o valor máximo/mínimo de empréstimo que o devedor pode pegar com
	// exception
	// Chamar ao clicar no botao apos digitar o valor solicitado 
	public void validarValorEmprestimoException(Double valor, Solicitante solicitante) throws UsuarioException {

		Double vlrMaximoEmprestimo = solicitante.getRenda() * 3;
		Double vlrMinimoEmprestimo = 300.0;

		if (valor > vlrMaximoEmprestimo || valor < vlrMinimoEmprestimo) {
			throw new UsuarioException("Valor inválido");
		}
	}

	// printa valor maximo de emprestimo que pode pegar
	public Double printValorMaximoEmprestimo() {
		Double valorMaximo = getRenda() * 3;
		return valorMaximo;
	}


}