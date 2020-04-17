package com.techelevator.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techelevator.model.Course.Course;
import com.techelevator.model.Course.CourseDAO;

@Controller
public class APIMapController {


@Autowired
private CourseDAO courseDao;

@RequestMapping (path= {"/map", "/users/{currentUser}/map"}, method=RequestMethod.GET)
public String testAPI(@PathVariable(required = false) String currentUser, HttpServletRequest request, ModelMap map) {
	List<Course> courseList = courseDao.getAllCourses();	
	map.put("courses", courseList);
    String apiKey = "AIzaSyAU2WLjSJwad6UxAVzMZP9GGfuNRjqmF-4";
    map.addAttribute("request", apiKey);
    return "map";
	}

}