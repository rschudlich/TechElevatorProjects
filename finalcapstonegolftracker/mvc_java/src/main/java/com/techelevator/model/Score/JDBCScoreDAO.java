package com.techelevator.model.Score;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Course.Course;

@Component
public class JDBCScoreDAO implements ScoreDAO {
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCScoreDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Score> getAllScoresByUserId(Integer id) {
		List <Score> scores = new ArrayList<>();
		
		String sqlSelectAllCourses = "SELECT s.*, t.time FROM scores s "
				+ "JOIN tee_time t ON t.teetimeid = s.teetimeid WHERE id = ? ORDER BY time DESC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllCourses, id);
		while (results.next()) {
			scores.add(mapRowToScore(results));
		}
		return scores;
	}
	
	@Override
	public List<Integer> getAllScoresByLeagueIdAndUserId(int leagueId, int userId) {
		List <Integer> scores = new ArrayList<>();
		
		String sqlSelectAllCourses = "SELECT score FROM scores s JOIN tee_time t "
				+ "ON t.teetimeid = s.teetimeid WHERE t.leagueid = ? AND id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllCourses, leagueId, userId);
		while (results.next()) {
			scores.add(results.getInt("score"));
		}
		return scores;
	}

	private Score mapRowToScore(SqlRowSet results) {
		Score theScore = new Score();
		
		theScore.setScore(results.getInt("score"));
		theScore.setId(results.getInt("id"));
		theScore.setCourseId(results.getInt("courseid"));
		theScore.setScoreId(results.getInt("scoreid"));
		theScore.setTeeTimeId(results.getInt("teetimeid"));
		return theScore;
	}

	@Override
	public void saveScore(Score theScore) {
		String sqlAddScore = "INSERT INTO scores (score, id, courseid, teetimeid) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sqlAddScore, theScore.getScore(), theScore.getId(), theScore.getCourseId(), theScore.getTeeTimeId());	
		
	}
	
	@Override
	public String getDateFromScoreId(int id) {
		String time = null;
		String sqlMySql = "SELECT t.time FROM scores s JOIN tee_time t ON t.teetimeid = s.teetimeid WHERE s.scoreid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlMySql, id);
		if (results.next()) {
			time = results.getString("time");
		}
		return time;
	}
	
	@Override
	public Course getCourseRatingAndSlopeFromScoreID(int scoreId) {
		Course myCourse = new Course();
		String sqlMySql = "SELECT rating, slope FROM courses JOIN scores ON scores.courseid = courses.courseid WHERE scoreid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlMySql, scoreId);
		if (results.next()) {
			myCourse.setSlope(results.getInt("slope"));
			myCourse.setRating(results.getDouble("rating"));
		}
		return myCourse;
	}

}
