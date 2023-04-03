package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Reaction;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

/** Repository for Reaction */
public interface ReactionRepository extends CrudRepository<Reaction, Integer> {

  /**
   * Find a Reaction by id
   *
   * @param id - the id of the Reaction
   * @return the Reaction with the given id
   */
  Reaction findReactionById(String id);

  /**
   * Find a Reaction by idea_id and user_id
   *
   * @param idea_id - the id of the idea
   * @param user_id - the id of the user
   * @return the Reaction with the given idea_id and user_id
   */
  Reaction findReactionByIdea_IdAndRegularUser_Id(String idea_id, String user_id);

  /**
   * Delete a Reaction by id
   *
   * @param id - the id of the Reaction
   * @return the deleted Reaction
   */
  String deleteReactionById(String id);

  /**
   * Find all Reactions by idea_id
   *
   * @param idea_id - the id of the idea
   * @return a list of all Reactions with the given idea_id
   */
  ArrayList<Reaction> findAllByIdeaId(String idea_id);

  /**
   * Find all Reactions by user_id
   *
   * @param user_id - the id of the user
   * @return a list of all Reactions with the given user_id
   */
  ArrayList<Reaction> findAllByRegularUser_Id(String user_id);
}
