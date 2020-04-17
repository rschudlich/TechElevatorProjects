package com.techelevator.model.TeeTime;

import java.time.LocalDateTime;

public class TeeTime {
	private int teeTimeId;
	private LocalDateTime time;
	private String dateString;
	private String timeString;
	private int leagueId;
	private int numGolfers;
	private int courseId;
	private String courseName;
	
	public TeeTime () {
		this.leagueId = 0;
	}
	
	public int getTeeTimeId() {
		return teeTimeId;
	}
	public void setTeeTimeId(int teeTimeId) {
		this.teeTimeId = teeTimeId;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public int getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public int getNumGolfers() {
		return numGolfers;
	}
	public void setNumGolfers(int numGolfers) {
		this.numGolfers = numGolfers;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
