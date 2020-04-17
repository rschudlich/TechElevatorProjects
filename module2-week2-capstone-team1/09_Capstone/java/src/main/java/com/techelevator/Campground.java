package com.techelevator;

import java.time.LocalDate;

public class Campground {
	
	private int id;
	private int parkId;
	private String name;
	private int openFromMM;
	private int openToMM;
	private double dailyFee;
	
	public Campground() {
		
	}
	
	public Campground(int parkId, String name, int openFromMM, int openToMM, double dailyFee) {
		this.parkId = parkId;
		this.name = name;
		this.openFromMM = openFromMM;
		this.openToMM = openToMM;
		this.dailyFee = dailyFee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenFromMM() {
		return openFromMM;
	}
	public void setOpenFromMM(int openFromMM) {
		this.openFromMM = openFromMM;
	}
	public int getOpenToMM() {
		return openToMM;
	}
	public void setOpenToMM(int openToMM) {
		this.openToMM = openToMM;
	}
	public double getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	
}
