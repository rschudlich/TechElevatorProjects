package com.techelevator.npgeek.model;

import java.time.LocalDate;

public class Weather {
	private String parkCode;
	private int fiveDayForecastValue;
	private int low;
	private int high; 
	private String forecast;
	private String day;
	
	public void setDay(int fiveDayForecastValue) {  
		LocalDate date = LocalDate.now();
		if (fiveDayForecastValue == 1) {
			day = "Today";
		}else if (fiveDayForecastValue == 2) {
			day = "Tomorrow";
		}else if (fiveDayForecastValue == 3) {
			date = date.plusDays(2);
			String day1 = date.getDayOfWeek().toString().toLowerCase();
			day = day1.substring(0, 1).toUpperCase() + day1.substring(1);
		}else if (fiveDayForecastValue == 4) {
			date = date.plusDays(3);
			String day1 = date.getDayOfWeek().toString().toLowerCase();
			day = day1.substring(0, 1).toUpperCase() + day1.substring(1);
		}else if (fiveDayForecastValue == 5) {
			date = date.plusDays(4);
			String day1 = date.getDayOfWeek().toString().toLowerCase();
			day = day1.substring(0, 1).toUpperCase() + day1.substring(1);
		} 
	}
	public String getDay() {
		return day;
	}

	public String getParkCode() { 
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public int getFiveDayForecastValue() {
		return fiveDayForecastValue;
	}
	public void setFiveDayForecastValue(int fiveDayForecastValue) {
		this.fiveDayForecastValue = fiveDayForecastValue;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
}
