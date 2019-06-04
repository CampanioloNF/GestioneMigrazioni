package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> graph ;
	private List<Country> countries ;
	private Map<Integer,Country> countriesMap ;
	Simulatore sim ;
	public Model() {
		
		this.countriesMap = new HashMap<>() ;
        this.sim = new Simulatore();
		
	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;

		BordersDAO dao = new BordersDAO() ;
		
		//vertici
		dao.getCountriesFromYear(anno,this.countriesMap) ;
		Graphs.addAllVertices(graph, this.countriesMap.values()) ;
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno) ;
		for( Adiacenza c: archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), 
					this.countriesMap.get(c.getState2no())) ;
			
		}
	}
	
	public List<CountryAndNumber> getCountryAndNumber() {
		List<CountryAndNumber> list = new ArrayList<>() ;
		
		for(Country c: graph.vertexSet()) {
			list.add(new CountryAndNumber(c, graph.degreeOf(c))) ;
		}
		Collections.sort(list);
		return list ;
	}

	public List<Country> getCountries() {

		List<Country> result =  new LinkedList<>(this.countriesMap.values());
		Collections.sort(result);
		return result;

		
	}

	public void simula(Country partenza) {
		
		
		sim.init(partenza, this.graph);
		sim.run();
		
	}

	public int getLastT() {
		return sim.getT();
	}

	public List<CountryAndNumber> getListaPaesiStanziali(){
		
		List<CountryAndNumber> result =  new LinkedList<>();
		Map<Country, Integer> stanziali = sim.getStanziali();
		for(Country paese : stanziali.keySet())
			result.add(new CountryAndNumber(paese, stanziali.get(paese)));
		
		Collections.sort(result);
		return result;
		
	}
}
