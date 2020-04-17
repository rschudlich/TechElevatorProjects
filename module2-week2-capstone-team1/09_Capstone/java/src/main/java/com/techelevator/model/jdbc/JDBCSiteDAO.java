package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Site;
import com.techelevator.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	@Override
	public List<Site> getAllSitesByCampgroundId(int campgroundId) {
		List<Site> sites = new ArrayList <> ();
		Site theSite = null;
		String sqlSelectSites = "SELECT * FROM site WHERE campground_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectSites, campgroundId);
		while (results.next()) {
			theSite = mapRowToSite(results);
			sites.add(theSite);
		}
		return sites; 
	}
	
	private Site mapRowToSite(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setId(results.getInt("site_id"));
		theSite.setCampgroundId(results.getInt("campground_id"));
		theSite.setSiteNumber(results.getInt("site_number"));
		theSite.setMaxOccupancy(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRVLength(results.getInt("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}
	
}
