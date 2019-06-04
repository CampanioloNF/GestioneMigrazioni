package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{

	private int t;
	private int n; //numero di persone che sono arrivate e che si sposteranno
	private Country paese; //paese in cui le persone arrivano e da cui si sposteranno
	
	public Evento(int t, int n, Country paese) {
		
		this.t = t;
		this.n = n;
		this.paese = paese;
	}

	public int getT() {
		return t;
	}

	public int getN() {
		return n;
	}

	public Country getPaese() {
		return paese;
	}

	@Override
	public int compareTo(Evento e) {
		// TODO Auto-generated method stub
		return this.t-e.t;
	}
	
	
	
	
	
}
