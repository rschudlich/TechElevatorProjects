package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCWeatherDao implements WeatherDao {

private JdbcTemplate jdbcTemplate;

@Autowired
public JDBCWeatherDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
}

private Weather mapRowToWeather(SqlRowSet results) {
    Weather weatherInPark = new Weather();

    weatherInPark.setParkCode(results.getString("parkCode").toLowerCase());
    weatherInPark.setFiveDayForecastValue(results.getInt("fiveDayForecastValue"));
    weatherInPark.setLow(results.getInt("low"));
    weatherInPark.setHigh(results.getInt("high"));
    weatherInPark.setForecast(results.getString("forecast"));
    
    return weatherInPark ;
}



@Override
public List<Weather> getWeatherByParkCode(String parkCode) {
    
    List<Weather> allWeatherInPark = new ArrayList<>();
    String sqlGetAllWeatherInPark = "SELECT parkcode, fivedayforecastvalue, low, high, forecast "
            + "FROM weather "
            + "WHERE parkcode = UPPER (?) "
            + "ORDER BY fivedayforecastvalue";

    SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllWeatherInPark, parkCode);
    while (results.next()) {
        allWeatherInPark.add(mapRowToWeather(results)); 
    }
    
    return allWeatherInPark;
    
}
}