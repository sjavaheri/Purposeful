package ca.mcgill.purposeful.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.SecurityUser;

/**
 * The AppUserService class, the business logic for managing AppUsers
 */
@Service
public class AppUserService implements UserDetailsService {

  @Autowired
  AppUserRepository appUserRepository;

  @Autowired
  RegularUserRepository regularUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * Register a new regular user
   *
   * @param email    - email of the user
   * @param username - username of the user
   * @param password - password of the user
   * @return AppUser - the newly created user
   * @author Siger Ma
   */
  @Transactional
  public AppUser registerRegularUser(String email, String username, String password) {

    // Error validation
    if (email == null || email.isEmpty()) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
          "Please enter a valid email. Email cannot be left empty");
    }
    if (username == null || username.isEmpty()) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
          "Please enter a valid username. Username cannot be left empty");
    }
    if (password == null || password.isEmpty()) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
          "Please enter a valid password. Password cannot be left empty");
    }
    if (!email.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
          "Please enter a valid email. The email address you entered is not valid");
    }
    if (password.length() < 8 || !password.matches(".*[0-9].*") || !password.matches(".*[A-Z].*")
        || !password.matches(".*[a-z].*")) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
          "Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character");
    }
    if (appUserRepository.findAppUserByEmail(email) != null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
          "An account with this email address already exists");
    }
    if (appUserRepository.findAppUserByUsername(username) != null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST,
          "An account with this username already exists");
    }

    AppUser appUser = null;

    try {
      // Create app user
      appUser = new AppUser();
      appUser.setEmail(email);
      appUser.setUsername(username);
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

    return appUser;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // look for user in database. Tell spring how to get the user
    AppUser appUser = appUserRepository.findAppUserByUsername(username);
    if (appUser == null) {
      throw new UsernameNotFoundException("User not found");
    }
    // security user is an implementation of UserDetails which already knows how to
    // check the password and whether it matches the given username
    return new SecurityUser(appUser);
  }

}
