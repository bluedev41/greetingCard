package junglee.greetings.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import junglee.greetings.model.ActivityStatus;
import junglee.greetings.model.User;
import junglee.greetings.security.CustomUserDetailsService;

@Controller
public class MainController {
	
	@Autowired
	private CustomUserDetailsService userService;
	
	@RequestMapping(value = {"/","/home","/login"}, method = RequestMethod.GET)
	public ModelAndView login() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("login");
	    return modelAndView;
	}
		
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
	    ModelAndView modelAndView = new ModelAndView();
	    User user = new User();
	    ActivityStatus activityStatus = new ActivityStatus();
	    modelAndView.addObject("activityStatus",activityStatus);
	    modelAndView.addObject("user", user);
	    modelAndView.setViewName("signup");
	    return modelAndView;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, @Valid ActivityStatus activityStatus, BindingResult bindingResult) throws MessagingException{
		
		ModelAndView modelAndView = new ModelAndView();
	    User userExists = userService.findUserByEmail(user.getEmail());
	    if (userExists != null) {
	    	modelAndView.addObject("fail_message", "User is already registered, please check your email id get username & password");
	        modelAndView.setViewName("signup");
	    }
	    if (bindingResult.hasErrors()) {
	        modelAndView.setViewName("signup");
	    } else {
	        userService.saveUser(user);
	        userService.saveActivityStatus(activityStatus);
	        modelAndView.addObject("sucsess_message", "User has been registered successfully, Check your mail for UserId and Password");
	        modelAndView.addObject("user", new User());
	        modelAndView.setViewName("signup");
	    }
	    return modelAndView;
		
	}
}
