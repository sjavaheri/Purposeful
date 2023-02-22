package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Reaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Reaction
 */

public interface ReactionRepository extends CrudRepository<Reaction, Integer> {

  Reaction findReactionById(String id);

}
