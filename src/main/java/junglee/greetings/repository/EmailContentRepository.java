package junglee.greetings.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import junglee.greetings.model.EmailContent;

public interface EmailContentRepository extends MongoRepository<EmailContent, String> {
	
	EmailContent findByType(String type);
	
}
