package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.TeeTime.JDBCTeeTimeDAO;

public class DAOTeeTimeTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCTeeTimeDAO teetimedao;
	private final int TEST_ID = 10000;
	private final LocalDateTime TEST_TIME = LocalDateTime.of(2020, 4, 17, 17, 30);
	private final int TEST_LEAGUE = 1;
	private final int TEST_GOLFERS = 3;
	private final int TEST_COURSE = 1;

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/capstone");
		dataSource.setUsername("capstone_appuser");
		dataSource.setPassword("capstone_appuser1");
		/* The following line disables autocommit for connections 
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	/* This method provides access to the DataSource for subclasses so that 
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Before
	public void setup() {
		
		teetimedao = new JDBCTeeTimeDAO(dataSource);
		
		String sqlInsertTeeTime = "INSERT INTO tee_time (teetimeid, time, leagueid, numgolfers, courseid) VALUES (?, ?, ?, ?, ?)";
		JdbcTemplate jdbctemplate = new JdbcTemplate(dataSource);
		jdbctemplate.update(sqlInsertTeeTime, TEST_ID, TEST_TIME, TEST_LEAGUE, TEST_GOLFERS, TEST_COURSE);
	}
	
	@Test
	public void testGetLastTeeTime() {
		int actual = teetimedao.getLastTeeTimeId();
		assertEquals(TEST_ID, actual);
	}

}
