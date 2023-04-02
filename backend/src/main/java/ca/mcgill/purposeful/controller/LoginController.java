package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API for authenticating users when they log in */
@RestController
@RequestMapping({"/api/login", "/api/login/"})
public class LoginController {

  @Autowired LoginService loginService;
  @Autowired AppUserRepository appUserRepository;

  /**
   * This methods takes in a username and and password of a user, and authenticates them The
   * username and password are passed in the header of the request with the format:
   * "Authorization":"Basic encodeBase64 (username:password)" encodeBase64 is a function that
   * encodes the username and password in base64 format
   *
   * @return a JWT token if the user is properly authenticated
   * @author Shidan Javaheri, Sasha Denouvilliez-Pech
   */
  @PostMapping()
  public String getToken() {
    // get the authentication object from the context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // generate a token from the authentication object for the user and return it
    return loginService.generateToken(authentication);
  }

  /**
   * This method validates a JWT token by returning information on the user.
   *
   * @author Sasha Denouvilliez-Pech
   */
  @GetMapping()
  public ResponseEntity<AppUserDto> validateToken() {
    // get the authentication object from the context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // get the current user
    String currentEmail = authentication.getName();
    var appUser = appUserRepository.findAppUserByEmail(currentEmail);
    // Convert to DTO
    var appUserDto =
        new AppUserDto(appUser.getEmail(), "", appUser.getFirstname(), appUser.getLastname());

    return ResponseEntity.status(HttpStatus.OK).body(appUserDto);
  }
}
