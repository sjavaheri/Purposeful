package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.ModeratorRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Moderator;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.SecurityUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** The AppUserService class, the business logic for managing AppUsers */
@Service
public class AppUserService implements UserDetailsService {

  @Autowired AppUserRepository appUserRepository;

  @Autowired RegularUserRepository regularUserRepository;

  @Autowired ModeratorRepository moderatorRepository;

  @Autowired PasswordEncoder passwordEncoder;

  /**
   * Register a new regular user
   *
   * @param email - email of the user
   * @param password - password of the user
   * @param firstname - first name of the user
   * @param lastname - last name of the user
   * @return AppUser - the newly created user
   * @author Siger Ma
   */
  @Transactional
  public AppUser registerRegularUser(
      String email, String password, String firstname, String lastname) {

    // Error validation
    if (email == null || email.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid email. Email cannot be left empty");
    }
    if (firstname == null || firstname.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST,
          "Please enter a valid first name. First name cannot be left empty");
    }
    if (lastname == null || lastname.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid last name. Last name cannot be left empty");
    }
    if (password == null || password.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid password. Password cannot be left empty");
    }
    if (!email.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST,
          "Please enter a valid email. The email address you entered is not valid");
    }
    if (password.length() < 8
        || !password.matches(".*[0-9].*")
        || !password.matches(".*[A-Z].*")
        || !password.matches(".*[a-z].*")) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST,
          "Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character");
    }
    if (appUserRepository.findAppUserByEmail(email) != null) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "An account with this email address already exists");
    }
    AppUser appUser = null;

    try {
      // Create app user
      appUser = new AppUser();
      appUser.setEmail(email);
      appUser.setFirstname(firstname);
      appUser.setLastname(lastname);
      appUser.setPassword(passwordEncoder.encode(password));
      appUser.getAuthorities().add(Authority.User);
      appUserRepository.save(appUser);

      // Create role
      RegularUser regularUser = new RegularUser();
      regularUser.setAppUser(appUser);
      regularUser.setVerifiedCompany(false);
      regularUserRepository.save(regularUser);
    } catch (Exception e) {
      throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // return appUser;
    return appUser;
  }

  /**
   * Register a new moderator user
   *
   * @param email - email of the user
   * @param password - password of the user
   * @param firstname - first name of the user
   * @param lastname - last name of the user
   * @return AppUser - the newly created user
   * @author Siger Ma
   */
  @Transactional
  public AppUser registerModerator(
      String email, String password, String firstname, String lastname) {

    // Error validation
    if (email == null || email.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid email. Email cannot be left empty");
    }
    if (firstname == null || firstname.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST,
          "Please enter a valid first name. First name cannot be left empty");
    }
    if (lastname == null || lastname.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid last name. Last name cannot be left empty");
    }
    if (password == null || password.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please enter a valid password. Password cannot be left empty");
    }
    if (!email.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST,
          "Please enter a valid email. The email address you entered is not valid");
    }
    if (password.length() < 8
        || !password.matches(".*[0-9].*")
        || !password.matches(".*[A-Z].*")
        || !password.matches(".*[a-z].*")) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST,
          "Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character");
    }
    if (appUserRepository.findAppUserByEmail(email) != null) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "An account with this email address already exists");
    }
    AppUser appUser = null;

    try {
      // Create app user
      appUser = new AppUser();
      appUser.setEmail(email);
      appUser.setFirstname(firstname);
      appUser.setLastname(lastname);
      appUser.setPassword(passwordEncoder.encode(password));
      appUser.getAuthorities().add(Authority.Moderator);
      appUserRepository.save(appUser);

      // Create role
      Moderator moderator = new Moderator();
      moderator.setAppUser(appUser);
      moderatorRepository.save(moderator);
    } catch (Exception e) {
      throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    return appUser;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // look for user in database. Tell spring how to get the user
    AppUser appUser = appUserRepository.findAppUserByEmail(email);
    if (appUser == null) {
      throw new UsernameNotFoundException("User not found");
    }
    // security user is an implementation of UserDetails which already knows how to
    // check the password and whether it matches the given username
    return new SecurityUser(appUser);
  }

  /**
   * Modify the first name and last name of a user
   *
   * @param email - email of the user
   * @param firstname - first name of the user
   * @param lastname - last name of the user
   * @return AppUser - the modified user
   * @auhtor Enzo Benoit-Jeannin
   */
  @Transactional
  public AppUser modifyUserNames(String email, String firstname, String lastname) {

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
    if (error.length() > 0) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, error);
    }

    // Check if the user we are trying to modify does indeed exist
    AppUser user = appUserRepository.findAppUserByEmail(email);
    if (user == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "This account does not exist.");
    }

    user.setFirstname(firstname);
    user.setLastname(lastname);
    appUserRepository.save(user);
    return user;
  }

  /**
   * This service method updates the user's password based on the given inputs. Passwords must be at
   * least 8 characters long and contain at least one number, one lowercase character and one
   * uppercase character
   *
   * @param email The email of the user account to modify its password
   * @param password The new password of the user
   * @return The modified user
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public AppUser modifyPassword(String email, String password) {
    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0) {
      error += "Email cannot be left empty! ";
    }
    // Check that string is at least 8 characters long and contain at least one
    // number, one lowercase character and one uppercase character
    if (password.length() < 8
        || !password.matches(".*[0-9].*")
        || !password.matches(".*[A-Z].*")
        || !password.matches(".*[a-z].*")) {
      error +=
          "Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! ";
    }
    if (error.length() > 0) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, error);
    }

    // Check if the user we are trying to modify does indeed exist
    AppUser user = appUserRepository.findAppUserByEmail(email);
    if (user == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "This account does not exist.");
    }

    user.setPassword(passwordEncoder.encode(password));
    appUserRepository.save(user);
    return user;
  }

  /**
   * This service method returns all the users in the database
   *
   * @return List<AppUser> - the list of all the users in the database
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public List<AppUser> getAllUsers() {
    Iterable<AppUser> iterable = appUserRepository.findAll();
    List<AppUser> resultList = new ArrayList<AppUser>();
    for (AppUser t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
