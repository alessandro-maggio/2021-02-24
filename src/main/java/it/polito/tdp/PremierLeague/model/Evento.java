package it.polito.tdp.PremierLeague.model;

public class Evento implements Comparable<Evento>{
	
	public enum EventType{
		
		GOAL,
		ESPULSIONE,
		INFORTUNIO
		
	}
	
	
	private Team squdraH;
	private Team squadraA;
//	private int giocatoriH;
//	private int giocatoriA;
	
	Double probabilita;
	
	private EventType tipo;

	public Evento(Team squadraH, Team squadraA) {
		super();
//		this.giocatoriH = giocatoriS1;
//		this.giocatoriA = giocatoriS2;
		this.squdraH= squadraH;
		this.squadraA= squadraA;
		
	}

//	public int getGiocatoriH() {
//		return giocatoriH;
//	}
//
//	public void setGiocatoriH(int giocatoriS1) {
//		this.giocatoriH = giocatoriS1;
//	}
//
//	public int getGiocatoriA() {
//		return giocatoriA;
//	}
//
//	public void setGiocatoriA(int giocatoriS2) {
//		this.giocatoriA = giocatoriS2;
//	}
	

	public EventType getTipo() {
		return tipo;
	}

	public Team getSqudraH() {
		return squdraH;
	}

	public Team getSquadraA() {
		return squadraA;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	
	

	public Double getProbabilita() {
		probabilita= Math.random();
		return probabilita;
	}

	@Override
	public int compareTo(Evento o) {
		
		return this.probabilita.compareTo(o.probabilita);
		
	}	
	
}
