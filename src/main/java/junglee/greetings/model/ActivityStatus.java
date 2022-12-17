package junglee.greetings.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection ="ActivityStatus")
public class ActivityStatus {
	
	@Id
	private String email;
	
	private boolean welcomeMessage;
	private boolean loginPermission;
	private boolean enableMail;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(boolean welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public boolean isLoginPermission() {
		return loginPermission;
	}
	public void setLoginPermission(boolean loginPermission) {
		this.loginPermission = loginPermission;
	}
	public boolean isEnableMail() {
		return enableMail;
	}
	public void setEnableMail(boolean enableMail) {
		this.enableMail = enableMail;
	}	
}
