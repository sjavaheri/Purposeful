package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.ReactionDTO;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import ca.mcgill.purposeful.service.ReactionService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
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

  /**
   * POST method to react
   *
   * @param reactionDTO - the reaction added or removed
   * @return the newly created reaction or null if the reaction was removed
   * @author Athmane Benarous
   */
  @PutMapping(value = {"/react", "/react/"})
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<ReactionDTO> react(@RequestBody ReactionDTO reactionDTO) {
    // Unpack the DTO
    if (reactionDTO == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "reactionDTO is null");
    }

    ReactionType reactionType = reactionDTO.getReactionType();
    String userId = reactionDTO.getUser_id();
    String ideaId = reactionDTO.getIdea_id();
    Date date = reactionDTO.getDate();

    // react
    Reaction reaction = reactionService.react(date, reactionType, userId, ideaId);

    return ResponseEntity.status(HttpStatus.OK).body(new ReactionDTO(reaction));
  }

}
