package junglee.greetings.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import junglee.greetings.imageSetup.WelcomeImageSetup;
import junglee.greetings.model.UserFormData;
import junglee.greetings.security.CustomUserDetailsService;

@Component
public class WelcomeMessageScheduler {
	
	@Autowired
	private CustomUserDetailsService userRepository;	
	@Autowired
	private WelcomeImageSetup welcomeImageSetup;
	
	@Scheduled(cron = "0 * * ? * *")
	public void scheduleWelcomeMessage() {
		System.out.println("entered into welcome mail scheduling function ");
		List<UserFormData> newjoinee = userRepository.findAllNew();
		if(newjoinee.size() > 0) {
			welcomeImageSetup.overlayImagesMutipleNewJoinee(newjoinee);			
		}
	}
}
