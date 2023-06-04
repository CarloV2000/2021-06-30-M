package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private Graph<Integer, DefaultWeightedEdge>grafo;
	private List<Integer>allCromosomi;
	private GenesDao dao;
	
	private List<Integer>camminoMigliore;
	private double sommaMigliore;
	
	
	public Model() {
		this.allCromosomi = new ArrayList<>();
		this.dao = new GenesDao();
		this.camminoMigliore = new ArrayList<>();
	}
	
	
	
	public Graph<Integer, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}



	public void setGrafo(Graph<Integer, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}



	public List<Integer> getAllCromosomi() {
		return allCromosomi;
	}



	public void setAllCromosomi(List<Integer> allCromosomi) {
		this.allCromosomi = allCromosomi;
	}



	public List<Integer> getCamminoMigliore() {
		return camminoMigliore;
	}



	public void setCamminoMigliore(List<Integer> camminoMigliore) {
		this.camminoMigliore = camminoMigliore;
	}



	public double getSommaMigliore() {
		return sommaMigliore;
	}



	public void setSommaMigliore(double sommaMigliore) {
		this.sommaMigliore = sommaMigliore;
	}



	public String creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		this.allCromosomi = new ArrayList<>(dao.getAllCromosomi());
		Graphs.addAllVertices(grafo, this.allCromosomi);
		
		//archi Un arco collega due cromosomi diversi solo se i due cromosomi contengono due geni (uno per cromosoma) che compaiono 
		//(nello stesso ordine) nella tabella interactions. Si noti che, per ciascun cromosoma, possono esistere più geni,
		//e ciascuno di essi potrebbe essere presente più volte (associato a function diverse).
		List<CoppiaA>archi = new ArrayList<>(dao.getAllArchi());
		for(CoppiaA x : archi) {
			this.grafo.addEdge(x.getCromosoma1(), x.getCromosoma2());
			grafo.setEdgeWeight(x.getCromosoma1(), x.getCromosoma2(), x.getPeso());
		}
		return "Grafo creato con"+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi ";
	}
	
	public double pesoMinimo() {
		double minimo = 10000000;
		List<CoppiaA>archi = new ArrayList<>(dao.getAllArchi());
		for(CoppiaA x : archi) {
			if(x.getPeso()<minimo) {
				minimo = x.getPeso();
			}
		}
		return minimo;
	}
	
	public double pesoMassimo() {
		double massimo = 0;
		List<CoppiaA>archi = new ArrayList<>(dao.getAllArchi());
		for(CoppiaA x : archi) {
			if(x.getPeso()>massimo) {
				massimo = x.getPeso();
			}
		}
		return massimo;
	}
	
	public List<CoppiaA>getArchiPesoMinore(int soglia){
		 List<CoppiaA>archiFiltrati = new ArrayList<>();
		 List<CoppiaA>archi = new ArrayList<>(dao.getAllArchi());
		 for(CoppiaA x : archi) {
			 if(x.getPeso()<soglia) {
				 archiFiltrati.add(x);
			 }
		 }
		 return archiFiltrati;
	}
	public List<CoppiaA>getArchiPesoMaggiore(int soglia){
		 List<CoppiaA>archiFiltrati = new ArrayList<>();
		 List<CoppiaA>archi = new ArrayList<>(dao.getAllArchi());
		 for(CoppiaA x : archi) {
			 if(x.getPeso()>soglia) {
				 archiFiltrati.add(x);
			 }
		 }
		 return archiFiltrati;
	}
	public void getCamminoMax(int soglia){
		 List<CoppiaA>archiFiltrati = new ArrayList<>();
		 List<CoppiaA>archi = new ArrayList<>(dao.getAllArchi());
		 List<Integer>parziale = new ArrayList<>();

		 for(CoppiaA x : archi) {
			 if(x.getPeso()>soglia) {
				 archiFiltrati.add(x);
			 }
		 }
		 ricorsione(0, parziale);
	}
	
	public void ricorsione(int livello, List<Integer>parziale){
        double sommaPesi = calcoloSommaPesiArchi(parziale);
		
			if(sommaPesi > sommaMigliore) {
				sommaMigliore = sommaPesi;
				camminoMigliore = new ArrayList<>(parziale);
			}
		
		if(livello == this.allCromosomi.size())//se ho gia aggiunto tutti i cromosomi della lista esco
			return;
		
		//se arrivo qui: ho soluzoni!!!
		for (Integer i : this.allCromosomi) {//ciclo sui dati e chiamo metodo che fa ricorsione
			if(!parziale.contains(i)) {//se l soluz parziale non contiene l'esame e lo aggingo
				parziale.add(i);
				ricorsione(livello+1, parziale);//chiamo metodo ricorsione
				parziale.remove(i);//ricordare di rimuovere l'oggetto(serve per far si che alla prossima iterazione parziale sarà vuoto
			}
		}
		
	}
	
	public double calcoloSommaPesiArchi(List<Integer>parziale){
		double somma = 0.0;
		for(Integer x : parziale) {
			for(Integer y : parziale) {
				if(!x.equals(y)) {
					DefaultWeightedEdge e = grafo.getEdge(x, y);
					if(e != null) {
						double peso = grafo.getEdgeWeight(e);
						somma += peso;
					}
				}
			}
		}
		return somma;
	}

}