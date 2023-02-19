package ca.mcgill.purposeful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.util.DtoUtility;

/**
 * API for accessing the endpoints of AppUser
 */

@RestController
@RequestMapping({"/api/appuser", "/api/appuser/"})
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  /**
   * POST method to register a new regular user
   *
   * @param appUserDto - the user to be registered
   * @return the newly created user
   * @author Siger Ma
   */

  @PostMapping(value = {"/regular",
      "/regular/"}, consumes = "application/json", produces = "application/json")
  @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
  public ResponseEntity<AppUserDto> registerRegularUser(@RequestBody AppUserDto appUserDto) {
    // Unpack the DTO
    if (appUserDto == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "AppUserDto is null");
    }
    String email = appUserDto.getEmail();
    String password = appUserDto.getPassword();
    String firstname = appUserDto.getFirstname();
    String lastname = appUserDto.getLastname();

    // Register the user
    AppUserDto registeredUser = DtoUtility.convertToDto(
        appUserService.registerRegularUser(email, password, firstname, lastname));

    return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
  }

}
