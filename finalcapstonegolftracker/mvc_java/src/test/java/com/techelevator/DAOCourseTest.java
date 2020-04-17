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
import org.springframework.ui.ModelMap;

import com.techelevator.model.Course.Course;
import com.techelevator.model.Course.JDBCCourseDAO;


public class DAOCourseTest {
	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private JDBCCourseDAO coursedao;
	private int courseSizeBefore = 0;
	
	private final int COURSE_ID = 10000;
	private final String COURSE_NAME = "Test Course";
	private final int COURSE_PAR = 71;
	private final int COURSE_SLOPE = 145;
	private final double COURSE_RATING = 75.2;
	private final String COURSE_ADDRESS = "440 Burroughs St";
	private final String COURSE_CITY = "Detroit";
	private final String COURSE_STATE = "MI";
	private final int COURSE_ZIP = 48202;
	
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
		
		coursedao = new JDBCCourseDAO(dataSource);
		
		List <Course> allCourses = coursedao.getAllCourses();
		courseSizeBefore = allCourses.size();
		
		String sqlInsertCourse = "INSERT INTO courses (courseid, name, par, slope, rating, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertCourse, COURSE_ID, COURSE_NAME, COURSE_PAR, COURSE_SLOPE, COURSE_RATING, COURSE_ADDRESS, COURSE_CITY, COURSE_STATE, COURSE_ZIP);
		
	}
	
	//This test shows that the size of the getAllCourses method in the database is 1 greater after a test course is inserted in the setup.
	//courseSizeBefore is a variable whose value is assigned in the setup before the test course is inserted.
	@Test
	public void testGetAllCourses() { 
		List <Course> allCourses = coursedao.getAllCourses();
		int courseSizeAfter = allCourses.size();
		
		assertEquals((courseSizeBefore + 1), courseSizeAfter);
	}
	
	@Test
	public void testSearchCourse() {
		List <Course> expected = new ArrayList<>();
		Course expectedCourse = new Course(COURSE_NAME, COURSE_PAR, COURSE_SLOPE, COURSE_RATING, COURSE_ADDRESS, COURSE_CITY, COURSE_STATE, COURSE_ZIP);
		expected.add(expectedCourse);
		ModelMap map = null;
		List <Course> actual = coursedao.searchCourses(COURSE_NAME, COURSE_CITY);
		assertCoursesEqual(expected.get(0), actual.get(0)); //method that takes value of both courses and asserts they are all equal
	}
	
	@Test
	public void testFindCourseById() {
		String actualName = coursedao.getCourseNameByCourseId(COURSE_ID);
		assertEquals(COURSE_NAME, actualName);
	}
	
	@Test
	public void testFindCourseByName() {
		int actualId = coursedao.getCourseIdByCourseName(COURSE_NAME);
		assertEquals(COURSE_ID, actualId);
	}

	private void assertCoursesEqual(Course expected, Course actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPar(), actual.getPar());
		assertEquals(expected.getSlope(), actual.getSlope());
		assertEquals(expected.getRating(), actual.getRating());
		assertEquals(expected.getAddress(), actual.getAddress());
		assertEquals(expected.getCity(), actual.getCity());
		assertEquals(expected.getState(), actual.getState());
		assertEquals(expected.getZip(), actual.getZip());
		
	}

}