package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Moderator;
import org.springframework.data.repository.CrudRepository;

/** Repository for Moderator */
public interface ModeratorRepository extends CrudRepository<Moderator, Integer> {

  /**
   * Find a Moderator by id
   * @param id - the id of the Moderator
   * @return the Moderator with the given id
   */
  Moderator findModeratorById(String id);

    /**
     * Find a Moderator by email
     * @param email - the email of the Moderator
     * @return the Moderator with the given email
     */
  Moderator findModeratorByAppUserEmail(String email);
}
