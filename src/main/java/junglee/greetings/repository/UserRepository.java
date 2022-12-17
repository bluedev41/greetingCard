package junglee.greetings.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import junglee.greetings.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	User findByEmail(String email);
}
