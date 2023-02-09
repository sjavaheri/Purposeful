package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Role
 */

public interface RoleRepository extends CrudRepository<Role, Integer> {

  Role findRoleById(String id);

}
