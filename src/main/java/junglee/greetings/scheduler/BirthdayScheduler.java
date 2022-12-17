package junglee.greetings.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import junglee.greetings.imageSetup.BirthdayImageSetup;
import junglee.greetings.model.UserFormData;
import junglee.greetings.security.CustomUserDetailsService;

@Component
public class BirthdayScheduler {
	
	@Autowired
	private BirthdayImageSetup birthdayImageSetup;
	
	@Autowired
	private CustomUserDetailsService userRepository;
	
	//"0 0 10 * * ?" for everyday at 10
	//"0 * * ? * *" for every minutes
	@Scheduled(cron = "0 * * ? * *")
	public void ScheduleMail() {
		System.out.println("entered into birthday scheduling function ");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
	    Date date = new Date();
	    
	    List<UserFormData> users = userRepository.findAllBirthdays(formatter.format(date));
	    if(users.size() > 0) {
	    	birthdayImageSetup.overlayImagesMutipleBDayN(users);		
	        
	    }
	}

}
