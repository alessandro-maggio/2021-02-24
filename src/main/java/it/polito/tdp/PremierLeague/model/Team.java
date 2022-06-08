package it.polito.tdp.PremierLeague.model;

import java.util.Set;

public class Team {
	Integer teamID;
	String name;
	int goal;
	int giocatori;
	

	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getGoal() {
		return this.goal;
	}
	
	public void setGoal(int numero) {
		this.goal= numero;
	}
	
	
	public int getNGiocatori() {
		return this.giocatori;
	}
	
	public void setNGiocatori(int numero) {
		this.giocatori=numero;
	}
	
	

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
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
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}
	
}
