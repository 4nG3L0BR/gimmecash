package com.gft.gimmecash.controller;

import com.gft.gimmecash.exceptions.UsuarioException;

public enum Rating {
	UM(1, 0.05), DOIS(2, 0.04), TRES(3, 0.03), QUATRO(4, 0.02), CINCO(5, 0.01);
	
	private Double taxaJuros;
	private Integer rating;
	
	Rating(Integer rating, Double taxaJuros){
		this.rating = rating;
		this.taxaJuros = taxaJuros;
	}
	
	public Double getTaxaJuros() {
		return taxaJuros;
	}
	
	public Integer getRating() {
		return rating;
	}
	
	public static Rating get(Integer rating) throws UsuarioException {
		for (Rating rat : Rating.values()) {
			if(rat.getRating().equals(rating)) {
				return rat;
			}
		}
		
		throw new UsuarioException("Rating Inválido");
	}
	
	

}
