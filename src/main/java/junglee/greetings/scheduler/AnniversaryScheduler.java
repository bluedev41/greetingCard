package junglee.greetings.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import junglee.greetings.imageSetup.AnniversaryImageSetup;
import junglee.greetings.model.UserFormData;
import junglee.greetings.security.CustomUserDetailsService;

@Component
public class AnniversaryScheduler {	
	@Autowired
	AnniversaryImageSetup anniversaryImageSetup;
	
	@Autowired
	private CustomUserDetailsService userRepository;
	
	@Scheduled(cron = "0 * * ? * *")
	public void SchedulerAnniversary() {
		
		System.out.println("entered into Anniversary scheduling function ");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
	    Date date = new Date();
	    
	    List<UserFormData> users = userRepository.findJoiningAnniversary(formatter.format(date));
	    
	    if(users.size() > 0) {
	    	 anniversaryImageSetup.overlayImagesMutipleAnniversaryN(users);
	    }
	}
}
