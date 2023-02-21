package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.dao.ReactionRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service functions of the Reaction class
 */
@Service
public class ReactionService {

  /*
   CRUD repos
  */

  @Autowired
  ReactionRepository reactionRepository;
  @Autowired
  RegularUserRepository regularUserRepository;

  /*
   Service functions
  */
  IdeaService ideaService;

  /**
   * Method to create a reaction and fill its appropriate attributes if it doesn't exist. The method
   * will remove an existing reaction if it exists already
   *
   * @param date         date of the reaction post
   * @param reactionType type of reaction
   * @param idea_id      id of the idea being reacted to
   * @param user_id      id of the regular user that reacts
   * @return the reaction that has been created
   */
  @Transactional
  public Reaction react(Date date, ReactionType reactionType, String idea_id,
      String user_id) {

    // check if a previous reaction exists (in which case it gets deleted by reacting a second time)
    Reaction previousReaction = this.getReactionByIdeaAndRegularUser(idea_id, user_id);

    if (previousReaction != null) {
      this.removeReaction(previousReaction.getId());
      return null;
    } else {
      Reaction reaction = new Reaction();
      reaction.setDate(date);
      reaction.setReactionType(reactionType);
      reaction.setIdea(ideaService.getIdeaById(idea_id));
      reaction.setRegularUser(regularUserRepository.findRegularUserById(user_id));
      return reaction;
    }
  }

  /**
   * Get a reaction by its id
   *
   * @param uuid UUID of the reaction
   * @return The reaction with the given UUID
   * @author Athmane Benarous
   */
  @Transactional
  public Reaction getReactionById(String uuid) {

    if (uuid == null || uuid.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid UUID. UUID cannot be empty.");
    }

    // TODO: replace this by a getRegularUser method from the RegularUserService class
    Reaction reaction = reactionRepository.findReactionById(uuid);

    if (reaction == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND, "Reaction with UUID " + uuid + " does not exist.");
    }

    return reaction;
  }


  @Transactional
  public Reaction getReactionByIdeaAndRegularUser(String idea_id, String user_id) {

    // TODO: replace user_id in the method below by a getter from RegularUserService to check for valid user
    Reaction reaction = reactionRepository.findReactionByIdeaAndRegularUser(
        ideaService.getIdeaById(idea_id).getId(), user_id);

    if (reaction == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND,
          "Reaction associated with idea_id " + idea_id + " and user_id " + user_id
              + " does not exist.");
    }

    return reaction;
  }

  /**
   * Get all reactions by their idea
   *
   * @param uuid UUID of the idea
   * @return An ArrayList of reactions for an idea with the given UUID
   * @author Athmane Benarous
   */
  @Transactional
  public ArrayList<Reaction> getReactionsByIdea(String uuid) {

    if (uuid == null || uuid.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid UUID. UUID cannot be empty.");
    }

    return reactionRepository.findAllByIdeaId(ideaService.getIdeaById(uuid).getId());
  }

  /**
   * Get all reactions by their regular user
   *
   * @param uuid UUID of the regular user
   * @return An ArrayList of reactions for a regular user with the given UUID
   * @author Athmane Benarous
   */
  @Transactional
  public ArrayList<Reaction> getReactionsByRegularUser(String uuid) {

    if (uuid == null || uuid.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid UUID. UUID cannot be empty.");
    }

    return reactionRepository.findAllByRegularUserId(uuid);
  }

  /**
   * Remove a reaction by its id
   *
   * @param uuid the UUID of the reaction that is to be removed
   * @author Athmane Benarous
   */
  @Transactional
  public void removeReaction(String uuid) {
    reactionRepository.deleteById(this.getReactionById(uuid).getId());
  }

  /**
   * Remove all reactions by an idea
   *
   * @param uuid the UUID of the idea that contains the reactions
   * @author Athmane Benarous
   */
  @Transactional
  public void removeReactionsByIdea(String uuid) {
    for (Reaction reaction : this.getReactionsByIdea(uuid)) {
      this.removeReaction(reaction.getId());
    }
  }


  /**
   * Remove all reactions by a regular user
   *
   * @param uuid the UUID of the regular user that contains the reactions
   * @author Athmane Benarous
   */
  @Transactional
  public void removeReactionsByRegularUser(String uuid) {
    for (Reaction reaction : this.getReactionsByRegularUser(uuid)) {
      this.removeReaction(reaction.getId());
    }
  }
}
