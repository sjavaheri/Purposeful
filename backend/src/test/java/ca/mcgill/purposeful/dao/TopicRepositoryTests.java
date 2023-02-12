package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.model.Domain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.purposeful.model.Topic;

/**
 * To test the Topic CRUD repository methods.
 *
 * @author  Enzo Benoit-Jeannin
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TopicRepositoryTests {

  // Create the repository we are testing
  @Autowired
  private Topic topicRepository;

  /**
   * Clear the database before all tests
   * @author Enzo Benoit-Jeannin
   */
  @AfterEach
  public void clearDatabase() {
    topicRepository.deleteAll();
  }

  /**
   * Topic testing method which creates, populates the attributes and saves a topic in the database. Then checks that the same topic is 
   * correctly retrieved by the repository.
   * 
   * It tests the findTopicById(String id) for the topic 
   * 
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testPersistAndLoadTopicById() {

	// Initializing the topic to test
	String name = "Conv Nets"
    String id = UUID.randomUUID().toString();

    // Creating the topic and setting the fields
    Topic topic = new Topic() ;
    topic.setName(name);
    topic.setId (id)

    // Save the topic in memory
    topicRepository.save(topic);

    // Setting the address to null
    address = null;

    // Finding the topic by ID
    topic = topicRepository.findTopicById(id);

    // Make sure topic is not null
    assertNotNull(topic);

    // Checking that this is the same topic we saved earlier
    assertEquals(name, topic.getName());
    assertEquals(id, topic.getId());
  }
}
  
  
  
  
  
 