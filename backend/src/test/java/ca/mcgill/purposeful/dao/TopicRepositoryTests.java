package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.util.DatabaseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * To test the Topic CRUD repository methods.
 *
 * @author Enzo Benoit-Jeannin
 */
@SpringBootTest
public class TopicRepositoryTests {

  // Create the repository we are testing
  @Autowired private TopicRepository topicRepository;

  /** Clear the database before all tests */
  @BeforeAll
  public static void clearDatabaseBefore(@Autowired DatabaseUtil util) {
    util.clearDatabase();
  }

  /** Clear the database after each test */
  @AfterEach
  public void clearDatabaseAfter(@Autowired DatabaseUtil util) {
    util.clearDatabase();
  }

  /**
   * Topic testing method which creates, populates the attributes and saves a topic in the database.
   * Then checks that the same topic is correctly retrieved by the repository.
   *
   * <p>It tests the findTopicById(String id) for the topic
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testPersistAndLoadTopicById() {

    // Initializing the topic to test
    String name = "Conv Nets";

    // Creating the topic and setting the fields
    Topic topic = new Topic();
    topic.setName(name);

    // Save the topic in memory
    topicRepository.save(topic);

    String topicId = topic.getId();

    // Setting the address to null
    topic = null;

    // Finding the topic by ID
    topic = topicRepository.findTopicById(topicId);

    // Make sure topic is not null
    assertNotNull(topic);

    // Checking that this is the same topic we saved earlier
    assertEquals(name, topic.getName());
    assertEquals(topicId, topic.getId());
  }
}
