package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCSurveyResultDao implements SurveyResultDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCSurveyResultDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private int getSurveyResultId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet(" SELECT nextval('seq_surveyid')");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Uhoh!  Something went wrong while getting the next id!");
		}
	}

	

	@Override
	public void save(SurveyResult newSurveyResult) {
		newSurveyResult.setSurveyId(getSurveyResultId());
		String sqlCreateSurveyResult = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activityLevel)"
				+ "VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlCreateSurveyResult, newSurveyResult.getSurveyId(), newSurveyResult.getParkCode(),
				newSurveyResult.getEmail(), newSurveyResult.getState(), newSurveyResult.getActivityLevel());
	}

	@Override
	public List<Park> getAllSurveyCounts() {

		String sqlFindAllParks = "SELECT COUNT(s.*), p.parkname, p.parkcode FROM survey_result s JOIN park p ON p.parkcode = s.parkcode GROUP BY s.parkcode, p.parkname, p.parkcode ORDER BY count DESC, p.parkname";
		List<Park> allParks = new ArrayList<>();
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindAllParks);
		while (result.next()) {

			allParks.add(mapCountToParks(result));

		}
		return allParks;

	}

	private Park mapCountToParks(SqlRowSet results) {
		Park myPark = new Park();
		myPark.setParkCode(results.getString("parkCode").toLowerCase());
		myPark.setParkName(results.getString("parkName"));
		myPark.setCount(results.getInt("count"));
		return myPark;
	}

}