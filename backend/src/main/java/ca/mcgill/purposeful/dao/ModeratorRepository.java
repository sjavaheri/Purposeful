package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Moderator;
import org.springframework.data.repository.CrudRepository;

/** Repository for Moderator */
public interface ModeratorRepository extends CrudRepository<Moderator, Integer> {

  Moderator findModeratorById(String id);

  Moderator findModeratorByAppUserEmail(String email);
}
