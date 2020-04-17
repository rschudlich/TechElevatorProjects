package com.techelevator.model.Score;

import java.time.LocalDate;

public class Score {
	private int scoreId;
	private int score;
	private int id;
	private int teeTimeId;
	private int courseId;
	private String courseName;
	private LocalDate date;
	private String dateString;
	
	public Score() {
		
	}
	
	public Score (int score, int teeTimeId, int courseId, int id) {
		this.score = score;
		this.teeTimeId = teeTimeId;
		this.courseId = courseId;
		this.id = id;
	}
	
	public int getScoreId() {
		return scoreId;
	}
	public void setScoreId(int scoreId) {
		this.scoreId = scoreId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeeTimeId() {
		return teeTimeId;
	}
	public void setTeeTimeId(int teeTimeId) {
		this.teeTimeId = teeTimeId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String name) {
		this.courseName = name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
}
