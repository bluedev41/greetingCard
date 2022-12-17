package junglee.greetings.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection ="UserFormData")
public class UserFormData {
	
	@Id
	private String email;
	
	private String fullname;
	private String birthdate;
	private String birthdayaniversary;
	private String joiningYear;
	private String joinigAnniversary;
	
	private String lastOrganisation;
	private String totalExperience;
	private String joiningAs;
	private String persionalEmail;
	private String about;
	private String permanetAddress;
	private String currentAddress;
	private String mobile;
	private String emergencyContact;
	private String gender;
	private String bloodgroup;
	private String nationality;
	private String panNumber;
	private String aadharNumber;
	private String pfAccount;
	private String BankAndBranch;
	private String AccountNumber;
	private String ifscCode;
	private String department;
	private String TsertSize;
	private String hoodiesSize;
	private boolean welcomemail;
	
	private String welcomeWords;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getPersionalEmail() {
		return persionalEmail;
	}
	public void setPersionalEmail(String persionalEmail) {
		this.persionalEmail = persionalEmail;
	}
	public String getPermanetAddress() {
		return permanetAddress;
	}
	public void setPermanetAddress(String permanetAddress) {
		this.permanetAddress = permanetAddress;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getWelcomeWords() {
		return welcomeWords;
	}
	public void setWelcomeWords(String welcomeWords) {
		this.welcomeWords = welcomeWords;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getJoiningYear() {
		return joiningYear;
	}
	public void setJoiningYear(String joiningYear) {
		this.joiningYear = joiningYear;
	}
	public String getJoinigAnniversary() {
		return joinigAnniversary;
	}
	public void setJoinigAnniversary(String joinigAnniversary) {
		this.joinigAnniversary = joinigAnniversary;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBloodgroup() {
		return bloodgroup;
	}
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getPfAccount() {
		return pfAccount;
	}
	public void setPfAccount(String pfAccount) {
		this.pfAccount = pfAccount;
	}
	public String getBankAndBranch() {
		return BankAndBranch;
	}
	public void setBankAndBranch(String bankAndBranch) {
		BankAndBranch = bankAndBranch;
	}
	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTsertSize() {
		return TsertSize;
	}
	public void setTsertSize(String tsertSize) {
		TsertSize = tsertSize;
	}
	public String getHoodiesSize() {
		return hoodiesSize;
	}
	public void setHoodiesSize(String hoodiesSize) {
		this.hoodiesSize = hoodiesSize;
	}
	public String getBirthdayaniversary() {
		return birthdayaniversary;
	}
	public void setBirthdayaniversary(String birthdayaniversary) {
		this.birthdayaniversary = birthdayaniversary;
	}
	public boolean isWelcomemail() {
		return welcomemail;
	}
	public void setWelcomemail(boolean welcomemail) {
		this.welcomemail = welcomemail;
	}
	public String getLastOrganisation() {
		return lastOrganisation;
	}
	public void setLastOrganisation(String lastOrganisation) {
		this.lastOrganisation = lastOrganisation;
	}
	public String getTotalExperience() {
		return totalExperience;
	}
	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}
	public String getJoiningAs() {
		return joiningAs;
	}
	public void setJoiningAs(String joiningAs) {
		this.joiningAs = joiningAs;
	}

}
