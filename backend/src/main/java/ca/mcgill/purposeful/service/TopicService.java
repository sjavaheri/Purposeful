package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Topic;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** Service functions of the Reaction class */
@Service
public class TopicService {

  /*
   * CRUD repos
   */

  @Autowired TopicRepository topicRepository;

  /**
   * Method to retrieve all existing domains from the database.
   *
   * @return the domains in the database.
   * @author Adam Kazma
   */
  @Transactional
  public List<Topic> getAllTopics() {

    List<Topic> topics = (List<Topic>) topicRepository.findAll();

    if (topics == null) {
      throw new GlobalException(HttpStatus.NOT_FOUND, "Could not fetch topics from database.");
    }

    return topics;
  }
}
