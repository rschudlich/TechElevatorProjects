package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.jdbc.JDBCCampgroundDAO;

public abstract class DAOCampgroundTest {
	

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;

	CampgroundDAO campgroundDao = new JDBCCampgroundDAO(dataSource);
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
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
	
	@Test
	public void returns_campground_by_park_id() {
		Campground theCampground = new Campground();
		theCampground.setParkId(3);

		List<Campground> campgrounds = campgroundDao.getCampgroundsByParkId(3);
		Campground savedCampground = new Campground();
		savedCampground.setParkId(campgrounds.get(0).getParkId());
		
		assertNotEquals(null, savedCampground.getId());
		assertEquals(theCampground.getId(), savedCampground.getId());
	}
	
	
	@Test
	public void returns_campground_by_campground_id() {
		Campground theCampground = new Campground();
		theCampground.setId(3);

		Campground savedCampground = campgroundDao.getCampgroundByCampgroundId(3, 1);
		
		assertNotEquals(null, savedCampground.getId());
		assertEquals(theCampground.getId(), savedCampground.getId());
	}
}
