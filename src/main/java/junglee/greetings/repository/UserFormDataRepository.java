package junglee.greetings.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import junglee.greetings.model.UserFormData;

public interface UserFormDataRepository extends MongoRepository<UserFormData, String> {
	
	UserFormData findByEmail(String email);
	List<UserFormData> findUserFormDataBybirthdayaniversary(String birthdayaniversary);
	List<UserFormData> findUserFormDataByjoinigAnniversary(String joiningAnniversary);
	List<UserFormData> findUserFormDataBywelcomemail(boolean welcomemail);
	
//	List<UserFormData> findUserByBdayMonth(String bdayMonth);
//	List<UserFormData> findUserByJoiningMonth(String joiningMonth);
}
