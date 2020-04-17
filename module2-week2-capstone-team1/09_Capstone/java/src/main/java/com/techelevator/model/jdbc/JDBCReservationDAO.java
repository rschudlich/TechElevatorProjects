package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Reservation;
import com.techelevator.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getReservationsOverlappingStay(int campgroundId, LocalDate arrivalDate, LocalDate departureDate) {
		List<Reservation> reservations = new ArrayList <> ();
		Reservation theReservation = null;
		String sqlSelectReservations = "SELECT * FROM reservation" + 
				"  JOIN site s ON s.site_id = reservation.site_id" + 
				"  WHERE s.campground_id = ?" + 
				"  AND ? BETWEEN from_date AND to_date" + 
				"  OR ? BETWEEN from_date AND to_date";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectReservations, campgroundId, arrivalDate, departureDate);
		while (results.next()) {
			theReservation = mapRowToReservation(results);
			reservations.add(theReservation);
		}
		return reservations; 
	}

	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation = new Reservation();
		theReservation.setId(results.getLong("reservation_id"));
		theReservation.setSiteId(results.getInt("site_id"));
		theReservation.setName(results.getString("name"));
		theReservation.setFromDate(results.getDate("from_date").toLocalDate());
		theReservation.setToDate(results.getDate("to_date").toLocalDate());
		theReservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return theReservation;
	}

	@Override
	public Reservation makeReservation(int siteID, String name, LocalDate fromDate, LocalDate toDate, LocalDate currentDate) {
		Reservation theReservation = new Reservation (siteID, name, fromDate, toDate, currentDate);
		Long reservationId = getNextReservationId();
		String sqlCreateReservation = "INSERT INTO reservation VALUES (?, ?, ?, ?, ?, ?)";
				theReservation.setId(getNextReservationId());
				
				jdbcTemplate.update(sqlCreateReservation, reservationId, 
						siteID, name, fromDate, toDate, currentDate);
		return theReservation;
		
	}
	private Long getNextReservationId() {
		String sqlNextId = "SELECT * FROM reservation ORDER BY reservation_id DESC LIMIT 1";
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet(sqlNextId);
		Long id = (long) 0;
		if (nextIdResult.next()) {
			id = nextIdResult.getLong("reservation_id") + 1;
		} else {
			throw new RuntimeException("Uhoh!  Something went wrong while getting the next id!");
		}
		return id;
	}

}
