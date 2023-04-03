package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Idea;
import org.springframework.data.repository.CrudRepository;

/** Repository for Idea */
public interface IdeaRepository extends CrudRepository<Idea, Integer> {

  /**
   * Find an Idea by id
   *
   * @param id - the id of the Idea
   * @return the Idea with the given id
   */
  Idea findIdeaById(String id);

  /**
   * Delete an Idea by id
   *
   * @param id - the name of the Idea
   * @return the Idea with the given id
   */
  String deleteById(String id);
}
