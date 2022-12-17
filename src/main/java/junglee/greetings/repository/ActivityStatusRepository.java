package junglee.greetings.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import junglee.greetings.model.ActivityStatus;

public interface ActivityStatusRepository extends MongoRepository<ActivityStatus, String> {
	
      List<ActivityStatus> findActivityStatusBywelcomeMessage(boolean flag);

}
