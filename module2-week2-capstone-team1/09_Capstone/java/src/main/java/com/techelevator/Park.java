package com.techelevator;

import java.time.LocalDate;

public class Park {
	public int id;
	public String name;
	public String location;
	public LocalDate establishDate;
	public int area;
	public int visitors;
	public String description;
	
	public Park() {

	}

	public Park(int id, String name, String location, LocalDate establishDate, int area, int visitors, String description) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.establishDate = establishDate;
		this.area = area;
		this.visitors = visitors;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getVisitors() {
		return visitors;
	}
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
