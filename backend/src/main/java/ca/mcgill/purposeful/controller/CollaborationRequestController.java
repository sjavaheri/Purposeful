package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.CollaborationRequestDTO;
import ca.mcgill.purposeful.service.CollaborationRequestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/collaborationRequest", "/api/collaborationRequest/"})
public class CollaborationRequestController {
  @Autowired CollaborationRequestService collaborationRequestService;

  /**
   * This method send a collaboration request to the owner of an idea
   *
   * @param collaborationRequestDTO - the collaboration request to be sent
   * @return the newly created collaboration request DTO
   * @author Wassim Jabbour
   */
  @PostMapping
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<CollaborationRequestDTO> sendCollaborationRequest(
      @RequestBody CollaborationRequestDTO collaborationRequestDTO) {

    // Retrieve the sender's email given that he is logged in
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    // Extract the required information from the DTO
    String ideaId = collaborationRequestDTO.getIdea().getId();
    String message = collaborationRequestDTO.getMessage();
    String additionalContact = collaborationRequestDTO.getAdditionalContact();

    // Send the request and return it in the form of a DTO
    return new ResponseEntity<CollaborationRequestDTO>(
        new CollaborationRequestDTO(
            collaborationRequestService.sendCollaborationRequest(
                email, ideaId, message, additionalContact)),
        HttpStatus.OK);
  }

  /**
   * This method returns all collaboration requests linked to a given idea
   *
   * @param ideaId - the id of the idea
   * @return a list of collaboration request DTOs linked to the idea
   * @throws GlobalException if the idea does not exist
   */
  @GetMapping("/{ideaId}")
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<List<CollaborationRequestDTO>> getCollaborationRequestsByIdea(
      @PathVariable String ideaId) {

    // Retrieve the sender's email given that he is logged in
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    return new ResponseEntity<List<CollaborationRequestDTO>>(
        CollaborationRequestDTO.convertToDTO(
            collaborationRequestService.getCollaborationRequestsByIdea(email, ideaId)),
        HttpStatus.OK);
  }
}
