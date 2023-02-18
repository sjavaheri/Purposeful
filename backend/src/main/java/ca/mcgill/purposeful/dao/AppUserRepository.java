package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.AppUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for AppUser
 */

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

  AppUser findAppUserById(String id);

  AppUser findAppUserByEmail(String email);

}
