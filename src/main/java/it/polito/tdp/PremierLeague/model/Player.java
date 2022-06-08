package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	Integer playerID;
	String name;
	double efficienza;
	Team squadra;
	List<Player> avversari = new ArrayList<>();
	
	
	public Player(Integer playerID, String name) {
		super();
		this.playerID = playerID;
		this.name = name;
		
	}
	
	public Integer getPlayerID() {
		return playerID;
	}
	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getEfficienza() {
		return efficienza;
	}
	public void setEfficienza(double efficienza) {
		this.efficienza = efficienza;
	}
	
	public void setAvversario(Player p) {
		avversari.add(p);
	}
	
	public List<Player> getAvversari(){
		return avversari;
	}
	
	

	public Team getSquadra() {
		return squadra;
	}

	public void setSquadra(Team squadra) {
		this.squadra = squadra;
	}

	public void setAvversari(List<Player> avversari) {
		this.avversari = avversari;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playerID == null) ? 0 : playerID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playerID == null) {
			if (other.playerID != null)
				return false;
		} else if (!playerID.equals(other.playerID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return playerID + " - " + name;
	}
	
	
	
}
