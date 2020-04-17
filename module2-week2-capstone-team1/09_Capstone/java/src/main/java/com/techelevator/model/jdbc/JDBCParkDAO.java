package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Park;
import com.techelevator.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAllParks() {
			List<Park> parks = new ArrayList <> ();
			Park thePark = null;
			String sqlSelectParks = "SELECT * FROM park";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectParks);
			while (results.next()) {
				thePark = mapRowToPark(results);
				parks.add(thePark);
			}
			return parks; 
	}
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setId(results.getInt("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

	@Override
	public List<Integer> getAllParksId() {
		List<Integer> parkId = new ArrayList <> ();
		Integer myInt = null;
		String sqlSelectEmployees = "SELECT park_id FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectEmployees);
		while (results.next()) {
			myInt = mapRowToInteger(results);
			parkId.add(myInt);
		}
		return parkId; 
	}
	
	private int mapRowToInteger(SqlRowSet results) {
		Integer myInt = results.getInt("park_id");
		return myInt;
	}

	@Override
	public Park getParkByName(String name) {
		Park thePark = null;
		String sqlSelectEmployees = "SELECT * FROM park WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectEmployees, name);
		while (results.next()) {
			thePark = mapRowToPark(results);
		}
		return thePark; 
	}


}
