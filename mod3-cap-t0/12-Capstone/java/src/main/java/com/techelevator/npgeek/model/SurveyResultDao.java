package com.techelevator.npgeek.model;

import java.util.List;

public interface SurveyResultDao {
	public List<Park> getAllSurveyCounts();
	public void save(SurveyResult newSurveyResult);
	
}
