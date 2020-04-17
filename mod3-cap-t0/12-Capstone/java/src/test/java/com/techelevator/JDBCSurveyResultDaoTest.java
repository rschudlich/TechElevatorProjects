package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.npgeek.model.JDBCParkDao;
import com.techelevator.npgeek.model.JDBCSurveyResultDao;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.SurveyResult;

public class JDBCSurveyResultDaoTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCSurveyResultDao surveyResultDao;
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
		String sqlInsertPark3 = "INSERT INTO park (parkcode, parkname, state, acreage, elevationinfeet, milesoftrail,"
				+ " numberofcampsites, climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource,"
				+ " parkdescription, entryfee, numberofanimalspecies )"
				
				+ " VALUES ('TMA', 'Test','Michigan', 100, 1000, 200, "
				+ " 10, 'Cold', 1000, 1000000, 'Test oh Test', 'Ryan', " + " 'Destcription Test Park', 0, 100) ";
		
		String sqlInsertPark4 = "INSERT INTO park (parkcode, parkname, state, acreage, elevationinfeet, milesoftrail,"
				+ " numberofcampsites, climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource,"
				+ " parkdescription, entryfee, numberofanimalspecies )"
				
				+ " VALUES ('TMD', 'TestPark','Ohio', 100, 1000, 200, "
				+ " 10, 'HOT', 1000, 1000000, 'Oh oh oh', 'Kevin', " + " 'Destcription Test Park again', 0, 100) ";

		String sqlInsertSurveyResult1 = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel)"

				+ " VALUES (991,'TMC', 'wwwww@hotmail.com', 'Michigan', 'inactive')";

		String sqlInsertSurveyResult2 = "INSERT INTO survey_result ( surveyid, parkcode, emailaddress, state, activitylevel)"

				+ " VALUES (992, 'TMB', 'yyyyy@hotmail.com', 'Ohio', 'active')";
		String sqlInsertSurveyResult3 = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (993,'TMC', 'xxxxx@hotmail.com', 'Michigan', 'inactive')";
		
		String sqlInsertSurveyResult4 = "INSERT INTO survey_result ( surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (994,'TMC', 'ttttt@hotmail.com', 'Ohio', 'active')";
		String sqlInsertSurveyResult5 = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (995,'TMC', 'sssss@hotmail.com', 'Michigan', 'inactive')";
		
		String sqlInsertSurveyResult6 = "INSERT INTO survey_result ( surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (996,'TMC', 'uuuuu@hotmail.com', 'Ohio', 'active')";
		String sqlInsertSurveyResult7 = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (997,'TMA', 'aaaaa@hotmail.com', 'Michigan', 'inactive')";
		
		String sqlInsertSurveyResult8 = "INSERT INTO survey_result ( surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (998,'TMB', 'bbbbb@hotmail.com', 'Ohio', 'active')";
		String sqlInsertSurveyResult9 = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (999,'TMD', 'ccccc@hotmail.com', 'Michigan', 'inactive')";
		
		String sqlInsertSurveyResult10 = "INSERT INTO survey_result ( surveyid, parkcode, emailaddress, state, activitylevel)"
				
				+ " VALUES (910,'TMD', 'dddd@hotmail.com', 'Ohio', 'active')";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark);
		jdbcTemplate.update(sqlInsertPark2);
		jdbcTemplate.update(sqlInsertPark3);
		jdbcTemplate.update(sqlInsertPark4);
		jdbcTemplate.update(sqlInsertSurveyResult1);
		jdbcTemplate.update(sqlInsertSurveyResult2);
		jdbcTemplate.update(sqlInsertSurveyResult3);
		jdbcTemplate.update(sqlInsertSurveyResult4);
		jdbcTemplate.update(sqlInsertSurveyResult5);
		jdbcTemplate.update(sqlInsertSurveyResult6);
		jdbcTemplate.update(sqlInsertSurveyResult7);
		jdbcTemplate.update(sqlInsertSurveyResult8);
		jdbcTemplate.update(sqlInsertSurveyResult9);
		jdbcTemplate.update(sqlInsertSurveyResult10);
		parkDao = new JDBCParkDao(dataSource);
		surveyResultDao = new JDBCSurveyResultDao(dataSource);

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}


	@Test
	public void test_to_save_first_new_surveyResult() {
		List<SurveyResult> newSurvey = new ArrayList<>();
		
		SurveyResult test = new SurveyResult();		
		test.setParkCode("FFF");
		test.setEmail("testtest@hotmail.com");
		test.setState("New York");
		test.setActivityLevel("extremely active");
		
		newSurvey.add(test);
		surveyResultDao.save(test);
				
		SurveyResult test1 = new SurveyResult();		
		test1.setParkCode("TTT");
		test1.setEmail("wwwww@hotmail.com");
		test1.setState("Michigan");
		test1.setActivityLevel("inactive");

		newSurvey.add(test1);
		surveyResultDao.save(test1);
		
		
		SurveyResult test2 = new SurveyResult();		
		test2.setParkCode("YYY");
		test2.setEmail("yyyyyyy@hotmail.com");
		test2.setState("Ohio");
		test2.setActivityLevel("active");

		newSurvey.add(test2);
		surveyResultDao.save(test2);
		
		assertNotNull(newSurvey);
		assertEquals(3, newSurvey.size());
		
		assertEquals("FFF", newSurvey.get(newSurvey.size() - 3).getParkCode());
		assertEquals("testtest@hotmail.com", newSurvey.get(newSurvey.size() - 3).getEmail());
		assertEquals("New York", newSurvey.get(newSurvey.size() - 3).getState());
		assertEquals("extremely active", newSurvey.get(newSurvey.size() - 3).getActivityLevel());
		
		assertEquals("TTT", newSurvey.get(newSurvey.size() - 2).getParkCode());
		assertEquals("wwwww@hotmail.com", newSurvey.get(newSurvey.size() - 2).getEmail());
		assertEquals("Michigan", newSurvey.get(newSurvey.size() - 2).getState());
		assertEquals("inactive", newSurvey.get(newSurvey.size() - 2).getActivityLevel());
		
		assertEquals("YYY", newSurvey.get(newSurvey.size() - 1).getParkCode());
		assertEquals("yyyyyyy@hotmail.com", newSurvey.get(newSurvey.size() - 1).getEmail());
		assertEquals("Ohio", newSurvey.get(newSurvey.size() - 1).getState());
		assertEquals("active", newSurvey.get(newSurvey.size() - 1).getActivityLevel());
	}
	 
}