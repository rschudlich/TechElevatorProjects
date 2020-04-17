package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.CampgroundDAO;
import com.techelevator.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundsByParkId(int id) {
		List<Campground> campgrounds = new ArrayList <> ();
		Campground theCampground = null;
		String sqlSelectCampgrounds = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectCampgrounds, id);
		while (results.next()) {
			theCampground = mapRowToCampground(results);
			campgrounds.add(theCampground);
		}
		return campgrounds; 
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setId(results.getInt("campground_id"));
		theCampground.setParkId(results.getInt("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFromMM(results.getInt("open_from_mm"));
		theCampground.setOpenToMM(results.getInt("open_to_mm"));
		theCampground.setDailyFee((results.getDouble("daily_fee")));
		return theCampground;
	}

	@Override
	public Campground getCampgroundByCampgroundId(int campgroundId, int parkId) {
		Campground theCampground = null;
		String sqlSelectCampgrounds = "SELECT * FROM campground WHERE campground_id = ? AND park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectCampgrounds, campgroundId, parkId);
		while (results.next()) {
			theCampground = mapRowToCampground(results);
		}
		return theCampground; 
	}

}
