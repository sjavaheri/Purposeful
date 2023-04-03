package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.RegularUser;
import org.springframework.data.repository.CrudRepository;

/** Repository for RegularUser */
public interface RegularUserRepository extends CrudRepository<RegularUser, Integer> {

  /**
   * Find a regular user by id
   *
   * @param id the id of the regular user
   * @return the regular user
   */
  RegularUser findRegularUserById(String id);

  /**
   * Find a regular user by email
   *
   * @param email the email of the regular user
   * @return the regular user
   */
  RegularUser findRegularUserByAppUserEmail(String email);

  /**
   * Find a regular user by app user id
   *
   * @param app_user_id the app user id of the regular user
   * @return the regular user
   */
  RegularUser findRegularUserByAppUser_Id(String app_user_id);
}
