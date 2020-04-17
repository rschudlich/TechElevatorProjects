package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.jdbc.JDBCReservationDAO;

public abstract class DAOReservationTest {
	

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;

	ReservationDAO reservationDao = new JDBCReservationDAO(dataSource);
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
	public void make_reservation_makes_reservation() {
		LocalDate fromDate = LocalDate.of(2020, 02, 01);
		LocalDate toDate = LocalDate.of(2020, 02, 10);
		LocalDate createDate = LocalDate.of(2020, 02, 22);
		Reservation theReservation = new Reservation(1, "Ryan", fromDate, toDate, createDate);
		
		Reservation savedReservation = reservationDao.makeReservation(1, "Ryan", fromDate, toDate, createDate);
		
		assertNotEquals(null, theReservation.getId());
		// asserts to make sure each attribute is the same for the cities to be equal
		assertEquals(theReservation.getId(), savedReservation.getId());
		assertEquals(theReservation.getName(), savedReservation.getName());
		assertEquals(theReservation.getFromDate(), savedReservation.getFromDate());
		assertEquals(theReservation.getToDate(), savedReservation.getToDate());
		assertEquals(theReservation.getCreateDate(), savedReservation.getCreateDate());
	}
}