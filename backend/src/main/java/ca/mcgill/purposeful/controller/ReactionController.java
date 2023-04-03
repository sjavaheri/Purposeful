package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dto.ReactionDTO;
import ca.mcgill.purposeful.dto.ReactionRequestDTO;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import ca.mcgill.purposeful.service.ReactionService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API for accessing the endpoints of Reaction */
@RestController
@RequestMapping({"/api/reaction", "/api/reaction/"})
public class ReactionController {

  @Autowired private ReactionService reactionService;
  @Autowired private AppUserRepository appUserRepository;
  @Autowired private RegularUserRepository regularUserRepository;

  /**
   * POST method to react
   *
   * @param reactionDTO - the reaction added or removed
   * @return the newly created reaction DTO or the removed reaction DTO with null values
   * @author Athmane Benarous
   */
  @PostMapping
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<ReactionDTO> react(@RequestBody ReactionRequestDTO reactionDTO) {
    // Unpack the DTO
    if (reactionDTO == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "reactionDTO is null");
    }
    ReactionType reactionType = reactionDTO.getReactionType();
    String idea_id = reactionDTO.getIdea_id();
    String email = reactionDTO.getEmail();
    Date date = new Date();

    // Check if the user is authorized
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String authorizedEmail = authentication.getName();

    if (!authorizedEmail.equals(email)) {
      throw new GlobalException(HttpStatus.FORBIDDEN, "User not authorized");
    }

    // Get the user id
    // this check should ideally not be in a controller, but it was added with time constraints
    AppUser appUser = appUserRepository.findAppUserByEmail(email);
    if (appUser == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "User not found");
    }

    String app_user_id = appUser.getId();
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(app_user_id).getId();
    // react
    Reaction reaction = reactionService.react(date, reactionType, idea_id, user_id);

    // return the reaction
    if (reaction == null) {
      return new ResponseEntity<ReactionDTO>(new ReactionDTO(), HttpStatus.OK);
    } else {
      return new ResponseEntity<ReactionDTO>(new ReactionDTO(reaction), HttpStatus.OK);
    }
  }
}
