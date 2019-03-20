package com.gft.gimmecash.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gft.gimmecash.exceptions.UsuarioException;
import com.gft.gimmecash.model.Credor;
import com.gft.gimmecash.model.Solicitante;
import com.gft.gimmecash.model.Usuario;
import com.gft.gimmecash.repository.CredorRepository;
import com.gft.gimmecash.repository.EnderecoRepository;
import com.gft.gimmecash.repository.OperacaoRepository;
import com.gft.gimmecash.repository.SolicitanteRepository;
import com.gft.gimmecash.service.LerArquivo;

@Controller
public class UsuarioController {
	@Autowired
	SolicitanteRepository repositorySolicitante;

	@Autowired
	CredorRepository repositoryCredor;

	@Autowired
	EnderecoRepository repositoryEndereco;

	@Autowired
	OperacaoRepository repositoryOperacao;

	@Autowired
	LerArquivo lerArquivo;


	@GetMapping
	public String index(Model model) {
		// model.addAttribute("listaSolicitante", repository.findAll());
		return "redirect:/";
	}

	// MÉTODO ADICIONAR CREDOR NA LISTA
	@PostMapping("/input-file")
	public String listaSolicitantes() throws FileNotFoundException, ParseException, java.text.ParseException {
		List<Solicitante> listaSolicitante = lerArquivo.listaSolicitante();
		List<Credor> listaCredor = lerArquivo.listaCredor();

		List<Usuario> lista = new ArrayList<>();
		lista.addAll(listaCredor);
		lista.addAll(listaSolicitante);

		lista.forEach(pessoa -> repositoryEndereco.save(pessoa.getEndereco()));

		repositorySolicitante.saveAll(listaSolicitante);
		repositoryCredor.saveAll(listaCredor);
		return "redirect:/";
	}

	@GetMapping("/")
	public String exibirLista(@RequestParam(value="edit", required=false) Long edit, Model model) {
		mostrarValorSolSoma(model);
		mostrarSaldoAtual(model);
		model.addAttribute("edit", edit);
		model.addAttribute("listaSolicitante", repositorySolicitante.findAll());
		model.addAttribute("listaCredor", repositoryCredor.findAll());
		
		if(edit != null) {
			model.addAttribute("solicitante", repositorySolicitante.findById(edit));
			
		}

		return "index";
	}
	
	@GetMapping("teste/{path}/mais_um_teste")
	public String teste(@RequestParam(value="id", required=false) Long id, @PathVariable("path") String path, Model model) {
		System.out.println(path + ":::::: " + id);
		
		model.addAttribute("id", id);
		model.addAttribute("path", path);
		
		return "teste";
	}
	
	@PostMapping("teste_post")
	public String testePost(@RequestParam(value="id", required=false) Long id, Model model) {
		System.out.println("TESTE");
		model.addAttribute("id", 123123);
		
		return "redirect:teste/jose/mais_um_teste";
	}
	
	
	

	@RequestMapping(value = "/input-next-step", method = RequestMethod.POST)
	public String valorNoBDSol(@ModelAttribute(name = "valorSolicitadoInput" ) String valorSolicitadoInput, @ModelAttribute(name = "solId") String id){
		try {
			Double d = Double.valueOf(valorSolicitadoInput);
			Long idNovo = Long.parseLong(id);
			Solicitante t = repositorySolicitante.findById(idNovo).get();
			t.setValorSolicitado(d);
			t.validaJuros(t);
			t.validaQdeMinParcelas(t);
			t.validaQtdeMaxParcelas(t);
			
			
			
			repositorySolicitante.save(t);
		} catch (UsuarioException e) {
			String error = e.getMessage();
		}
		
		
		
		
		return "redirect:/";
	}

	@RequestMapping(value = "/input-next-step-credor", method = RequestMethod.POST)
	public String valorNoBDCredor(
			@ModelAttribute(name = "valorSolicitadoInputCredor") String valorSolicitadoInputCredor,
			@ModelAttribute(name = "credorId") String id) {
		Double d = Double.valueOf(valorSolicitadoInputCredor);
		Long idNovo = Long.parseLong(id);
		Credor credor = repositoryCredor.findById(idNovo).get();
		credor.setSaldoAtual(d);
		credor.setSaldoInicial(d);
		System.out.println(idNovo);
		System.out.println(d);
		credor.setCredito(d);
		repositoryCredor.save(credor);
		return "redirect:/";
	}
	
	

	@GetMapping("/deleteCredor/{id}")
	public ModelAndView removeCredor(@PathVariable("id") Long id) {
		System.out.println(id);
		repositoryCredor.deleteById(id);

		return new ModelAndView("redirect:/");
	}

	@GetMapping("/deleteSolicitante/{id}")
	public ModelAndView removeSolicitante(@PathVariable("id") Long id) {
		System.out.println(id);
		repositorySolicitante.deleteById(id);

		return new ModelAndView("redirect:/");
	}
	public void mostrarValorSolSoma(Model model) {
		Double somaValor = repositorySolicitante.valorSolSoma();
		model.addAttribute("somaValor", somaValor);
	}

	public void mostrarSaldoAtual(Model model) {
		Double somaSaldoA = repositoryCredor.valorSaldoSoma();
		model.addAttribute("somaSaldo", somaSaldoA);
	}
}
