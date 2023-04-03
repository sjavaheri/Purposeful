package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Topic;
import org.springframework.data.repository.CrudRepository;

/** Repository for Topic */
public interface TopicRepository extends CrudRepository<Topic, Integer> {

  /**
   * Find a topic by its id
   * 
   * @param id the id of the topic
   * @return the topic
   */
  Topic findTopicById(String id);

  /**
   * Find a topic by its name
   * 
   * @param name the name of the topic
   * @return the topic
   */
  Topic findTopicByName(String name);
}
