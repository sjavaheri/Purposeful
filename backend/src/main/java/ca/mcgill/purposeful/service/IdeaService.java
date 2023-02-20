package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service functions of the Idea class
 *
 * @author Wassim Jabbour
 */
@Service
public class IdeaService {

  /*
   CRUD repos
  */

  @Autowired IdeaRepository ideaRepository;

  /*
   GET functions
  */

  /**
   * Get an idea by its UUID
   *
   * @param uuid UUID of the idea
   * @return The idea with the given UUID
   * @author Wassim Jabbour
   */
  @Transactional
  public Idea getIdeaById(String uuid) {

    if (uuid == null || uuid.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid UUID. UUID cannot be empty.");
    }

    Idea idea = ideaRepository.findIdeaById(uuid);

    if (idea == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND, "Idea with UUID " + uuid + " does not exist.");
    }

    return idea;
  }

  // TODO: For the second sprint, we will implement a recommendations engine to sort the ideas!
  /**
   * Get all ideas with a set of domain names, topic names, and technology names. For now, we can
   * just return all ideas upon a (null, null, null) call. Currently just sorts from newest to
   * oldest.
   *
   * @param domainNames The list of domain names that the idea must have one of (null if no filter)
   * @param topicNames The list of topic names that the idea must have one of (null if no filter)
   * @param techNames The list of technology names that the idea must have one of (null if no
   *     filter)
   * @return The set of ideas that match all the criteria
   * @author Wassim Jabbour
   */
  @Transactional
  public List<Idea> getIdeasByAllCriteria(
      List<String> domainNames, List<String> topicNames, List<String> techNames) {

    // Retrieve all ideas
    Iterable<Idea> allIdeas = ideaRepository.findAll();

    // Check whether the request was successfull
    if (allIdeas == null) {
      throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve ideas.");
    }

    // Convert the iterable object to a list
    List<Idea> allIdeasList = new ArrayList<>();
    allIdeas.forEach(allIdeasList::add);

    // Create a list to hold the filtered ideas
    List<Idea> filteredIdeas = new ArrayList<>();

    // Filter by all criteria
    for (Idea idea : allIdeasList) {

      // 1) Check whether the idea contains 1 of the required domains
      // We do this by checking if the required domain list contains at least 1 of the idea's
      // domains
      // The following boolean will be set to true if the required domain list contains at least 1
      // domain of our idea
      boolean contains = false;
      for (Domain ideaDomain : idea.getDomains()) {
        // If null, no requirement on domain, so we skip this check
        if (domainNames == null || domainNames.contains(ideaDomain.getName())) {
          contains = true;
          break;
        }
      }
      if (!contains)
        continue; // Skip the other checks if the idea does not contain the required domain

      // 2) Check whether the idea contains 1 of the required topics
      contains = false; // Variable reuse
      for (Topic ideaTopic : idea.getTopics()) {
        if (topicNames == null || topicNames.contains(ideaTopic.getName())) {
          contains = true;
          break;
        }
      }
      if (!contains)
        continue; // Skip the other checks if the idea does not contain the required topic

      // 3) Check whether the idea contains 1 of the required technologies
      contains = false; // Variable reuse
      for (Technology ideaTech : idea.getTechs()) {
        if (techNames == null || techNames.contains(ideaTech.getName())) {
          contains = true;
          break;
        }
      }
      if (!contains)
        continue; // Skip the other checks if the idea does not contain the required technology

      // If we reach this point, the idea matches all the criteria
      filteredIdeas.add(idea);
    }

    // Check whether any ideas match the criteria
    if (filteredIdeas.isEmpty()) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND,
          "No ideas match the given criteria. Please try again with different criteria.");
    }

    // Sort the ideas from newest to oldest
    // We flip the order so that the newest (bigger date) comes first
    filteredIdeas.sort((idea1, idea2) -> idea2.getDate().compareTo(idea1.getDate()));

    // Return the list of ideas otherwise
    return filteredIdeas;
  }

  /*
   CREATE functions
  */

  /*
   DELETE functions
  */

  /*
   UPDATE functions
  */

  /*
   DELETE functions
  */
}
