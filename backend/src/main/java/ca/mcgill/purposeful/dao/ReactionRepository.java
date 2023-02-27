package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Reaction;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/** Repository for Reaction */
public interface ReactionRepository extends CrudRepository<Reaction, Integer> {

  Reaction findReactionById(String id);

  Reaction findReactionByIdea_IdAndRegularUser_Id(String idea_id, String user_id);

  String deleteReactionById(String id);

  ArrayList<Reaction> findAllByIdeaId(String idea_id);

  ArrayList<Reaction> findAllByRegularUser_Id(String user_id);
}
