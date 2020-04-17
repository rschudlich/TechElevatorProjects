package com.techelevator.model.Course;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class JDBCCourseDAO implements CourseDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCCourseDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Course> getAllCourses() {
		List <Course> courses = new ArrayList<>();
		
		String sqlSelectAllCourses = "SELECT * FROM courses ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllCourses);
		while (results.next()) {
			courses.add(mapRowToCourse(results));
		}
		return courses;
	}
	
	@Override
	public List<Course> searchCourses(@RequestParam(required = false) String searchName, @RequestParam(required = false) String searchCity) {
		List<Course> courses = new ArrayList<>();
		if (searchName == "" && searchCity != "") {
			searchName = "LKJDLKJDLKJDLKJDF";
		}
		if(searchCity == "" && searchName != "") {
			searchCity = "LDKJDLKFJSDF";
		}
		
		String sql = "SELECT * FROM courses WHERE UPPER(name) LIKE ? OR UPPER(city) LIKE ? ORDER BY name";
		if (searchName != "LKJDLKJDLKJDLKJDF" && searchCity != "LDKJDLKFJSDF") {
			sql = "SELECT * FROM courses WHERE UPPER(name) LIKE ? AND UPPER(city) LIKE ? ORDER BY name";
		}
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchName.toUpperCase() + "%", searchCity.toUpperCase() + "%");
		while (results.next()) {
			courses.add(mapRowToCourse(results));
		}
		return courses;
	}

	private Course mapRowToCourse(SqlRowSet results) {
		Course theCourse = new Course();
		
		theCourse.setCourseId(results.getInt("courseid"));
		theCourse.setName(results.getString("name"));
		theCourse.setRating(results.getDouble("rating"));
		theCourse.setSlope(results.getInt("slope"));
		theCourse.setPar(results.getInt("par"));
		theCourse.setAddress(results.getString("address"));
		theCourse.setCity(results.getString("city"));
		theCourse.setState(results.getString("state"));
		theCourse.setZip(results.getInt("zip"));
		theCourse.setLatitude(results.getDouble("latitude"));
		theCourse.setLongitude(results.getDouble("longitude"));
		return theCourse;
	}

	@Override
	public void addCourseToDatabase(Course theCourse) {
		String sqlAddCourse = "INSERT INTO courses (name, rating, slope, par, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlAddCourse, theCourse.getName(), theCourse.getRating(), theCourse.getSlope(), theCourse.getPar(), theCourse.getAddress(), theCourse.getCity(), theCourse.getState(), theCourse.getZip());		
	}
	
	@Override
	public String getCourseNameByCourseId(int id) {
		String courseName = null;
		Course course = new Course();
		String sqlSelectAllCourses = "SELECT * FROM courses WHERE courseid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllCourses, id);
		if (results.next()) {
			course = mapRowToCourse(results);
		}
		return course.getName();	
	}
	
	@Override
	public int getCourseIdByCourseName(String name) {
		int courseId = 0;
		Course course = new Course();
		String sqlSelectAllCourses = "SELECT * FROM courses WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllCourses, name);
		if (results.next()) {
			course = mapRowToCourse(results);
		}
		courseId = course.getCourseId();
		return courseId;
	}

}
