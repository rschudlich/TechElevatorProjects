package com.techelevator.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.Course.Course;
import com.techelevator.model.Course.CourseDAO;


@Controller
public class HomeController {
	
	@Autowired
	private CourseDAO courseDao;
	
	@RequestMapping(path="/")
	public String displayHomePage() {
		return "home";
	}
	
	@RequestMapping(path="/users/{currentUser}/")
	public String displayHomePageUserLoggedIn() {
		return "home";
	}
	
	

	@RequestMapping(path= {"/courseSearch", "/users/{currentUser}/courseSearch"}, method=RequestMethod.GET)
	public String displayCourse(@PathVariable(required = false) String currentUser, HttpServletRequest request, ModelMap map) {
		List<Course> course = courseDao.getAllCourses();
		map.put("allCourses", course);
	    String apiKey = "AIzaSyAU2WLjSJwad6UxAVzMZP9GGfuNRjqmF-4";
	    map.addAttribute("request", apiKey);
		return "courseSearch";
	}
	
	@RequestMapping(path= {"/courseSearchResults", "/users/{currentUser}/courseSearchResults"})
	public String displayCourseSearch(@PathVariable(required = false) String currentUser, 
			@RequestParam(required = false) String searchName,
			@RequestParam(required = false) String searchCity, 
			@RequestParam(required = false) String request, ModelMap map) {
				List<Course> course = courseDao.searchCourses(searchName, searchCity);
				//String request = (String) map.get("request");
				map.put("allCourses", course);
				map.addAttribute("request", request);
				return "courseSearch";
			}

	
	
	@RequestMapping(path="/users/{currentUser}/addCourse", method = RequestMethod.GET)
	public String displayAddCourse(@PathVariable("currentUser") String currentUser, ModelMap map) {
		if (!map.containsAttribute("course")) {
			map.put("course", new Course());
		}
		return "addCourse";
	}
	
	@RequestMapping (path = "/users/{currentUser}/addCourse", method = RequestMethod.POST)
	public String submitCourse(@Valid @ModelAttribute Course course, @PathVariable("currentUser") String currentUser, BindingResult result, RedirectAttributes flash) {
		
		flash.addFlashAttribute("course", course);
		
		if (result.hasErrors()) {
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "course", result);
			return "redirect:/addCourse";
		}
		courseDao.addCourseToDatabase(course);
		return "redirect:/users/{currentUser}/addCourseConfirmation";
	}
	
	@RequestMapping (path = "/users/{currentUser}/addCourseConfirmation", method = RequestMethod.GET)
	public String courseConfirmation( @PathVariable("currentUser") String currentUser){
		return "addCourseConfirmation";
	}
	
}
	