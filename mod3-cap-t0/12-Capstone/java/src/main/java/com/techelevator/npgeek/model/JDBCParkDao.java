package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCParkDao implements ParkDao {

private JdbcTemplate jdbcTemplate;

@Autowired
public JDBCParkDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
}



/*Get all parks and put it into the list*/


@Override
public List<Park> getAllParks() {
    
	List<Park> allParks = new ArrayList<>();
    String sqlFindAllParks = "SELECT * FROM park";
    SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindAllParks);

    while (result.next()) {
        allParks.add(mapRowToPark(result));
    }
    return allParks;
}
    
@Override
public Park getParkByParkCode(String parkCode) {
    
    Park parkByParkCode = null;
    String sqlFindParkByParkCode = "SELECT * FROM park WHERE parkcode = UPPER (?)";
    SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindParkByParkCode, parkCode);
    
    if(results.next()) {
    parkByParkCode = mapRowToPark(results);
    
    }
        
    return parkByParkCode;
                    
} 

private Park mapRowToPark(SqlRowSet results) {
	Park park = new Park();
	park.setParkCode(results.getString("parkCode").toLowerCase());
	park.setParkName(results.getString("parkName"));
	park.setState(results.getString("state"));
	park.setAcreage(results.getInt("acreage"));
	park.setElevationInFeet(results.getInt("elevationInFeet"));
	park.setMilesOfTrail(results.getDouble("milesOfTrail"));
	park.setNumberOfCampsites(results.getInt("numberOfCampsites"));
	park.setClimate(results.getString("climate"));  
	park.setYearFounded(results.getInt("yearFounded"));
	park.setAnnualVisitorCount(results.getInt("annualVisitorCount"));
	park.setInspirationalQuote(results.getString("inspirationalQuote"));
	park.setInspirationalQuoteSource(results.getString("inspirationalQuoteSource"));
	park.setParkDescription(results.getString("parkDescription"));
	park.setEntryFee(results.getInt("entryFee"));
	park.setNumberOfAnimalSpecies(results.getInt("numberOfAnimalSpecies")); 
	return park;
}
}