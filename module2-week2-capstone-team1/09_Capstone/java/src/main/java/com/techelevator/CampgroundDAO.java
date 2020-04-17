package com.techelevator;

import java.util.List;

public interface CampgroundDAO {
	public List<Campground> getCampgroundsByParkId(int id);
	public Campground getCampgroundByCampgroundId(int id, int parkId);
}
