package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.SecurityUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for SecurityUser
 */

public interface SecurityUserRepository extends CrudRepository<SecurityUser, Integer> {

  SecurityUser findSecurityUserById(String id);

}
