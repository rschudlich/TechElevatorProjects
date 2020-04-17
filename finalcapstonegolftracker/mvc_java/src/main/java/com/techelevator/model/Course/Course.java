package com.techelevator.model.Course;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class Course {
	private int courseId;
	private int id;

	@NotBlank(message = "Must enter a course name")
	private String name;
	
	@Min (value = 20, message = "Par is too low")
	@Max (value = 100, message = "Par is too high")
	private Integer par;
	
	private Integer slope;
	
	private Double rating;
	
	@NotBlank(message = "Must enter address")
	private String address;
	
	@NotBlank(message = "Must enter a city")
	private String city;
	
	@NotBlank(message = "Must select your state")
	private String state;
	
	@Min (value = 10000, message = "Must enter valid zip")
	@Max (value = 99999, message = "Must enter valid zip")
	private Integer zip;
	private double latitude;
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Course() {
		
	}
	
	public Course(String name, int par, int slope, double rating, String address, String city, String state, int zip) {
		this.name = name;
		this.par = par;
		this.slope = slope;
		this.rating = rating;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getPar() {
		return par;
	}
	public void setPar(Integer par) {
		this.par = par;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Integer getSlope() {
		return slope;
	}
	public void setSlope(Integer slope) {
		this.slope = slope;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
}
