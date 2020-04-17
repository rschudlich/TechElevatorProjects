package com.techelevator.model.Course;

import java.util.List;

import org.springframework.ui.ModelMap;

public interface CourseDAO {

	public List<Course> getAllCourses();
	
	public List<Course> searchCourses(String searchName, String searchCity);

	public void addCourseToDatabase(Course theCourse);

	public String getCourseNameByCourseId(int id);

	public int getCourseIdByCourseName(String name);


}
