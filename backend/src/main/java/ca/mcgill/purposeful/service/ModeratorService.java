package ca.mcgill.purposeful.service;

import jakarta.transaction.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;

import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Moderator;
import ca.mcgill.purposeful.model.Role;

/**
 * The services of the Moderator class
 *
 * @author Enzo Benoit-Jeannin
 */
@Service
public class ModeratorService {

  // CRUD Repositories
  @Autowired
  private AppUserRepository appUserRepository;

    /**
     * This service method modifies an exisiting moderator based on the given inputs
     * 
     * Note we do not modify the role of that app user, because we consider it to stay a moderator.
     * We can use the modifyUser in the appUser service to change its role
     * 
     * @param email new email of the moderator (must be unique)
     * @param lastname new last name of the moderator
     * @param firstname new first name of the moderator
     * @param authorities new authorities of the moderator
     * @return  the modified moderator
     */
    @Transactional
    public AppUser modifyModerator(String email, String lastname, String firstname, Set<Authority> authorities){

        // Input Validation
        String error = "";
        if (email == null || email.isEmpty()) {
          error += "Email cannot be left empty! ";
        }
        if (firstname == null || firstname.isEmpty()) {
          error += "First name cannot be left empty! ";
        }
        if (lastname == null || lastname.isEmpty()) {
          error += "Last name cannot be left empty! ";
        }
        if (authorities == null) {
          error += "Authorities cannot be null! ";
        }
        if (error.length() > 0){
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          error);
        }

        // Check if the oderator we are trying to modify does indeed exist
        AppUser moderator = appUserRepository.findAppUserByEmail(email);
        if (moderator == null){
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "This account does not exist.");
        }

        // Check that the new auhtorities of that user still include a moderator
        Iterator<Authority> itr = authorities.iterator();
        boolean isModerator = false;
        while (itr.hasNext()){
          if (itr.next().equals(Authority.Moderator)){
            isModerator = true;
          }
        }
        if (!isModerator){
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "New authorities do not include the moderator authority!");
        }

        List<Role> roles = moderator.getRole();

        boolean modified = false; 

        for (int i = 0; i<roles.size(); i++){
          if (roles.get(i) instanceof Moderator){
              moderator.setFirstname(firstname);
              moderator.setLastname(lastname);
              moderator.setEmail(email);
              moderator.setAuthorities(authorities);
              appUserRepository.save(moderator);
              modified = true;
          }
        }
        if (!modified){
          throw new GlobalException(HttpStatus.BAD_REQUEST,
          "User is not a moderator!");
        }
        return moderator;

    }

      /**
   * This service method updates the moderator's password based on the givenm inputs
   *  asswords must be at least 8 characters long and contain at least one number, 
   *  one lowercase character and one uppercase character
   * 
   * @param email The email of the moderator account to modify its password
   * @param password The new password of the moderator
   * @return The modified moderator
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public AppUser modifyPassword(String email, String password){
    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0) {
      error += "Email cannot be left empty! ";
    }
    // Check that string is at least 8 characters long and contain at least one number, one lowercase character and one uppercase character
    if (password == null || password.trim().length() == 0 || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
      error += "Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! ";
    }
    if (error.length() > 0) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, error);
    }

    // Check if the oderator we are trying to modify does indeed exist
    AppUser moderator = appUserRepository.findAppUserByEmail(email);
    if (moderator == null){
      throw new GlobalException(HttpStatus.BAD_REQUEST,
      "This account does not exist.");
    }

    List<Role> roles = moderator.getRole();

    boolean modified = false; 

    for (int i = 0; i<roles.size(); i++){
      if (roles.get(i) instanceof Moderator){
          moderator.setPassword(password);
          appUserRepository.save(moderator);
          modified = true;
      }
    }
    if (!modified){
      throw new GlobalException(HttpStatus.BAD_REQUEST,
      "User is not a moderator!");
    }
    return moderator;
  }
}
