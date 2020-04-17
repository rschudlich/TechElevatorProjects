package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;

import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.npgeek.model.JDBCParkDao;
import com.techelevator.npgeek.model.Park;

public class JDBCParkDaoTest { 

	private static SingleConnectionDataSource dataSource;
	private JDBCParkDao parkDao;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/npgeek");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		String sqlInsertPark = "INSERT INTO park (parkcode, parkname, state, acreage, elevationinfeet, milesoftrail,"
				+ " numberofcampsites, climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource,"
				+ " parkdescription, entryfee, numberofanimalspecies )"

				+ " VALUES ('TMC', 'Test','Michigan', 100, 1000, 200, "
				+ " 10, 'Cold', 1000, 1000000, 'Test oh Test', 'Ryan', " + " 'Destcription Test Park', 0, 100) ";

		String sqlInsertPark2 = "INSERT INTO park (parkcode, parkname, state, acreage, elevationinfeet, milesoftrail,"
				+ " numberofcampsites, climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource,"
				+ " parkdescription, entryfee, numberofanimalspecies )"

				+ " VALUES ('TMB', 'TestPark','Ohio', 100, 1000, 200, "
				+ " 10, 'HOT', 1000, 1000000, 'Oh oh oh', 'Kevin', " + " 'Destcription Test Park again', 0, 100) ";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark);
		jdbcTemplate.update(sqlInsertPark2);
		parkDao = new JDBCParkDao(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	

	@Test
	public void test_to_get_all_park_count() {

		List<Park> allPark = parkDao.getAllParks();
		int parkCount = 0;
		for (int i = 0; i < allPark.size(); i++) {
			parkCount++;
		}

		assertNotNull(allPark);
		assertEquals(parkCount, allPark.size());
		assertEquals(12, allPark.size());

		assertEquals("tmc", allPark.get(allPark.size() - 2).getParkCode());
		assertEquals("tmb", allPark.get(allPark.size() - 1).getParkCode());

		assertEquals("Test", allPark.get(allPark.size() - 2).getParkName());
		assertEquals("TestPark", allPark.get(allPark.size() - 1).getParkName());

		assertEquals("Michigan", allPark.get(allPark.size() - 2).getState());
		assertEquals("Ohio", allPark.get(allPark.size() - 1).getState());

		assertEquals(100, allPark.get(allPark.size() - 2).getAcreage());
		assertEquals(100, allPark.get(allPark.size() - 1).getAcreage());

		assertEquals(1000, allPark.get(allPark.size() - 2).getElevationInFeet());
		assertEquals(1000, allPark.get(allPark.size() - 1).getElevationInFeet());

		assertEquals(200, allPark.get(allPark.size() - 2).getMilesOfTrail(), 0001);
		assertEquals(200, allPark.get(allPark.size() - 1).getMilesOfTrail(), 0001);

		assertEquals(10, allPark.get(allPark.size() - 2).getNumberOfCampsites());
		assertEquals(10, allPark.get(allPark.size() - 1).getNumberOfCampsites());

		assertEquals("Cold", allPark.get(allPark.size() - 2).getClimate());
		assertEquals("HOT", allPark.get(allPark.size() - 1).getClimate());

		assertEquals(1000, allPark.get(allPark.size() - 2).getYearFounded());
		assertEquals(1000, allPark.get(allPark.size() - 1).getYearFounded());

		assertEquals(1000000, allPark.get(allPark.size() - 2).getAnnualVisitorCount());
		assertEquals(1000000, allPark.get(allPark.size() - 1).getAnnualVisitorCount());

		assertEquals("Test oh Test", allPark.get(allPark.size() - 2).getInspirationalQuote());
		assertEquals("Oh oh oh", allPark.get(allPark.size() - 1).getInspirationalQuote());

		assertEquals("Ryan", allPark.get(allPark.size() - 2).getInspirationalQuoteSource());
		assertEquals("Kevin", allPark.get(allPark.size() - 1).getInspirationalQuoteSource());

		assertEquals("Destcription Test Park", allPark.get(allPark.size() - 2).getParkDescription());
		assertEquals("Destcription Test Park again", allPark.get(allPark.size() - 1).getParkDescription());

		assertEquals(0, allPark.get(allPark.size() - 2).getEntryFee());
		assertEquals(0, allPark.get(allPark.size() - 1).getEntryFee());

		assertEquals(100, allPark.get(allPark.size() - 2).getNumberOfAnimalSpecies());
		assertEquals(100, allPark.get(allPark.size() - 1).getNumberOfAnimalSpecies());
	}

	@Test
	public void test_to_get_park_by_park_code() {

		Park testPark = parkDao.getParkByParkCode("TMC");

		String expectedParkName = "Test";
		String expectedState = "Michigan";
		int expectedAcreage = 100;
		int expectedElevationInFeet = 1000;
		double expectedMilesOfTrail = 200;
		int expectedNumberOfCampsites = 10;
		String expectedClimate = "Cold";
		int expectedYearFounded = 1000;
		int expectedaAnualVisitorCount = 1000000;
		String expectedInspirationalQuote = "Test oh Test";
		String expectedInspirationalQuoteSource = "Ryan";
		String expectedParkDescription = "Destcription Test Park";
		int expectedEntryFee = 0;
		int expectedNumberOfAnimalSpecies = 100;

		assertNotNull(testPark);
		assertEquals(expectedParkName, testPark.getParkName());
		assertEquals(expectedState, testPark.getState());
		assertEquals(expectedAcreage, testPark.getAcreage());
		assertEquals(expectedElevationInFeet, testPark.getElevationInFeet());
		assertEquals(expectedMilesOfTrail, testPark.getMilesOfTrail(), 0001);
		assertEquals(expectedNumberOfCampsites, testPark.getNumberOfCampsites());
		assertEquals(expectedClimate, testPark.getClimate());
		assertEquals(expectedYearFounded, testPark.getYearFounded());
		assertEquals(expectedaAnualVisitorCount, testPark.getAnnualVisitorCount());
		assertEquals(expectedInspirationalQuote, testPark.getInspirationalQuote());
		assertEquals(expectedInspirationalQuoteSource, testPark.getInspirationalQuoteSource());
		assertEquals(expectedParkDescription, testPark.getParkDescription());
		assertEquals(expectedEntryFee, testPark.getEntryFee());
		assertEquals(expectedNumberOfAnimalSpecies, testPark.getNumberOfAnimalSpecies());

	}
}