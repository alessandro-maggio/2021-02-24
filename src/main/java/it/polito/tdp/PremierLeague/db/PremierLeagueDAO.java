package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	Map<Integer, Player> idMap;
	Map<Integer, Team> squadre;
	
	
	public Map<Integer, Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		idMap = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				idMap.put(player.getPlayerID(),player);
			}
			conn.close();
			return idMap;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Team> listAllTeams(){
		
		String sql = "SELECT * FROM Teams";
		squadre = new HashMap<Integer, Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				squadre.put(res.getInt("TeamID"),team);
			}
			conn.close();
			return squadre;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID "
				+ "ORDER BY m.MatchID ASC";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getPlayersPerMatch(int match){
		
		String sql = "select PlayerId as id, a.`TeamID` as squadra, (a.`TotalSuccessfulPassesAll`+a.`Assists`)/a.`TimePlayed` as eff "
				+ "from actions a "
				+ "where a.MatchID = ?";
		
		List<Player> players = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, match);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				

				//Player p= new Player(res.getInt("id"), idMap.get(res.getInt("id")).getName());
				Player p= idMap.get(res.getInt("id"));
				p.setSquadra(squadre.get(res.getInt("squadra")));
				p.setEfficienza(res.getDouble("eff"));
				players.add(p);

			}
			

			for(Player p: idMap.values()) {
				p.getAvversari().clear();
			}
			setAvversariPerGiocatore(match);
			
			conn.close();
			return players;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setAvversariPerGiocatore(int match) {
		
		
		String sql = "select a1.`PlayerID` as id1, a2.`PlayerID` as id2 "
				+ "from actions a1, actions a2 "
				+ "where a1.`MatchID`= ? "
				+ "and a1.`MatchID`= a2.`MatchID` "
				+ " and a1.`TeamID`!=a2.`TeamID`";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, match);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Player p1= this.idMap.get(res.getInt("id1"));
				Player p2= this.idMap.get(res.getInt("id2"));
				
				if(p1.getEfficienza()>p2.getEfficienza() && !p2.getAvversari().contains(p1)) {
					p1.setAvversario(p2);
				}
				else{
					if(!p1.getAvversari().contains(p2))
						p2.setAvversario(p1);
				}

			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public List<Team> getSquadreMatch(int match){
		
		List<Team> result= new ArrayList<>();
		listAllTeams();
		
		String sql = "select * "
				+ "from matches m "
				+ "where m.`MatchID`=?";
		
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, match);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				
				result.add(squadre.get(res.getInt("TeamHomeID")));
				result.add(squadre.get(res.getInt("TeamAwayID")));
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		
		return result;
		
		
	}
	
}
