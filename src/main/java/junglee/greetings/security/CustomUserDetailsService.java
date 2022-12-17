package junglee.greetings.security;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import junglee.greetings.mail.SendMail;
import junglee.greetings.model.ActivityStatus;
import junglee.greetings.model.EmailContent;
import junglee.greetings.model.User;
import junglee.greetings.model.UserFormData;
import junglee.greetings.repository.ActivityStatusRepository;
import junglee.greetings.repository.EmailContentRepository;
import junglee.greetings.repository.UserFormDataRepository;
import junglee.greetings.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ActivityStatusRepository activityStatusRepository;
	
	@Autowired
	private EmailContentRepository emailContentRepository;
	
	@Autowired
	private UserFormDataRepository userdata;
	
	@Autowired
	SendMail sendmail;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public User findUserByEmail(String email) {
	    return userRepository.findByEmail(email);
	}
	
	public EmailContent findEmailContent(String type) {
		return emailContentRepository.findByType(type);
	}
	
	public List<UserFormData> findAllBirthdays(String aniversary){
		return userdata.findUserFormDataBybirthdayaniversary(aniversary);
	}
	
	public List<UserFormData> findJoiningAnniversary(String joiningAnniversary) {
		return userdata.findUserFormDataByjoinigAnniversary(joiningAnniversary);
	}
	
	public UserFormData getWelcomeData(String email) { 
        return userdata.findById(email).get(); 
    }
	
	public void saveUser(User user) throws MessagingException {
		String pass=String.valueOf(generateRandomPassword(7));
	    user.setPassword(bCryptPasswordEncoder.encode(pass));
	    user.setRole("USER");
	    User saveduser = userRepository.save(user);
	    if(saveduser != null) {
	    	String subject = "Junglee UserId & Password";
	    	String body = "<p>Your Username is: "+"<b>"+user.getEmail()+"</b>"+"<br/>"+"<p>And password: "+"<b>"+pass+"</b>";
	    	sendmail.SendWithoutAttachement("rohitsalwan2020@gmail.com",user.getEmail(),subject, body);
	    	System.out.println("user data is saved & password mail is sent to user");
	    }
	    else {
	    	System.out.println("user data is not saved into database");
	    } 
	}
	private char[] generateRandomPassword(int len) {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String Small_chars = "abcdefghijklmnopqrstuvwxyz"; 
        String numbers = "0123456789"; 
        
        String values = Capital_chars + Small_chars + numbers ; 
        
        Random rndm_method = new Random(); 
        
        char[] password = new char[len]; 
  
        for (int i = 0; i < len; i++) 
        { 
            password[i] = values.charAt(rndm_method.nextInt(values.length())); 
  
        } 
        return password; 
	}
	
	public void SaveEmailContent(EmailContent emailContent) {
		emailContentRepository.save(emailContent);
	}
	
	public void SaveUserFormData(UserFormData userformdata){
		SimpleDateFormat dateMonth = new SimpleDateFormat("dd-MM");
		SimpleDateFormat Year = new SimpleDateFormat("YYYY");
	    Date date = new Date();
		userformdata.setJoinigAnniversary(dateMonth.format(date));
		userformdata.setJoiningYear(Year.format(date));
		userformdata.setWelcomemail(false);
		userdata.save(userformdata);
    }

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

	    User user = userRepository.findByEmail(email);
	    if(user != null) {
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new SimpleGrantedAuthority(user.getRole()));
	        return buildUserForAuthentication(user, authorities);
	    } else {
	        throw new UsernameNotFoundException("username not found");
	    }
	}
	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	public UserFormData findUserFormdata(String email) {
		return userdata.findByEmail(email);
	}

	public void saveActivityStatus(ActivityStatus activityStatus) {
		activityStatus.setEnableMail(true);
		activityStatus.setLoginPermission(true);
		activityStatus.setWelcomeMessage(false);
		activityStatusRepository.save(activityStatus);
	}

	public void deleteJoiners(String email) {
		userRepository.deleteById(email);
		activityStatusRepository.deleteById(email);
		userdata.deleteById(email);
		String imagepath = "src/main/resources/static/image/"+email+"photo.jpg";
		File file = new File(imagepath);
		file.delete();
	}

	public List<UserFormData> findAllNewJunglee() {
		return userdata.findAll();
	}

	public UserFormData finduserForm(String email) {
		return userdata.findByEmail(email);
	}

	public List<UserFormData> findAllNew() {
		return userdata.findUserFormDataBywelcomemail(false);
	}

	public void greetingStop(String email) {
		userRepository.deleteById(email);
		activityStatusRepository.deleteById(email);
		userdata.deleteById(email);
		String imagepath = "src/main/resources/static/image/"+email+"photo.jpg";
		File file = new File(imagepath);
		file.delete();
		
	}

//	public int findUpcomingBdays(String bdayMonth) {
//		List<UserFormData> users = userdata.findUserByBdayMonth(bdayMonth);
//		int count = 0;
//		SimpleDateFormat formatter = new SimpleDateFormat("dd");
//		Date date = new Date();
//		int	currDay=Integer.parseInt(formatter.format(date));
//		for(int i=0;i<users.size();i++) {
//			String bithdate[] = users.get(0).getBirthdayaniversary().trim().split("-");
//			int userDay=Integer.parseInt(bithdate[0]);
//			if(userDay>currDay) {
//				//upcomingBdayList.add(users.get(i).getBdayDay());
//				count++;
//			}
//		}
//		return count;
//	}

//	public int findUpcomingAnniversay(String joiningMonth) {
//		List<UserFormData> users = userdata.findUserByJoiningMonth(joiningMonth);
//		//List<UserFormData> upcomingAnniversaryList = new ArrayList<String>();
//		int count = 0;
//		SimpleDateFormat formatter = new SimpleDateFormat("dd");
//		Date date = new Date();
//		int	currDay=Integer.parseInt(formatter.format(date));
//		for(int i=0;i<users.size();i++) {
//			int userDay=Integer.parseInt(formatter.format(users.get(0).getJoinigAnniversary()));
//			if(userDay>currDay) {
//				//upcomingAnniversaryList.add(users.get(i).getJoiningDay());
//				count++;
//			}
//		}
//		return count;
//	}

}
