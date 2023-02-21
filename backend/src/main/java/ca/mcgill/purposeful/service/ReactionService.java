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
  @Autowired
  IdeaService ideaService;

  /*
   Service functions
  */

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

    // check if a previous reaction exists
    Reaction previousReaction = this.getReactionByIdeaAndRegularUser(idea_id, user_id);

    // delete reaction if it exists and return null
    if (previousReaction != null) {
      reactionRepository.deleteById(previousReaction.getId());
      return null;
    }
    // create reaction if it doesn't exist and return reaction
    else {
      Reaction reaction = new Reaction();
      reaction.setDate(date);
      reaction.setReactionType(reactionType);
      reaction.setIdea(ideaService.getIdeaById(idea_id));
      reaction.setRegularUser(regularUserRepository.findRegularUserById(user_id));
      return reactionRepository.save(reaction);
    }
  }

  /**
   * DEPRECATED Get a reaction by its id
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

    Reaction reaction = reactionRepository.findReactionById(uuid);

    if (reaction == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND, "Reaction with UUID " + uuid + " does not exist.");
    }

    return reaction;
  }

  /**
   * Finds a reaction given its idea id and regular user id
   *
   * @param idea_id id of the idea
   * @param user_id id of the regular user
   * @return the reaction linking the idea and the regular user
   */
  @Transactional
  public Reaction getReactionByIdeaAndRegularUser(String idea_id, String user_id) {
    // validate idea
    ideaService.getIdeaById(idea_id);
    // TODO: replace user_id in the method below by a getter from RegularUserService to check for valid user

    Reaction reaction = reactionRepository.findReactionByIdeaAndRegularUser(idea_id, user_id);

    if (reaction == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND,
          "Reaction associated with idea_id " + idea_id + " and user_id " + user_id
              + " does not exist.");
    }

    return reaction;
  }

  /**
   * DEPRECATED Get all reactions by their idea
   *
   * @param uuid UUID of the idea
   * @return An ArrayList of reactions for an idea with the given UUID
   * @author Athmane Benarous
   */
  @Transactional
  public ArrayList<Reaction> getReactionsByIdea(String uuid) {
    // validate idea
    ideaService.getIdeaById(uuid);

    ArrayList<Reaction> reactions = reactionRepository.findAllByIdeaId(uuid);

    if (reactions.size() == 0) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND,
          "Reactions associated with idea_id " + uuid
              + " do not exist.");
    }

    return reactions;
  }

  /**
   * DEPRECATED Get all reactions by their regular user
   *
   * @param uuid UUID of the regular user
   * @return An ArrayList of reactions for a regular user with the given UUID
   * @author Athmane Benarous
   */
  @Transactional
  public ArrayList<Reaction> getReactionsByRegularUser(String uuid) {

    // TODO: validate regular user with a getRegularUserById method from its service class

    ArrayList<Reaction> reactions = reactionRepository.findAllByRegularUserId(uuid);

    if (reactions.size() == 0) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND,
          "Reactions associated with user_id " + uuid
              + " do not exist.");
    }

    return reactions;
  }

  /**
   * DEPRECATED Remove a reaction by its id
   *
   * @param uuid the UUID of the reaction that is to be removed
   * @author Athmane Benarous
   */
  @Transactional
  public void removeReaction(String uuid) {
    // validate reaction
    this.getReactionById(uuid);
    reactionRepository.deleteById(uuid);
  }

  /**
   * DEPRECATED Remove all reactions by an idea
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
   * DEPRECATED Remove all reactions by a regular user
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
