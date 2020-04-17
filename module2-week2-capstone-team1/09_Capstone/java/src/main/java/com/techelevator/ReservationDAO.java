package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	public List<Reservation> getReservationsOverlappingStay(int campgroundId, LocalDate arrivalDate, LocalDate departureDate);
	public Reservation makeReservation(int siteID, String name, LocalDate fromDate, LocalDate toDate, LocalDate currentDate);
}
