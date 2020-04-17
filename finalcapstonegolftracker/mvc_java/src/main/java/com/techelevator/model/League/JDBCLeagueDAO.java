package com.techelevator.model.League;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.User.UserDAO;

@Component
public class JDBCLeagueDAO implements LeagueDAO {

	@Autowired
	private UserDAO userDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCLeagueDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<League> getAllLeagues() {
		List <League> leagues = new ArrayList<>();
		
		String sqlSelectAllLeagues = "SELECT * FROM league";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllLeagues);
		while (results.next()) {
			leagues.add(mapRowToLeague(results));
		}
		return leagues;
	}
	
	@Override
	public List<League> getAllLeaguesByUserId(int id) {
		List <League> leagues = new ArrayList<>();
		
		String sqlSelectAllLeagues = "SELECT l.*, g.* FROM league l JOIN "
				+ "league_golfer g ON g.leagueid = l.leagueid WHERE g.id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllLeagues, id);
		while (results.next()) {
			leagues.add(mapRowToLeague(results));
		}
		return leagues;
	}
	
	@Override
	public List<League> getAllLeaguesByOwner(int id){
		List <League> leagues = new ArrayList<>();
		
		String sqlSelectLeagues = "SELECT * from league WHERE leagueowner = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectLeagues, id);
		while (results.next()) {
			leagues.add(mapRowToLeague(results));
		}
		
		return leagues;
	}

	private League mapRowToLeague(SqlRowSet results) {
		League theLeague = new League();
		
		theLeague.setLeagueId(results.getInt("leagueid"));
		theLeague.setOwner(results.getString("leagueowner"));
		theLeague.setName(results.getString("leaguename"));
		return theLeague;
	}

	@Override
	public League getLeagueByLeagueId(int leagueId) {
		League league = new League();

		String sqlSelectAllLeagues = "SELECT * FROM league WHERE leagueid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllLeagues, leagueId);
		if (results.next()) {
			league = mapRowToLeague(results);
		}
		return league;
	}
	
	@Override
	public int getLeagueIdByLeagueName(String name) {
		int id = 0;

		String sqlSelectAllLeagues = "SELECT leagueid FROM league WHERE leaguename = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllLeagues, name);
		if (results.next()) {
			id = results.getInt("leagueid");
		}
		return id;
	}

	@Override
	public void saveLeague(League league) {
		String sqlAddLeague = "INSERT INTO league (leaguename, leagueowner) VALUES (?, ?)";
		int leagueid = userDao.getIdByUserName(league.getOwner());
		jdbcTemplate.update(sqlAddLeague, league.getName(), leagueid);	
		
	}
	
	@Override
	public void addUserToLeague(String user, String leagueName) {
		String sqlAddLeague = "INSERT INTO league_golfer (id, leagueid) VALUES (?, ?)";
		int id = userDao.getIdByUserName(user);
		int leagueId = getLeagueIdByLeagueName(leagueName);
		jdbcTemplate.update(sqlAddLeague, id, leagueId);	
	}

}
