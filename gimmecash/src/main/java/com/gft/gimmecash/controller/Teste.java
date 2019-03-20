package com.gft.gimmecash.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.gft.gimmecash.exceptions.UsuarioException;
import com.gft.gimmecash.model.Endereco;
import com.gft.gimmecash.model.Solicitante;

public class Teste {
	
//	@PostMapping("/input-next-step")
//	public String updateSolicitador(Model model, Solicitante sol) {
//		Endereco end = null;
//		end = new Endereco(end.getRua(), end.getCidade(), end.getEstado(), end.getPais());
//		
//		List<Solicitante> listUsuario = new ArrayList<>();
//		sol.setValorSolicitado(sol.getValorComJuros());
//		sol = new Solicitante(null, sol.getNome(), sol.getCpf(), sol.getRg(), sol.getSexo(), sol.getEmail(), sol.getTelefone(), sol.getDtNascimento(), sol.getEmpresa(), end,
//				sol.getRenda(), sol.getRating(), sol.getDataSolicitacao());
//		
//		listUsuario.add(sol);
//		repository.save(listUsuario);
//		
//
//		return "redirect:/";
//	}

	public static void main(String[] args) throws Exception {
		/*
		 * Logicas: Tempo de Espera: Maior Tempo && Maior Rating && Menor NdeP &&
		 * ValorCompativel Parcelas: Parcela Minima >= 300 Parcela Maxima <= 30% renda
		 * Mensal Juros Juros Simples (R1 = 5, R2 = 4 ... )  Valor Maximo Pedido <= *
		 * rendaMensal * 3 Total a Pagar Valor + Juros || Parcelas + Juros x NumeroP Ler
		 * o arquivo: Inserir no BD
		 */
		Endereco end = new Endereco("Dennett Place 226", "Herbster", "Montana", "Tajikistan");
		Date data = new Date(19, 02, 2018);
		Date dataSolicitacao = new Date (21, 05, 2018);
		Solicitante sol = new Solicitante(null, "Juana Mckinney", "199.748.161-81", "82.216.311-5", "female",
				"juanamckinney@isoswitch.com", "+55 (20) 99042-4381", data, "ECLIPSENT", end, 3356.63, 1, dataSolicitacao, 1);
		sol.setValorSolicitado(300.0);
		// System.out.println(imprimirResumo());
		System.out.println(sol.getValorSolicitado() + ": valor solicitado");
		System.out.println(sol.getValorComJuros() + ": getValorComJuros");
		System.out.println(validaJuros(sol) + ": método validaJuros");
		System.out.println(validaQdeMinParcelas(sol) + ": método valida quantidade mínima de parcelas");
		System.out.println(validaQtdeMaxParcelas(sol) + ": valida quantidade máxima de parcelas");
		System.out.println(validaMinValorParc(sol) + ": valida valor mínimo de parcelas");
		System.out.println(validaMaxValorParcela(sol) + ": valida valor máximo de parcelas");
		System.out.println(printValorMaximoEmprestimo(sol) + ": valida o valor máximo do empréstimo");
	}

	// valida o juros em cada solicitante
	public static Double validaJuros(Solicitante sol) throws UsuarioException {

		Double taxa = sol.getValorSolicitado() * Rating.get(sol.getRating()).getTaxaJuros();

		Double valorComJuros = sol.getValorSolicitado() + taxa;
		sol.setValorComJuros(valorComJuros);
		return valorComJuros;
		
	}

	// valida a quantidade mínima de parcelas
	public static Integer validaQdeMinParcelas(Solicitante sol) throws UsuarioException {
		Double valor = validaJuros(sol);
		Double vlrMaximoParcela = sol.getRenda() * 0.3; // 30 % da renda do solicitante
		Double d = sol.getRenda() / vlrMaximoParcela;
		Integer qtdeMinimaDeParcela = d.intValue();
		if (vlrMaximoParcela > valor) {
			return 1;

		}
		if (!(sol.getRenda() % vlrMaximoParcela == 0)) {
			qtdeMinimaDeParcela = (int) Math.ceil(d);
		}
		return qtdeMinimaDeParcela;

	}

	// valida a quantidade máxima de parcelas
	public static Integer validaQtdeMaxParcelas(Solicitante sol) throws UsuarioException {
		Double valor = validaJuros(sol);
		Double qtdeMax = valor / 300;
		Integer qtdeParcMax = qtdeMax.intValue();
		return qtdeParcMax;
	}

	// valida o valor mínimo em cada parcela
	public static Double validaMinValorParc(Solicitante sol) throws UsuarioException {
		Double valor = validaJuros(sol);
		Double minParcelas = valor / validaQtdeMaxParcelas(sol);
		return minParcelas;
	}

	// valida o valor máximo em cada parcela
	public static Double validaMaxValorParcela(Solicitante sol) throws UsuarioException {
		Double valor = validaJuros(sol);
		Double maxParcelas = valor / validaQdeMinParcelas(sol);
		return maxParcelas;
	}

	// valida o valor máximo/mínimo de empréstimo que o devedor pode pegar com
	// exception
	// Chamar ao clicar no botao apos digitar o valor solicitado
	public static void validarValorEmprestimoException(Double valor, Solicitante solicitante)
			throws UsuarioException {

		Double vlrMaximoEmprestimo = solicitante.getRenda() * 3;
		Double vlrMinimoEmprestimo = 300.0;

		if (valor > vlrMaximoEmprestimo || valor < vlrMinimoEmprestimo) {
			throw new UsuarioException("Valor inválido");
		}
	}

	// printa valor maximo de emprestimo que pode pegar
	public static Double printValorMaximoEmprestimo(Solicitante sol) {
		return sol.getRenda() * 3;
	}

//	// Parcelas
//	public static void validarValorParcela(Solicitante solicitante) throws ValorMaximoException {
//		Double vlrMaximoParcela = solicitante.getRenda() * 0.3;
//		Double vlrMinimoParcela = 300.0;
//
//		if ((solicitante.getValorSolicitado() / solicitante.getNumParcelas()) < vlrMinimoParcela) {
//			throw new ValorMaximoException("Valor inválido");
//
//		}
//	}

}
