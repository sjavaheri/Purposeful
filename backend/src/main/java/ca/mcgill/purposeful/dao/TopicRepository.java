package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Topic;
import org.springframework.data.repository.CrudRepository;

/** Repository for Topic */
public interface TopicRepository extends CrudRepository<Topic, Integer> {

  Topic findTopicById(String id);
}
