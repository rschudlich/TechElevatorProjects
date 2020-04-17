package com.techelevator.npgeek.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.ParkDao;
import com.techelevator.npgeek.model.SurveyResult;
import com.techelevator.npgeek.model.SurveyResultDao;
import com.techelevator.npgeek.model.Weather;
import com.techelevator.npgeek.model.WeatherDao;

@Controller
public class HomeController { 

	@Autowired
	private ParkDao parkDao;
	
	@Autowired
	private WeatherDao weatherDao;
	
	@Autowired
	private SurveyResultDao surveyDao;
	
	
	@RequestMapping (path = {"/", "/homePage"}, method = RequestMethod.GET)
	private String displayHomePage(ModelMap map) {
		List<Park> parks = parkDao.getAllParks();
		map.put("parks", parks);
		return "home";
	}
	
	@RequestMapping (path = "/parkDetails", method = RequestMethod.GET)
	private String displayParkDetails(@RequestParam String parkCode, @RequestParam Character tempType, ModelMap map) {
		Park myPark = parkDao.getParkByParkCode(parkCode);
		List<Weather> weatherList = weatherDao.getWeatherByParkCode(parkCode);
		
		
		if (tempType == 'C') {
			for (int x = 0; x < weatherList.size(); x++) {
				weatherList.get(x).setHigh((convertFToC(weatherList.get(x).getHigh())));
				weatherList.get(x).setLow(convertFToC(weatherList.get(x).getLow()));
			}
		}
		
		for (int x = 0; x < weatherList.size(); x++) {
			weatherList.get(x).setDay(weatherList.get(x).getFiveDayForecastValue());
		}
		
		map.put("myPark", myPark);
		map.put("weatherList", weatherList);
		return "parkDetail";
	}
	
	private int convertFToC(int temp) {
		return ((temp - 32) * 5) / 9;
	}
	
	@RequestMapping (path = "/survey", method = RequestMethod.GET)
	private String displaySurvey() {
		return "survey";
	}
	
	@RequestMapping (path = "/survey", method = RequestMethod.POST)
	private String processSurvey (
			@Valid @ModelAttribute SurveyResult surveyResult,
			BindingResult bindingResult,
			RedirectAttributes flash,
			@RequestParam String parkCode, 
			@RequestParam String email,
			@RequestParam String state,
			@RequestParam String activityLevel,
			ModelMap map) {
		SurveyResult result = new SurveyResult ();
		result.setParkCode(parkCode);
		result.setEmail(email);
		result.setState(state);
		result.setActivityLevel(activityLevel);
		surveyDao.save(result); 
		
		flash.addFlashAttribute("surveyResult", surveyResult);
		
		if (bindingResult.hasErrors()) {
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "", result);
			return "redirect:/survey";
		}
		return "redirect:/surveyResults";
	}
	
	@RequestMapping (path = "/surveyResults", method = RequestMethod.GET)
	private String displaySurveyResults(ModelMap map) {
		List<Park> surveyResults = surveyDao.getAllSurveyCounts();
		map.put("surveyResults", surveyResults);
		return "surveyResults";
	}
}
