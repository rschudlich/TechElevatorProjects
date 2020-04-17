package com.techelevator.model.TeeTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface TeeTimeDAO {

	void saveTeeTime(TeeTime teeTime, int playerId);

	int getLastTeeTimeId();

	List<TeeTime> getTeeTimesByGolferIdPastToday(int id);
	
	List<LocalDateTime> getTeeTimesByCourse(int courseId, String date);
	
	LocalTime getTimeFromString(String time);
	
	LocalDate getDateFromString(String date);

}
