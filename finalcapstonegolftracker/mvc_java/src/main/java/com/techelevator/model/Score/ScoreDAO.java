package com.techelevator.model.Score;

import java.util.List;

import com.techelevator.model.Course.Course;

public interface ScoreDAO {

	public List<Score> getAllScoresByUserId(Integer id);
	
	public void saveScore(Score theScore);

	public String getDateFromScoreId(int id);

	Course getCourseRatingAndSlopeFromScoreID(int scoreId);

	List<Integer> getAllScoresByLeagueIdAndUserId(int leagueId, int userId);
}
