package com.techelevator.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.techelevator.model.Course.Course;
import com.techelevator.model.Course.CourseDAO;
import com.techelevator.model.League.League;
import com.techelevator.model.League.LeagueDAO;
import com.techelevator.model.Score.Score;
import com.techelevator.model.Score.ScoreDAO;
import com.techelevator.model.Team.Team;
import com.techelevator.model.Team.TeamDAO;
import com.techelevator.model.TeeTime.TeeTime;
import com.techelevator.model.TeeTime.TeeTimeDAO;
import com.techelevator.model.User.UserDAO;

@Controller
public class DashboardController {
	
	@Autowired
	private CourseDAO courseDao;
	
	@Autowired
	private ScoreDAO scoreDao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private TeeTimeDAO teeTimeDao;
	
	@Autowired
	private LeagueDAO leagueDao;
	
	@Autowired
	private TeamDAO teamDao;
	
	
	@RequestMapping(path="/users/{currentUser}/dashboard")
	public String displayDashboard(@PathVariable("currentUser") String currentUser, ModelMap map) {
		List<League> league = leagueDao.getAllLeaguesByUserId(userDao.getIdByUserName(currentUser));
		List<Team> team = teamDao.getTeamsByUserId(userDao.getIdByUserName(currentUser));
		for(int x = 0; x < team.size(); x++) {
			int ranking = teamDao.getRanking(team.get(x).getLeagueId(), userDao.getIdByUserName(currentUser));
			team.get(x).setRanking(ranking);
			List<Integer> scoresByLeague = scoreDao.getAllScoresByLeagueIdAndUserId(team.get(x).getLeagueId(), userDao.getIdByUserName(currentUser));
			double scoreTotal = 0;
			int count = 0;
			for (int i = 0; i < scoresByLeague.size(); i++) {
				count++;
				scoreTotal = scoreTotal + scoresByLeague.get(i);
			}
			double averageScore = scoreTotal / count;
			if(scoresByLeague.size() == 0) {
				averageScore = 0;
			}
			team.get(x).setAverageScore(averageScore);
		}
		List <Score> scores = scoreDao.getAllScoresByUserId(userDao.getIdByUserName(currentUser));
		for(int x = 0; x < scores.size(); x++) {
			String courseName = courseDao.getCourseNameByCourseId(scores.get(x).getCourseId());
			scores.get(x).setCourseName(courseName);
			String dateString = scoreDao.getDateFromScoreId(scores.get(x).getScoreId());
			int year = Integer.parseInt(dateString.substring(0, 4));
			int month = Integer.parseInt(dateString.substring(5, 7));
			int day = Integer.parseInt(dateString.substring(8, 10));
			LocalDate myDate = LocalDate.of(year, month, day);
			scores.get(x).setDate(myDate);
			scores.get(x).setDateString(turnDateIntoString(scores.get(x).getDate()));
		}
		LocalDate today = LocalDate.now();
		String todayString = turnDateIntoString(today);
		
		List<Double> differentialList = new ArrayList<>();
		
		for(int x = 0; x < scores.size(); x++) {
			Course myCourse = scoreDao.getCourseRatingAndSlopeFromScoreID(scores.get(x).getScoreId());
			double differential = calculateDifferential(scores.get(x).getScore(), myCourse.getRating(), myCourse.getSlope());
			differentialList.add(differential);
		}
		Collections.sort(differentialList);
		double differentialAverage = differentialTotal(differentialList);
		double handicap = calculateHandicap(differentialAverage);
		double newHandicap = 0;
		
		if (scores.size() >= 5) {
			BigDecimal bd = BigDecimal.valueOf(handicap);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			newHandicap = bd.doubleValue();
			map.put("handicap", newHandicap);
		}else {
			map.put("handicap", "You must have at least 5 scores for a handicap");
		}
		
		
		
		List<TeeTime> teeTimes = teeTimeDao.getTeeTimesByGolferIdPastToday(userDao.getIdByUserName(currentUser));
		map.put("leagues", league);
		map.put("teams", team);
		map.put("teeTimes", teeTimes);
		map.put("date", todayString);
		map.put("scores", scores);
		return "dashboard";
	}
	
	private double calculateHandicap(double differentialAverage) {
		double handicap = differentialAverage * 0.96;
		return handicap;
	}
	
	private double differentialTotal(List<Double> differentialList) {
		double differentialTotal = 0;
		int differentialCount = 0;
		if (differentialList.size() >= 20) {
			for(int x = 0; x < 10; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 19) {
			for(int x = 0; x < 9; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 18) {
			for(int x = 0; x < 8; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 17) {
			for(int x = 0; x < 7; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 16 || differentialList.size() == 15) {
			for(int x = 0; x < 6; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 14 || differentialList.size() == 13) {
			for(int x = 0; x < 5; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 12 || differentialList.size() == 11) {
			for(int x = 0; x < 4; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 10 || differentialList.size() == 9) {
			for(int x = 0; x < 3; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 8 || differentialList.size() == 7) {
			for(int x = 0; x < 2; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}else if (differentialList.size() == 6 || differentialList.size() == 5) {
			for(int x = 0; x < 1; x++) {
				differentialTotal += differentialList.get(x);
				differentialCount++;
			}
		}
		double differentialAverage = differentialTotal / differentialCount;
		return differentialAverage;
	}
	
	private double calculateDifferential(int score, double courseRating, int slope) {
		double differential = ((score - courseRating) * 113) / slope;
		return differential;
	}
	
	private String turnDateIntoString(LocalDate date) {
		String todayString = date.toString();
		String buildString = "";
	
		if (todayString.substring(5, 7).contentEquals("01")) {
			buildString = buildString + "January";
		}else if (todayString.substring(5, 7).contentEquals("02")) {
			buildString = buildString + "February";
		}else if (todayString.substring(5, 7).contentEquals("03")) {
			buildString = buildString + "March";
		}else if (todayString.substring(5, 7).contentEquals("04")) {
			buildString = buildString + "April";
		}else if (todayString.substring(5, 7).contentEquals("05")) {
			buildString = buildString + "May";
		}else if (todayString.substring(5, 7).contentEquals("06")) {
			buildString = buildString + "June";
		}else if (todayString.substring(5, 7).contentEquals("07")) {
			buildString = buildString + "July";
		}else if (todayString.substring(5, 7).contentEquals("08")) {
			buildString = buildString + "August";
		}else if (todayString.substring(5, 7).contentEquals("09")) {
			buildString = buildString + "September";
		}else if (todayString.substring(5, 7).contentEquals("10")) {
			buildString = buildString + "October";
		}else if (todayString.substring(5, 7).contentEquals("11")) {
			buildString = buildString + "November";
		}else if (todayString.substring(5, 7).contentEquals("12")) {
			buildString = buildString + "December";
		}
		buildString = buildString + " " + todayString.substring(8, 10) + ", " + todayString.substring(0, 4);
		
		return buildString;
	}

	
	@RequestMapping(path = "/users/{currentUser}/scheduleTeeTime", method = RequestMethod.GET)
	public String scheduleTeetime(@PathVariable("currentUser") String currentUser, ModelMap map) {
		LocalTime now = LocalTime.now();
		LocalDate today = LocalDate.now();
		if (now.isAfter(LocalTime.of(17, 0))) {
			today = today.plusDays(1);
		}
		List<LocalDate> threeWeeks = new ArrayList <> ();
		for (int x = 0; x <= 21; x++) {
			threeWeeks.add(today);
			today = today.plusDays(1);
		}
		List<Course> course = courseDao.getAllCourses();
		map.put("dates", threeWeeks);
		map.put("allCourses", course);
		return "scheduleTeeTime";		
	}

	@RequestMapping (path = "/users/{currentUser}/scheduleTeeTime", method = RequestMethod.POST)
	public String submitTeeTimeDateAndCourse(@PathVariable("currentUser") String currentUser, 
			@RequestParam int course, @RequestParam String date, HttpSession session) {
		List <LocalDateTime> availableTimes = teeTimeDao.getTeeTimesByCourse(course, date);
		session.setAttribute("availableTimes", availableTimes);
		session.setAttribute("course", course);
		session.setAttribute("date", date);
		return "redirect:/users/{currentUser}/teeTimeSheet";
	}
	
	
	@RequestMapping (path = "/users/{currentUser}/teeTimeSheet", method = RequestMethod.GET)
	public String displayTeeTimeSheet(@PathVariable("currentUser") String currentUser, HttpSession session, ModelMap map){
		session.getAttribute("availableTimes");
		session.getAttribute("course");
		session.getAttribute("date");
		int playerId = userDao.getIdByUserName(currentUser);
		List <TeeTime> userBookings = teeTimeDao.getTeeTimesByGolferIdPastToday(playerId);
		List <String> bookings = new ArrayList<>();
		for (int x=0; x<userBookings.size();x++) {
			bookings.add(userBookings.get(x).getTime().toLocalDate().toString());
		}
		map.put("bookings", bookings);
		
		
		return "teeTimeSheet";
	}
	
	@RequestMapping (path = "/users/{currentUser}/teeTimeSheet", method = RequestMethod.POST)
	public String submitTeeTimeSheet(@PathVariable("currentUser") String currentUser, 
			@RequestParam String times, @RequestParam int golfers, @RequestParam int course) {
		
		int playerId = userDao.getIdByUserName(currentUser);
		TeeTime booking = new TeeTime();
		booking.setCourseId(course);
		booking.setNumGolfers(golfers);
		LocalDate date = teeTimeDao.getDateFromString(times);
		LocalTime time = teeTimeDao.getTimeFromString(times);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		booking.setTime(dateTime);
		
		teeTimeDao.saveTeeTime(booking, playerId);
		return "redirect:/users/{currentUser}/teeTimeConfirmation";
	}

	@RequestMapping (path = "/users/{currentUser}/teeTimeConfirmation", method = RequestMethod.GET)
	public String displayTeeTimeConfirmation( @PathVariable("currentUser") String currentUser) {
		return "teeTimeConfirmation";
	}
	
	@RequestMapping(path="/users/{currentUser}/addScore", method=RequestMethod.GET)
	public String displayAddScore(@PathVariable("currentUser") String currentUser, ModelMap map){
		List<Course> course = courseDao.getAllCourses();
		map.put("allCourses", course);		
		return "addScore";
	}	
	
	@RequestMapping (path = "/users/{currentUser}/addScore", method = RequestMethod.POST)
	public String submitScore(@PathVariable("currentUser") String currentUser, 
			@RequestParam String name, @RequestParam int score, @RequestParam(required = false) String date) {
		Score myScore = new Score();
		TeeTime myTeeTime = new TeeTime();
		int courseId = courseDao.getCourseIdByCourseName(name);
		int playerId = userDao.getIdByUserName(currentUser);
		LocalDate now = LocalDate.now();
		String buildDate = "";
		buildDate = buildDate + date.substring(6, 10) + "-" + date.substring(0, 2) + "-" + date.substring(3, 5);
		LocalDate aDate = LocalDate.parse(buildDate);
		
		if(aDate.isBefore(now)) {
			if(date != "") {
				int month = Integer.parseInt(date.substring(0, 2));
				int day = Integer.parseInt(date.substring(3, 5));
				int year = Integer.parseInt(date.substring(6, 10));
				LocalDate myDate = LocalDate.of(year, month, day);
				LocalDateTime theDate = LocalDateTime.of(myDate, LocalTime.of(0, 0));
				myTeeTime.setTime(theDate);
				myTeeTime.setNumGolfers(1);
				myTeeTime.setCourseId(courseId);
				teeTimeDao.saveTeeTime(myTeeTime, playerId);
				int teeTimeId = teeTimeDao.getLastTeeTimeId();
				myScore.setTeeTimeId(teeTimeId);
			}else {
				//this is where we will pull data from tee times table
				myScore.setTeeTimeId(1);
			}
			myScore.setCourseId(courseId);
			myScore.setId(playerId);
			myScore.setScore(score);
			scoreDao.saveScore(myScore);		
		}
		
		return "redirect:/users/{currentUser}/dashboard";
	}

}
