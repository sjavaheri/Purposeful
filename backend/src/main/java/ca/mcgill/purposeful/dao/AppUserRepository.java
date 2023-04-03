package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.AppUser;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

/** Repository for AppUser */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

  /**
   * Find an AppUser by id
   * @param id - the id of the AppUser
   * @return the AppUser with the given id
   */
  AppUser findAppUserById(String id);

  /**
   * Find an AppUser by email
   * @param email - the email of the AppUser
   * @return the AppUser with the given email
   */
  AppUser findAppUserByEmail(String email);

    /**
     * Find all AppUsers
     * @return a list of all AppUsers
     */
  ArrayList<AppUser> findAll();
}
