package junglee.greetings.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import junglee.greetings.model.EmailContent;
import junglee.greetings.model.User;
import junglee.greetings.model.UserFormData;
import junglee.greetings.security.CustomUserDetailsService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private CustomUserDetailsService userRepository;
	
	@GetMapping("/")
	public String Home() {
		return "redirect:dashboard";
	}
	
	@GetMapping("/dashboard")
	public ModelAndView AdminHome() {
		ModelAndView modelAndView = new ModelAndView();
		/*
		 * following code for admin as user view
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userRepository.findUserByEmail(auth.getName());
	    UserFormData userFormData = new UserFormData();
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("userFormData", userFormData);
	    modelAndView.addObject("fullName", "Welcome " + user.getFullname());
		/*
		 * following code for admin view & admin permissions
		 */
//	    SimpleDateFormat formatter = new SimpleDateFormat("MM");
//		Date date = new Date();
//	    int upcomingBdays = userRepository.findUpcomingBdays(formatter.format(date));
//	    int upcomingAnniversary = userRepository.findUpcomingAnniversay(formatter.format(date));
		List<UserFormData> newjoinee = userRepository.findAllNewJunglee();
		List<User> allusers = userRepository.findAllUser();
		int EmpSize = allusers.size();
		int newimp = newjoinee.size();
	    modelAndView.addObject("users", newjoinee);
	    modelAndView.addObject("EmployeeCount", EmpSize);
	    modelAndView.addObject("newEmp", newimp);
//	    modelAndView.addObject("upcomingBday",upcomingBdays);
//	    modelAndView.addObject("upcomingAnni",upcomingAnniversary);
	    modelAndView.setViewName("admin/dashboard");
	    return modelAndView;
	}
	
	
	@GetMapping("/employees")
	public ModelAndView EmployeesList() {
		
		ModelAndView modelAndView = new ModelAndView();
		List<User> users = userRepository.findAllUser();
		modelAndView.addObject("users", users);
	    modelAndView.setViewName("admin/employees");
	    return modelAndView;
	}
	
	@GetMapping("/welcomeform")
	public ModelAndView WelcomeForm(@RequestParam String email) {
		ModelAndView modelAndView = new ModelAndView();
		List<UserFormData> newjoiner = userRepository.findAllNew();
		UserFormData welcomedata = userRepository.finduserForm(email);
		if(welcomedata == null) {
			modelAndView.addObject("message", email+" has not filled welcome form");
		}
		modelAndView.addObject("welcomedata", welcomedata);
		modelAndView.addObject("newjunglees", newjoiner);
	    modelAndView.setViewName("admin/welcomeform");
	    return modelAndView;
	}
	
	@PostMapping("/welcomeformupdate")
	public String updateWelcomeForm(@RequestParam String email, @RequestParam(required = false) MultipartFile file, @RequestParam(required = false) String welcomecontent) throws IOException {
		
		if(!file.isEmpty()) {
			String fileName = email+"photo.jpg";
		    String filePath = "src/main/resources/static/image/" + fileName;
		    Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		}
		
		if(welcomecontent != null) {
			UserFormData welcomedata = userRepository.finduserForm(email);
		    welcomedata.setWelcomeWords(welcomecontent);
		    userRepository.SaveUserFormData(welcomedata);	
		}
	    return "redirect:welcomeform?email="+email;
	}
	
	@GetMapping("delete/{email}")
	public String deletePost(@PathVariable(value = "email") String email) {
		userRepository.deleteJoiners(email);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("stopGreeting/{email}")
	public String stopGreeting(@PathVariable(value = "email") String email) {
		userRepository.greetingStop(email);
		return "redirect:/admin/employees";
	}
	
	@GetMapping("/emailSettings")
	public ModelAndView BirthdayAnniversaryEmail() {
		ModelAndView modelAndView = new ModelAndView();
		EmailContent emailContent = new EmailContent();
		modelAndView.addObject("emailContent", emailContent);
		modelAndView.setViewName("admin/emailSettings");
		return modelAndView;
	}
	
	@RequestMapping(value = "/emailSettings", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid EmailContent emailContent, BindingResult bindingResult){
	
		ModelAndView modelAndView = new ModelAndView();
		
		EmailContent EmailContentExist = userRepository.findEmailContent(emailContent.getType());
		
	    if (EmailContentExist != null) {
	    	EmailContentExist.setSubject(emailContent.getSubject());
	    	EmailContentExist.setTextBody(emailContent.getTextBody());
	    	userRepository.SaveEmailContent(EmailContentExist);
	    	modelAndView.setViewName("admin/emailSettings");
	    }
	    if (bindingResult.hasErrors()) {
	        modelAndView.setViewName("admin/emailSettings");
	    } else {
	    	userRepository.SaveEmailContent(emailContent);
	        modelAndView.addObject("successMessage", "Email Content have been saved Sucessfully");
	        modelAndView.addObject("emailContent", new EmailContent());
	        modelAndView.setViewName("admin/emailSettings");
	    }
	    return modelAndView;		
	}
	
	@GetMapping("previews")
	public String Previews() {
		return "admin/Previews";
	}
		

}
