package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Player, DefaultWeightedEdge> grafo;
	Map<Integer, Player> idMap;
	List<Team> squadre= new ArrayList<>();
	Team home;
	Team away;
	
	Player best;
	int match;
	
	String risultatoPartita="";
	int espulsiH;
	int espusliA;
	
	public Model() {
		this.dao= new PremierLeagueDAO();
		idMap= dao.listAllPlayers();
	}
	
	
	public String creaGrafo(int match) {
		
		this.match= match;
		
		squadre= dao.getSquadreMatch(match);
		home= squadre.get(0);
		away= squadre.get(1);
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getPlayersPerMatch(match));
		
		
		
		for(Player p: dao.getPlayersPerMatch(match)) {
			
			for(Player p1: p.getAvversari()) {
				Graphs.addEdge(this.grafo, p, p1, Math.abs(p.getEfficienza()-p1.getEfficienza()));
				//Graphs.addEdgeWithVertices(this.grafo, p, p1, Math.abs(p.getEfficienza()-p1.getEfficienza()));
				//System.out.println(this.grafo.vertexSet().size());
		}
		
	}
		
		String string="Grafo creato!\n#VERTICI: "+this.grafo.vertexSet().size()+"\n#ARCHI: "+this.grafo.edgeSet().size();
		 return string;
		
	}
	
	public String giocatoreMigliore() {
		
		String result="";
		double max=-10;
		best=null;
		
		
		for(Player p: grafo.vertexSet()) {
			double out=0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p)) {
				out+= this.grafo.getEdgeWeight(e);
			}
			double in=0;
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p)) {
				in+= this.grafo.getEdgeWeight(e);
			}
			double delta= out-in;
			
			if(delta>max) {
				max= delta;
				best= p;
			}
		}
		
		//result= "Giocatore migliore: \n"+best.getPlayerID()+" - "+best.getName()+", delta efficienza= "+max;
		
		return "Giocatore migliore: \n"+best.getPlayerID()+" - "+best.getName()+", delta efficienza= "+max;
		
	}
	
	public List<Match> getAllMatches(){
		return dao.listAllMatches();
	}
	
	public void simula(int azioni) {
		
		Simulatore sim= new Simulatore();
		sim.setSquadre(squadre);
		sim.init(azioni);
		sim.run(best);
		risultatoPartita="Il risultato finale della partita è:\n"+home.getName()+": "+home.getGoal()+" --- "+away.getName()+": "+away.getGoal()+"\n"
				+"Espulsi--> "+home.getName()+": "+(11-home.getNGiocatori())+", "+away.getName()+": "+(11-away.getNGiocatori());
	}
	
	public String finale(int azioni) {
		simula(azioni);
		return this.risultatoPartita;
	}
	
	
	
	public Player bestPlayer() {
		return best;
	}
}
	

