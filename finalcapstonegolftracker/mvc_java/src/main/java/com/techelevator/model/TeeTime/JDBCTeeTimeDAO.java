package com.techelevator.model.TeeTime;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Course.CourseDAO;

@Component
public class JDBCTeeTimeDAO implements TeeTimeDAO {
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCTeeTimeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Autowired
	private CourseDAO courseDao;

	@Override
	public void saveTeeTime(TeeTime teeTime, int playerId) {
		int teeTimeId = 0;
		
		if (teeTime.getLeagueId() == 0) {
			String sqlAddTeeTime = "INSERT INTO tee_time (time, numgolfers, courseid) VALUES (?, ?, ?)";
			jdbcTemplate.update(sqlAddTeeTime, teeTime.getTime(), teeTime.getNumGolfers(), teeTime.getCourseId());
			
			String sqlFindTeeTimeId = "SELECT teetimeid FROM tee_time WHERE time = ? AND numgolfers = ? AND courseid = ? LIMIT 1;";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindTeeTimeId, teeTime.getTime(), teeTime.getNumGolfers(), teeTime.getCourseId());
			if (results.next()) {
				teeTimeId = results.getInt("teetimeid");
			}
			
		}else {
		String sqlAddTeeTime = "INSERT INTO tee_time (time, leagueid, numgolfers, courseid) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sqlAddTeeTime, 
				teeTime.getTime(), teeTime.getLeagueId(), teeTime.getNumGolfers(), teeTime.getCourseId());
		
		String sqlFindTeeTimeId = "SELECT teetimeid FROM tee_time WHERE time = ? AND numgolfers = ? AND courseid = ? AND leagueid = ? LIMIT 1;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindTeeTimeId, teeTime.getTime(), teeTime.getNumGolfers(), teeTime.getCourseId(), teeTime.getLeagueId());
		if (results.next()) {
			teeTimeId = results.getInt("teetimeid");
		}
		}
		

		
		String sqlGolferTeeTime = "INSERT INTO golfer_teetime (teetimeid, id) VALUES (?, ?)";
		jdbcTemplate.update(sqlGolferTeeTime, teeTimeId, playerId);
		
	}
	
	@Override
	public List<TeeTime> getTeeTimesByGolferIdPastToday(int id) {
		List<TeeTime> teeTimes = new ArrayList <> ();
		String sqlTeeTimes = "SELECT t.*, g.id FROM golfer_teetime g JOIN tee_time t "
				+ "ON t.teetimeid = g.teetimeid WHERE t.time > current_date AND g.id = ? ORDER BY t.time";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlTeeTimes, id);
		while (results.next()) {
			teeTimes.add(mapRowToTeeTime(results));
		}
		return teeTimes;
	}
	
	private TeeTime mapRowToTeeTime(SqlRowSet results) {
		TeeTime teeTime = new TeeTime();
		
		teeTime.setTeeTimeId(results.getInt("teetimeid"));
		teeTime.setCourseId(results.getInt("courseid"));
		teeTime.setCourseName(courseDao.getCourseNameByCourseId(teeTime.getCourseId()));
		String dateTime = results.getString("time");
		LocalDate theDate = getDateFromString(dateTime);
		LocalTime theTime = getTimeFromString(dateTime);
		LocalDateTime dateAndTime = LocalDateTime.of(theDate, theTime);
		teeTime.setTime(dateAndTime);

		teeTime.setDateString(turnDateIntoString(results.getDate("time").toLocalDate()));
		teeTime.setTimeString(turnTimeIntoString(results.getTime("time")));
		teeTime.setNumGolfers(results.getInt("numgolfers"));
		
		return teeTime;
	}

	private String turnTimeIntoString(Time time) {
		LocalTime myTime = time.toLocalTime();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
		String timeNoSeconds = myTime.format(myFormatObj);
		
		return timeNoSeconds;
	}

	private String turnDateIntoString(LocalDate date) {
		String todayString = date.toString();
		String buildString = "";
	
		if (todayString.substring(5, 7).contentEquals("01")) {
			buildString = buildString + "January";
		}else if (todayString.substring(5, 7).contentEquals("02")) {
			buildString = buildString + "February";
		}else if (todayString.substring(5, 7).contentEquals("03")) {
			buildString = buildString + "March";
		}else if (todayString.substring(5, 7).contentEquals("04")) {
			buildString = buildString + "April";
		}else if (todayString.substring(5, 7).contentEquals("05")) {
			buildString = buildString + "May";
		}else if (todayString.substring(5, 7).contentEquals("06")) {
			buildString = buildString + "June";
		}else if (todayString.substring(5, 7).contentEquals("07")) {
			buildString = buildString + "July";
		}else if (todayString.substring(5, 7).contentEquals("08")) {
			buildString = buildString + "August";
		}else if (todayString.substring(5, 7).contentEquals("09")) {
			buildString = buildString + "September";
		}else if (todayString.substring(5, 7).contentEquals("10")) {
			buildString = buildString + "October";
		}else if (todayString.substring(5, 7).contentEquals("11")) {
			buildString = buildString + "November";
		}else if (todayString.substring(5, 7).contentEquals("12")) {
			buildString = buildString + "December";
		}
		buildString = buildString + " " + todayString.substring(8, 10) + ", " + todayString.substring(0, 4);
		
		return buildString;
	}

	@Override
	public int getLastTeeTimeId() {
		int id = 0;
		String sqlSelectAllCourses = "SELECT teetimeid FROM tee_time ORDER BY teetimeid DESC LIMIT 1";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllCourses);
		if (results.next()) {
			id = results.getInt("teetimeid");
		}
		return id;
	}
	
	@Override
	public LocalDate getDateFromString(String date) {
		LocalDate theDate;
		
		String stringYear = date.substring(0,4);
		String stringMonth = date.substring(5,7);
		String stringDay = date.substring(8, 10);
		int year = Integer.parseInt(stringYear);
		int month = Integer.parseInt(stringMonth);
		int day = Integer.parseInt(stringDay);
		
		theDate = LocalDate.of(year,  month,  day);
		
		return theDate;
	}
	
	@Override
	public LocalTime getTimeFromString(String time) {
		LocalTime theTime;
		
		String stringHour = time.substring(11,13);
		String stringMinutes = time.substring(14, 16);
		int hour = Integer.parseInt(stringHour);
		int minutes = Integer.parseInt(stringMinutes);
		
		theTime = LocalTime.of(hour, minutes);
		
		return theTime;
	}

	@Override
	public List<LocalDateTime> getTeeTimesByCourse(int courseId, String date) {
		LocalDate selectedDate = getDateFromString(date);
		List<LocalTime> bookedTimes = getBookedTimesByCourse(courseId, selectedDate);
		LocalDate today = LocalDate.now();
		LocalDateTime firstBooking = LocalDateTime.of(selectedDate, LocalTime.of(8, 0));
		
		List <LocalDateTime> availableTimes = new ArrayList<>();
		
		if (selectedDate.equals(today)) {
			
			firstBooking = LocalDateTime.now();
			int minutes = firstBooking.getMinute();
			long minutesToAdd = 0;
		
			while (minutes%10 != 0) {
				minutes++;
				minutesToAdd++;
			}
			
			firstBooking = firstBooking.plusMinutes(minutesToAdd);
		}
		
		while (firstBooking.isBefore(LocalDateTime.of(selectedDate, LocalTime.of(17, 1)))) {
			boolean available = true;
			for (int x = 0; x<bookedTimes.size();x++) {
				if (firstBooking.toLocalTime().equals(bookedTimes.get(x))) {
					available = false;
				}
			}
			if (available) {
				availableTimes.add(firstBooking);
			}
			firstBooking = firstBooking.plusMinutes(10);
		}
		
		return availableTimes;
	}

	private List<LocalTime> getBookedTimesByCourse(int courseId, LocalDate date) {
		List <TeeTime> getBookings = new ArrayList<>();
		List <LocalTime> bookedTimes = new ArrayList<>();
		String sqlSelectBookedTimes = "SELECT * FROM tee_time WHERE courseid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectBookedTimes, courseId);
		while (results.next()) {
			getBookings.add(mapRowToTeeTime(results));
		}
		for (int x = 0; x<getBookings.size();x++) {
			LocalDate dateSort = getBookings.get(x).getTime().toLocalDate();
			LocalTime timeSort = getBookings.get(x).getTime().toLocalTime();
			if (dateSort.equals(date)) {
				bookedTimes.add(timeSort);
			}
		}
		return bookedTimes;
	}
	
	
	
}
