package com.techelevator.controller;

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

import com.techelevator.model.User.User;
import com.techelevator.model.User.UserDAO;

@Controller
public class UserController {

	private UserDAO userDAO;
	
	@Autowired
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	
	@RequestMapping(path="/users/new", method=RequestMethod.GET)
	public String displayNewUserForm(ModelMap modelHolder) {
		if( ! modelHolder.containsAttribute("user")) {
			modelHolder.addAttribute("user", new User());
		}
		return "newUser";
	}
	
	@RequestMapping(path="/users", method=RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			flash.addFlashAttribute("user", user);
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
			return "redirect:/users/new";
		}
		if (userDAO.verifyUserIsntInDatabase(user.getUserName())) {
			userDAO.saveUser(user.getUserName(), user.getPassword());
			return "redirect:/login";
		}
		return "redirect:/users/new";
	}
	
		
	@RequestMapping(path="/users/{currentUser}", method=RequestMethod.GET)
	public String logCurrentUserIn(@PathVariable("currentUser") String currentUser) {
		return "home";
	}

	
	@RequestMapping(path="/users/{currentUser}/changePassword", method=RequestMethod.GET)
	public String loadChangePasswordPage(@PathVariable("currentUser") String currentUser) {
		return "changePassword";
	}
	
	@RequestMapping(path="/users/{currentUser}/changePassword", method=RequestMethod.POST)
	public String submitChangePassword(@PathVariable("currentUser") String currentUser, 
			@RequestParam String userName, @RequestParam String password, 
			@RequestParam String newPassword) {
	userDAO.updatePassword(userName, password, newPassword);	
		return "redirect:/";
	}

	
	@RequestMapping(path="/users/{currentUser}/adminFunctions", method=RequestMethod.GET)
	public String loadAdminFunctionsPage(@PathVariable("currentUser") String currentUser) {
		return "adminFunctions";
	}
	
	@RequestMapping(path="/users/{currentUser}/adminFunctions/{role}", method=RequestMethod.POST)
	public String changeGolferRole(@PathVariable("currentUser") String currentUser, @PathVariable("role") String role,
			@RequestParam String userName, @RequestParam String myRole) {
		userDAO.updateRole(userName, myRole);
		return "redirect:/users/" + currentUser + "/adminFunctions";
	}
		
}
