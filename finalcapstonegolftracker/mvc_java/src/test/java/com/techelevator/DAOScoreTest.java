package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
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

import com.techelevator.model.Score.JDBCScoreDAO;
import com.techelevator.model.Score.Score;
import com.techelevator.model.User.JDBCUserDAO;
import com.techelevator.security.PasswordHasher;

public class DAOScoreTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCScoreDAO scoredao;
	private JDBCUserDAO userdao;
	private final int TEST_SCORE = 70;
	private final int TEST_GOLFER = 10000;
	private final int TEST_TEETIME = 1;
	private final int TEST_COURSE = 1;
	private final int TEST_SCOREID = 11000;
	
	private PasswordHasher hash;
	private final String USER_NAME = "testuser";
	private final String USER_PASSWORD = "KQfZkVIlXcGOeKzFnmmLTw==";
	private final String USER_SALT = "5SfhtBdlFRkFTtUMXnPMHL1eRt0qBATjWi9bvMRxPlhs1Qs0R+taaQvdiSq24QWmJUOYwx+QVPH7DfXcsVnoBYyRZM+W6FrQIEsMyh19EKh3z1nUEvHUjRoGGaQP9cnInpf/t+xtBfpg1pwkHk0VVrwNDWcafxjt98DoT0WwFAo=";
	private final String USER_ROLE = "Golfer";

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
		
		scoredao = new JDBCScoreDAO(dataSource);
		userdao = new JDBCUserDAO(dataSource, hash);
		JdbcTemplate jdbctemplate = new JdbcTemplate(dataSource);
		
		String sqlInsertUser = "INSERT INTO app_user (id, user_name, password, salt, role) VALUES (?, ?, ?, ?, ?)";
		jdbctemplate.update(sqlInsertUser, TEST_GOLFER, USER_NAME, USER_PASSWORD, USER_SALT, USER_ROLE);
		
		String sqlInsertScore = "INSERT INTO scores (scoreid, score, id, teetimeid, courseid) VALUES (?, ?, ?, ?, ?)";
		jdbctemplate.update(sqlInsertScore, TEST_SCOREID, TEST_SCORE, TEST_GOLFER, TEST_TEETIME, TEST_COURSE);
		

	}
	
	@Test
	public void testGetScoresById() {
		Score testScore = new Score();
		testScore.setScore(TEST_SCORE);
		testScore.setId(TEST_GOLFER);
		testScore.setCourseId(TEST_COURSE);
		testScore.setTeeTimeId(TEST_TEETIME);
		testScore.setScoreId(TEST_SCOREID);
		List <Score> expected = new ArrayList<>();
		expected.add(testScore);
		List <Score> actual = scoredao.getAllScoresByUserId(TEST_GOLFER);
		assertScoresEqual(expected.get(0), actual.get(0));
	}

	private void assertScoresEqual(Score expected, Score actual) {
		assertEquals(expected.getScore(), actual.getScore());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getCourseId(), actual.getCourseId());
		assertEquals(expected.getTeeTimeId(), actual.getTeeTimeId());
		assertEquals(expected.getScoreId(), actual.getScoreId());
		
	}

}
