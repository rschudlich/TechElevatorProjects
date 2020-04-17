package com.techelevator.model.League;

public class League {
	private int leagueId;
	private int courseId;
	private String name;
	private String owner;
	
	public League() {
		
	}
	
	public League(int leagueId, int courseId, String name, String owner) {
		super();
		this.leagueId = leagueId;
		this.courseId = courseId;
		this.name = name;
		this.owner = owner;
	}
	public int getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
}
