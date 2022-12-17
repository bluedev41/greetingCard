package junglee.greetings.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import junglee.greetings.model.User;
import junglee.greetings.model.UserFormData;
import junglee.greetings.security.CustomUserDetailsService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private CustomUserDetailsService userRepository;
	
	
	@GetMapping("/")
	public String Home() {
		return "user/dashboard";
	}
	
	@GetMapping("/dashboard")
	public ModelAndView userHome() {
		
		ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userRepository.findUserByEmail(auth.getName());
	    UserFormData userFormData = new UserFormData();
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("userFormData", userFormData);
	    modelAndView.addObject("fullName", "Welcome " + user.getFullname());
	    modelAndView.setViewName("user/dashboard");
	    return modelAndView;
	}
	
	@RequestMapping(value = "/welcomeform", method = RequestMethod.POST)
	public String saveWelcomeForm(@Valid UserFormData userFormData, MultipartFile file, BindingResult bindingResult) throws IOException{
		
	    if (bindingResult.hasErrors()) {
	    	return "redirect:dashboard";
	    } else {
	    	
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	/*
		     * image name as email+photo.jpg
		     */
	    	String fileName = auth.getName()+"photo.jpg";
		    String filePath = "src/main/resources/static/image/" + fileName;
		    Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		    
		    /*
		     * converting date formate yyyy-mm-dd to dd-yy-yyyy
		     * and set anniversary date as dd-yy
		     */
		    String birthdate = userFormData.getBirthdate();
		    String datearray[] = birthdate.trim().split("-");
		    String birthdayaniversary = datearray[2]+"-"+datearray[1];
		    //sting up birthanniversary and welcome message
		    userFormData.setBirthdayaniversary(birthdayaniversary);
		    userFormData.setWelcomeWords(userFormData.getAbout());
	    	userRepository.SaveUserFormData(userFormData);
	    	return "redirect:dashboard";
	    }	
	}
	

}
