package ca.mcgill.purposeful.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.service.ModeratorService;
import ca.mcgill.purposeful.util.DtoUtility;

/**
 * API for accessing the endpoints of AppUser
 */
@RestController
@RequestMapping({"/api/appuser", "/api/appuser/"})
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private ModeratorService moderatorService;

  /**
   * POST method to register a new regular user
   *
   * @param appUserDto - the user to be registered
   * @return the newly created user
   * @author Siger Ma
   */
  @PostMapping(value = {"/regular", "/regular/"})
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

    return new ResponseEntity<AppUserDto>(registeredUser, HttpStatus.OK);
  }

  /**
   * POST method to register a new moderator
   *
   * @param appUserDto - the user to be registered
   * @return the newly created user
   * @author Siger Ma
   */

  @PostMapping(value = {"/moderator", "/moderator/"})
  @PreAuthorize("hasAuthority('Owner')")
  public ResponseEntity<AppUserDto> registerModerator(@RequestBody AppUserDto appUserDto) {
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
        appUserService.registerModerator(email, password, firstname, lastname));

    return new ResponseEntity<AppUserDto>(registeredUser, HttpStatus.OK);
  }

  /**
   * PUT method to update the names of a regular user
   * @param appUserDto - the user to modify the names
   * @return the modified user
   * 
   * @author Enzo Benoit-Jeannin
   */
    @PutMapping(value = {"/regular",
      "/regular/"}, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('Owner', 'Moderator', 'User')")
    public ResponseEntity<AppUserDto> updateRegularUser(@RequestBody AppUserDto appUserDto) {
      // Unpack the DTO
      if (appUserDto == null) {
        throw new GlobalException(HttpStatus.BAD_REQUEST, "AppUserDto is null");
      }

      String email = appUserDto.getEmail();
      String firstname = appUserDto.getFirstname();
      String lastname = appUserDto.getLastname();

      // Check if the user making the request is authorized to modify the password
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentEmail = authentication.getName();
      List<String> authorities = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList());

      if (!authorities.contains("Owner") && !authorities.contains("Moderator") && !currentEmail.equals(email)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
      }

      // Register the user
      AppUserDto registeredUser = DtoUtility.convertToDto(
          appUserService.modifyUserNames(email, firstname, lastname));
  
      return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

    /**
     * PUT method to update the password of a regular user
     * @param appUserDto - the user to modify the password
     * @return the modified user
     * 
     * @author Enzo Benoit-Jeannin
     */
    @PutMapping(value = {"/regular/password",
      "/regular/password/"}, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('Owner', 'Moderator', 'User')")
    public ResponseEntity<AppUserDto> updatePasswordRegularUser(@RequestBody AppUserDto appUserDto) {
      // Unpack the DTO
      if (appUserDto == null) {
        throw new GlobalException(HttpStatus.BAD_REQUEST, "AppUserDto is null");
      }

      String email = appUserDto.getEmail();
      String password = appUserDto.getPassword();

      // Check if the user making the request is authorized to modify the password
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentEmail = authentication.getName();
      List<String> authorities = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList());

      if (!authorities.contains("Owner") && !authorities.contains("Moderator") && !currentEmail.equals(email)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
      }

      // Register the user
      AppUserDto registeredUser = DtoUtility.convertToDto(
          appUserService.modifyPassword(email, password));
  
      return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

  /**
   * GET method to get all users
   * 
   * @return a list of all users
   * 
   * @author Enzo Benoit-Jeannin
   */
  @GetMapping(value = {"/users", "/users/"})
  public List<AppUserDto> getAllUsers() {
    return appUserService.getAllUsers().stream()
        .map(e -> DtoUtility.convertToDto(e))
        .collect(Collectors.toList());
  }

  /**
   * PUT method to update the names of a moderator
   * @param appUserDto - the moderator to modify the names
   * @return the modified moderator
   * 
   * @author Enzo Benoit-Jeannin
   */
  @PutMapping(value = {"/moderator",
  "/moderator/"}, consumes = "application/json", produces = "application/json")
  @PreAuthorize("hasAnyAuthority('Owner', 'Moderator')")
  public ResponseEntity<AppUserDto> updateModerator(@RequestBody AppUserDto appUserDto) {
    // Unpack the DTO
    if (appUserDto == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "AppUserDto is null");
    }
    String email = appUserDto.getEmail();
    String firstname = appUserDto.getFirstname();
    String lastname = appUserDto.getLastname();

    // Check if the user making the request is authorized to modify the password
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentEmail = authentication.getName();
    List<String> authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    if (!authorities.contains("Owner") && !currentEmail.equals(email)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    // Register the user
    AppUserDto registeredUser = DtoUtility.convertToDto(
        moderatorService.modifyModerator(email, lastname, firstname));

    return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
  }

  /**
     * PUT method to update the password of a moderator
     * @param appUserDto - the moderator to modify the password
     * @return the modified moderator
     * 
     * @author Enzo Benoit-Jeannin
     */
    @PutMapping(value = {"/moderator/password",
      "/moderator/password/"}, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('Owner', 'Moderator')")
    public ResponseEntity<AppUserDto> updatePasswordModerator(@RequestBody AppUserDto appUserDto) {
      // Unpack the DTO
      if (appUserDto == null) {
        throw new GlobalException(HttpStatus.BAD_REQUEST, "AppUserDto is null");
      }

      String email = appUserDto.getEmail();
      String password = appUserDto.getPassword();

      // Check if the user making the request is authorized to modify the password
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentEmail = authentication.getName();
      List<String> authorities = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList());

      if (!authorities.contains("Owner") && !currentEmail.equals(email)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
      }
  
      // Register the user
      AppUserDto registeredUser = DtoUtility.convertToDto(
          moderatorService.modifyPassword(email, password));
  
      return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }


}
