package com.techelevator;

import java.util.List;

public interface ParkDAO {
	public List<Park> getAllParks();
	public List<Integer> getAllParksId();
	public Park getParkByName(String name);
}
