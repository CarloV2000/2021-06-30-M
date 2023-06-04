package it.polito.tdp.genes.model;

public class CoppiaA {
	private Integer cromosoma1;
	private Integer cromosoma2;
	private double peso;
	
	public CoppiaA(Integer cromosoma1, Integer cromosoma2, double peso) {
		super();
		this.cromosoma1 = cromosoma1;
		this.cromosoma2 = cromosoma2;
		this.peso = peso;
	}

	public Integer getCromosoma1() {
		return cromosoma1;
	}

	public void setCromosoma1(Integer cromosoma1) {
		this.cromosoma1 = cromosoma1;
	}

	public Integer getCromosoma2() {
		return cromosoma2;
	}

	public void setCromosoma2(Integer cromosoma2) {
		this.cromosoma2 = cromosoma2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	
	
}
