package com.techelevator;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.techelevator.npgeek.model.Weather;

public class WeatherTest {

	@Test
	public void test_to_check_date_1() {
		Weather day = new Weather();
		day.setDay(1);
		assertEquals("Today", day.getDay());
		
	}
	 
	@Test
	public void test_to_check_date_2() {
		Weather day = new Weather();

		day.setDay(2);
		assertEquals("Tomorrow", day.getDay());
	}
	
	@Test
	public void test_to_check_date_3() {
		Weather day = new Weather();
		day.setDay(3);
		LocalDate date = LocalDate.now();
		date = date.plusDays(2);
		String day3 = date.getDayOfWeek().toString().toLowerCase();
		assertEquals(day3.substring(0, 1).toUpperCase() + day3.substring(1), day.getDay());
		
	}

	@Test
	public void test_to_check_date_4() {
		Weather day = new Weather();	
		day.setDay(4);
		LocalDate date = LocalDate.now();
		date = date.plusDays(3);
		String day4 = date.getDayOfWeek().toString().toLowerCase();
		assertEquals(day4.substring(0, 1).toUpperCase() + day4.substring(1), day.getDay());
		
	}

	@Test
	public void test_to_check_date_5() {
		Weather day = new Weather();		
		day.setDay(5);
		LocalDate date = LocalDate.now();
		date = date.plusDays(4);
		String day5 = date.getDayOfWeek().toString().toLowerCase();
		assertEquals(day5.substring(0, 1).toUpperCase() + day5.substring(1), day.getDay());
		
	} 
	 
}