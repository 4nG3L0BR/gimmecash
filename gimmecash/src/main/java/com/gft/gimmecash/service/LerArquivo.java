package com.gft.gimmecash.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.gft.gimmecash.model.Credor;
import com.gft.gimmecash.model.Endereco;
import com.gft.gimmecash.model.Solicitante;

@Component
public class LerArquivo {
	
	public List<Solicitante> listaSolicitante() throws FileNotFoundException, ParseException {			
		Scanner scan = new Scanner(new FileInputStream("solicitantes.csv"));
		List<Solicitante> listaSolicitante = new ArrayList<>();
		//List<Endereco> listaEndereco = new ArrayList<>();
		while (scan.hasNext()) {
			String nextLine = scan.nextLine();// Atribui a função scanner a variável nextLine.

			Scanner line = new Scanner(nextLine);
			line.useDelimiter(",");
			line.useLocale(Locale.US);

			String nome = line.next().trim();
			String cpf = line.next().trim();
			String rg = line.next().trim();
			String sexo = line.next().trim();
			String email = line.next().trim();
			String telefone = line.next().trim();
			String dtNascimento = line.next().trim();
			String empresa = line.next().trim();
			Double renda = line.nextDouble();
			String rua = line.next().trim();
			String cidade = line.next().trim();
			String estado = line.next().trim();
			String pais = line.next().trim();
			Integer rating = line.nextInt();
			String data = line.next();
			
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dt;
			dt = formato.parse(data);
			java.sql.Date dataSolicitacao = new java.sql.Date(dt.getTime());
		
			Endereco end = new Endereco(rua, cidade, estado, pais);
			Date dtNasc = Date.valueOf(dtNascimento);
			Solicitante solicitante = new Solicitante(null, nome, cpf, rg, sexo, email, telefone, dtNasc, empresa, end,
					renda, rating, dataSolicitacao, 1);
			
			listaSolicitante.add(solicitante);
			
		} 
		//System.out.println(listaSolicitante);
		return listaSolicitante;
		

	}
	
	
	public List<Credor> listaCredor() throws FileNotFoundException {
		Scanner scan = new Scanner(new FileInputStream("credores.csv"));
		List<Credor> listaCredor = new ArrayList<>();

		while (scan.hasNext()) {
			String nextLine = scan.nextLine();// Atribui a função scanner a variável nextLine.

			Scanner line = new Scanner(nextLine);
			line.useDelimiter(",");
			line.useLocale(Locale.US);

			String nome = line.next().trim();
			String cpf = line.next().trim();
			String rg = line.next().trim();
			String sexo = line.next().trim();
			String email = line.next().trim();
			String telefone = line.next().trim();
			String dtNascimento = line.next().trim();
			String empresa = line.next().trim();
			String rua = line.next().trim();
			String cidade = line.next().trim();
			String estado = line.next().trim();
			String pais = line.next().trim();
			Endereco end = new Endereco(rua, cidade, estado, pais);
			Date dtNasc = Date.valueOf(dtNascimento);
			Credor credor = new Credor(null, nome, cpf, rg, sexo, email, telefone, dtNasc, empresa, end);
			listaCredor.add(credor);

		} 
		
		
		
		return listaCredor;

	}

	
	
	}
