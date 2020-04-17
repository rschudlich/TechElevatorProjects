package com.techelevator.model.Team;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.User.User;
import com.techelevator.model.User.UserDAO;

@Component
public class JDBCTeamDAO implements TeamDAO {
	
	@Autowired
	private UserDAO userDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCTeamDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private Team mapRowToTeam(SqlRowSet results) {
		Team team = new Team();
		team.setTeamId(results.getInt("teamid"));
		team.setName(results.getString("teamname"));
		team.setLeagueId(results.getInt("leagueid"));
		team.setPoints(results.getInt("points"));
		return team;
	}
	
	@Override 
	public List<String> getUserIdsByTeamId(int teamId){
		List<String> userIds = new ArrayList <> ();
		String sql = "SELECT user_name FROM teams t JOIN golfer_team g ON t.teamid = g.teamid"
				+ " JOIN app_user a ON a.id = g.id WHERE t.teamid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, teamId);
		while (results.next()) {
			userIds.add(results.getString("user_name"));
		}
		return userIds;
	}
	
	@Override
	public List<Team> getAllTeams() {
		List <Team> teams = new ArrayList<>();
		String sqlSelectAllTeams = "SELECT * FROM teams";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllTeams);
		while (results.next()) {
			teams.add(mapRowToTeam(results));
		}
		return teams;
	}

	@Override
	public Team getTeamByTeamId(int teamId) {
		Team team = new Team();
		String sqlSelectAllTeams = "SELECT * FROM teams WHERE teamid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllTeams, teamId);
		while (results.next()) {
			team = (mapRowToTeam(results));
		}		
		return team;
	}

	@Override
	public List<Team> getTeamsByLeagueId(int leagueId) {
		List <Team> teams = new ArrayList<>();
		String sqlSelectAllTeams = "SELECT * FROM teams WHERE leagueid = ? ORDER BY points DESC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllTeams, leagueId);
		while (results.next()) {
			teams.add(mapRowToTeam(results));
		}
		return teams;
	}
	
	@Override
	public int getTeamIdByUserId(int userId) {
		int teamId = 0;
		String sqlSelectAllTeams = "SELECT teamId FROM golfer_team WHERE Id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllTeams, userId);
		if (results.next()) {
			teamId = results.getInt("teamId");
		}
		return teamId;
	}
	
	@Override
	public List<Team> getTeamsByLeagueIdAndUserId(int leagueId, int userId) {
		List <Team> teams = new ArrayList<>();
		String sqlSelectAllTeams = "SELECT * FROM teams JOIN league_golfer ON league_golfer.leagueid = "
				+ "teams.leagueid WHERE leagueid = ? AND league_golfer.id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllTeams, leagueId, userId);
		while (results.next()) {
			teams.add(mapRowToTeam(results));
		}
		return teams;
	}

	@Override
	public List<Team> getTeamsByUserId(int id) {
		List <Team> teams = new ArrayList<>();
		String sqlSelectAllTeams = "SELECT * FROM teams JOIN golfer_team ON teams.teamid = golfer_team.teamid WHERE golfer_team.id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllTeams, id);
		while (results.next()) {
			teams.add(mapRowToTeam(results));
		}
		return teams;
	}

	@Override
	public List<Team> getRankingByLeagueId(int leagueId) {
		List <Team> rankedTeams = new ArrayList<>();
		String sqlGetRanking = "SELECT * FROM teams JOIN golfer_team ON teams.teamid = golfer_team.teamid WHERE points >= 0 AND leagueid = ? ORDER BY points DESC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetRanking, leagueId);
		while (results.next()) {
			rankedTeams.add(mapRowToTeam(results));
		}
		return rankedTeams;
	}
	
	@Override
	public int getRankingByUserIdAndLeagueId(int leagueId, int userId) {
		int ranking = 0;
		List <Team> rankedTeams = new ArrayList<>();
		String sqlGetRanking = "SELECT * FROM teams JOIN golfer_team ON teams.teamid = golfer_team.teamid WHERE points >= 0 AND leagueid = ? ORDER BY points DESC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetRanking, leagueId);
		while (results.next()) {
			rankedTeams.add(mapRowToTeam(results));
		}
		for (int x = 1; x < rankedTeams.size() + 1; x++) {
			if (rankedTeams.get(x).getTeamId() == getTeamIdByUserId(userId)) {
				ranking = x;
			}
		}
		return ranking;
	}
	
	@Override
	public int getRanking(int leagueId, int userId) {
		int points = 0;
		int ranking = 0;
		String sqlGetPointsByUserId = "SELECT points FROM teams JOIN golfer_team ON teams.teamid = golfer_team.teamid "
				+ "WHERE golfer_team.id = ? AND teams.leagueid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetPointsByUserId, userId, leagueId);
		if (results.next()) {
			points = results.getInt("points");
		}
		String sqlGetPoints = "SELECT COUNT(*) as total FROM teams WHERE points > ? AND leagueId = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetPoints, points, leagueId);
		if (result.next()) {
			ranking = result.getInt("total");
		}
		ranking = ranking + 1;
		return ranking;
	}


	
	

}
