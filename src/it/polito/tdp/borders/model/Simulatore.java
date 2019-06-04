package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;


public class Simulatore {
	
	


	//Stato del sistema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	//Quali sono i tipi di evento/coda prioritaria
	// 1 tipo di evento
	private PriorityQueue<Evento> queue;
	
	
	//Quali sono i parametri della simulazione 
	
	private static final int  NUM_MIGRANTI = 1000;
	private Country partenza;
	
	
	//Quali sono i valori in output
	
	private int T;
	private Map<Country, Integer> stanziali;
	
	
	public void init(Country partenza,  Graph<Country, DefaultEdge> grafo) {
		
		//ricevo i parametri
		this.partenza = partenza;
		this.grafo = grafo;
		
		//impostazione dello stato iniziale
		T = 1;
		stanziali = new HashMap<>();
		for(Country c : this.grafo.vertexSet()) 
		    stanziali.put(c,0);	
		
		queue = new PriorityQueue<>();
		
		//inserisco il primo evento
		queue.add(new Evento(T, NUM_MIGRANTI, partenza));
		
	}
	
	public void run() {
		
		//Estraggo un evento per volta dalla coda e lo eseguo finchè la coda non si svuota
		
		Evento e ;
		
		while(!queue.isEmpty()) {
			
			e = queue.poll();
			
			this.T =e.getT(); 
			int num_Pers = e.getN();
			Country stato = e.getPaese();
			
			List<Country> confinanti = Graphs.neighborListOf(grafo, stato);
			int migranti = (num_Pers/2) / confinanti.size();
			if(migranti>0) {
				//posso spostatre le persone
				for(Country confinante: confinanti) {
					queue.add(new Evento(e.getT()+1, migranti, confinante));
					
				}
			}
			
			int stanziali = num_Pers-migranti*confinanti.size();
			this.stanziali.put(stato, this.stanziali.get(stato) + stanziali);
			
		}
		
		
		
	}

	public int getT() {
	    return this.T;
		
	}

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	public PriorityQueue<Evento> getQueue() {
		return queue;
	}

	public static int getNumMigranti() {
		return NUM_MIGRANTI;
	}

	public Country getPartenza() {
		return partenza;
	}

	public Map<Country, Integer> getStanziali() {
		return stanziali;
	}
	
	
}
