package com.techelevator.model.League;

import java.util.List;

public interface LeagueDAO {
	public List<League> getAllLeagues();
	
	public League getLeagueByLeagueId(int leagueId);
	
	public void saveLeague(League league);

	List<League> getAllLeaguesByUserId(int id);

	int getLeagueIdByLeagueName(String name);

	void addUserToLeague(String user, String leagueName);
	
	List<League> getAllLeaguesByOwner(int id);
	
}
