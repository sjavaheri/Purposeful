package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.CollaborationResponseDTO;
import ca.mcgill.purposeful.dto.CollaborationResponseInformationDTO;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.service.CollaborationResponseService;
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
@RequestMapping({"/api/collaborationResponse", "/api/collaborationResponse/"})
public class CollaborationResponseController {

  @Autowired CollaborationResponseService collaborationResponseService;

  /**
   * This method returns the collaboration response for the requester and the idea
   *
   * @param ideaId the idea's id
   * @return collaboration response
   */
  @GetMapping({"/{ideaId}", "/{ideaId}/"})
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<CollaborationResponseDTO> getCollaborationResponseForRequesterAndIdea(
      @PathVariable String ideaId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      throw new GlobalException(HttpStatus.UNAUTHORIZED, "User is not authenticated.");
    }

    CollaborationResponse response =
        collaborationResponseService.getCollaborationResponseForRequesterAndIdea(
            auth.getName(), ideaId);
    CollaborationResponseDTO responseDTO = new CollaborationResponseDTO(response);

    return new ResponseEntity<CollaborationResponseDTO>(responseDTO, HttpStatus.OK);
  }

  /**
   * This method is used to approve a collaboration request
   *
   * @param collaborationResponseInformationDTO The collaboration response info WITH an additional
   *     contact
   * @return the collaboration response with a status
   * @author Wassim Jabbour
   */
  @PostMapping({"/approve", "/approve/"})
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<CollaborationResponseDTO> approveCollaborationRequest(
      @RequestBody CollaborationResponseInformationDTO collaborationResponseInformationDTO) {

    // Extract the email
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    // Make the call
    return new ResponseEntity<CollaborationResponseDTO>(
        new CollaborationResponseDTO(
            collaborationResponseService.approveCollaborationRequest(
                email,
                collaborationResponseInformationDTO.getCollaborationRequestId(),
                collaborationResponseInformationDTO.getAdditionalContact(),
                collaborationResponseInformationDTO.getMessage())),
        HttpStatus.OK);
  }

  /**
   * This method is used to decline a collaboration request
   *
   * @param collaborationResponseInformationDTO The collaboration response info without an
   *     additional contact (Leave field to be null)
   * @return the collaboration response with a status
   * @author Wassim Jabbour
   */
  @PostMapping({"/decline", "/decline/"})
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<CollaborationResponseDTO> declineCollaborationRequest(
      @RequestBody CollaborationResponseInformationDTO collaborationResponseInformationDTO) {

    // Extract the email
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    // Make the call
    return new ResponseEntity<CollaborationResponseDTO>(
        new CollaborationResponseDTO(
            collaborationResponseService.declineCollaborationRequest(
                email,
                collaborationResponseInformationDTO.getCollaborationRequestId(),
                collaborationResponseInformationDTO.getMessage())),
        HttpStatus.OK);
  }
}
