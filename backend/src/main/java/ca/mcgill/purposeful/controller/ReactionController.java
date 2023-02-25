package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dto.ReactionDTO;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.service.AppUserService;
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

/**
 * API for accessing the endpoints of Reaction
 */
@RestController
@RequestMapping({"/api/reaction", "/api/reaction/"})
public class ReactionController {

  @Autowired
  private ReactionService reactionService;
  @Autowired
  private AppUserRepository appUserRepository;
  @Autowired
  private RegularUserRepository regularUserRepository;
  @Autowired
  private AppUserService appUserService;

  /**
   * POST method to react
   *
   * @param reactionDTO - the reaction added or removed
   * @return the newly created reaction DTO or the removed reaction DTO with null values
   * @author Athmane Benarous
   */
  @PostMapping(value = {"/react", "/react/"})
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<ReactionDTO> react(@RequestBody ReactionDTO reactionDTO) {
    // Unpack the DTO
    if (reactionDTO == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "reactionDTO is null");
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    RegularUser user = regularUserRepository.findRegularUserById(reactionDTO.getId());
    
    if (!user.getAppUser().getEmail().equals(email)) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "User not authorized");
    }

    Date date = new Date();
    ReactionType reactionType = reactionDTO.getReactionType();
    String idea_id = reactionDTO.getIdea_id();
    String user_id = reactionDTO.getUser_id();

    // react
    Reaction reaction = reactionService.react(date, reactionType, idea_id, user_id);

    // return the reaction
    return new ResponseEntity<ReactionDTO>(
        (reaction == null) ? new ReactionDTO() : new ReactionDTO(reaction), HttpStatus.CREATED);
  }

}
