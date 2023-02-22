package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Idea;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Idea
 */

public interface IdeaRepository extends CrudRepository<Idea, Integer> {

  Idea findIdeaById(String id);
  Idea deleteById(String id);
}
