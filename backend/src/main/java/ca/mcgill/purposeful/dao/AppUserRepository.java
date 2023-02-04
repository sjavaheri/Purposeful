package ca.mcgill.purposeful.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.purposeful.model.AppUser;

/**
 * Repository for AppUser
 */
 
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    AppUser findAppUserByUsername(String username);

}
