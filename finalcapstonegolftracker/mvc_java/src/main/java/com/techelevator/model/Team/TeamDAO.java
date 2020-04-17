package com.techelevator.model.Team;

import java.util.List;

public interface TeamDAO {
	
	public List<Team> getAllTeams();
	
	public Team getTeamByTeamId(int teamId);
	
	public List<Team> getTeamsByLeagueId(int leagueId);
	
	public List<Team> getTeamsByUserId(int id);

	List<Team> getTeamsByLeagueIdAndUserId(int leagueId, int userId);
	
	public List<Team> getRankingByLeagueId(int leagueId);
	
	public int getRankingByUserIdAndLeagueId(int leagueId, int userId);

	int getTeamIdByUserId(int userId);

	int getRanking(int leagueId, int userId);

	List<String> getUserIdsByTeamId(int teamId);

}
