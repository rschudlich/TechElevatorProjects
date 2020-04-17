package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.npgeek.model.JDBCParkDao;
import com.techelevator.npgeek.model.JDBCWeatherDao;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.Weather;

public class JDBCWeatherDaoTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCParkDao parkDao;
	private JDBCWeatherDao weatherDao;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/npgeek");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		String sqlInsertPark = "INSERT INTO park (parkcode, parkname, state, acreage, elevationinfeet, milesoftrail,"
				+ " numberofcampsites, climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource,"
				+ " parkdescription, entryfee, numberofanimalspecies )"

				+ " VALUES ('TMC', 'Test','Michigan', 100, 1000, 200, "
				+ " 10, 'Cold', 1000, 1000000, 'Test oh Test', 'Ryan', " + " 'Destcription Test Park', 0, 100) ";

		String sqlInsertWeather1 = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast)"
				+ " VALUES( 'TMC', 1, 27, 40, 'snow')";
		String sqlInsertWeather2 = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast)"
				+ " VALUES( 'TMC', 2, 31, 43, 'snow')";
		String sqlInsertWeather3 = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast)"
				+ " VALUES( 'TMC', 3, 28, 40, 'partly cloudy')";
		String sqlInsertWeather4 = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast)"
				+ " VALUES( 'TMC', 4, 24, 34, 'cloudy')";
		String sqlInsertWeather5 = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast)"
				+ " VALUES( 'TMC', 5, 25, 32, 'snow')";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark);
		jdbcTemplate.update(sqlInsertWeather1);
		jdbcTemplate.update(sqlInsertWeather2);
		jdbcTemplate.update(sqlInsertWeather3);
		jdbcTemplate.update(sqlInsertWeather4);
		jdbcTemplate.update(sqlInsertWeather5);

		parkDao = new JDBCParkDao(dataSource);
		weatherDao = new JDBCWeatherDao(dataSource);

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}



	@Test
	public void test_to_get_next_5_day_weather_in_park() {

		Park test = new Park(); 
		test.setParkCode("TMC");

		List<Weather> weatherEachDay = weatherDao.getWeatherByParkCode("TMC");

		assertNotNull(weatherEachDay);

		assertEquals("tmc", weatherEachDay.get(weatherEachDay.size() - 1).getParkCode());

		assertEquals(5, weatherEachDay.get(weatherEachDay.size() - 1).getFiveDayForecastValue());
		assertEquals(4, weatherEachDay.get(weatherEachDay.size() - 2).getFiveDayForecastValue());
		assertEquals(3, weatherEachDay.get(weatherEachDay.size() - 3).getFiveDayForecastValue());
		assertEquals(2, weatherEachDay.get(weatherEachDay.size() - 4).getFiveDayForecastValue());
		assertEquals(1, weatherEachDay.get(weatherEachDay.size() - 5).getFiveDayForecastValue());

		assertEquals(25, weatherEachDay.get(weatherEachDay.size() - 1).getLow());
		assertEquals(24, weatherEachDay.get(weatherEachDay.size() - 2).getLow());
		assertEquals(28, weatherEachDay.get(weatherEachDay.size() - 3).getLow());
		assertEquals(31, weatherEachDay.get(weatherEachDay.size() - 4).getLow());
		assertEquals(27, weatherEachDay.get(weatherEachDay.size() - 5).getLow());

		assertEquals(32, weatherEachDay.get(weatherEachDay.size() - 1).getHigh());
		assertEquals(34, weatherEachDay.get(weatherEachDay.size() - 2).getHigh());
		assertEquals(40, weatherEachDay.get(weatherEachDay.size() - 3).getHigh());
		assertEquals(43, weatherEachDay.get(weatherEachDay.size() - 4).getHigh());
		assertEquals(40, weatherEachDay.get(weatherEachDay.size() - 5).getHigh());

		assertEquals("snow", weatherEachDay.get(weatherEachDay.size() - 1).getForecast());
		assertEquals("cloudy", weatherEachDay.get(weatherEachDay.size() - 2).getForecast());
		assertEquals("partly cloudy", weatherEachDay.get(weatherEachDay.size() - 3).getForecast());
		assertEquals("snow", weatherEachDay.get(weatherEachDay.size() - 4).getForecast());
		assertEquals("snow", weatherEachDay.get(weatherEachDay.size() - 5).getForecast());

	}
}