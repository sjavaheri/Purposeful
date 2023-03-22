package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Status;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * Collaboration request service functions
 *
 * @author Wassim Jabbour
 */
public class CollaborationRequestService {

  /*
   * CRUD repos
   */

  @Autowired
  CollaborationRequestRepository collaborationRequestRepository;

  @Autowired
  RegularUserRepository regularUserRepository;

  /*
   * Service repos
   */

  @Autowired
  IdeaService ideaService;

  /*
   * Service methods
   */

  /**
   * Sends a collaboration request to the owner of the idea
   * @param ideaId the id of the idea
   * @param message the message to send to the owner of the idea
   *
   * @author Wassim Jabbour
   */
  @Transactional
  public CollaborationRequest sendCollaborationRequest(String requesterEmail, String ideaId, String message, String additionalContact) {

    // Find the target idea (Throws an error itself if the idea does not exist)
    Idea targetIdea = ideaService.getIdeaById(ideaId);

    // Find the requester account
    RegularUser requester = regularUserRepository.findRegularUserByAppUserEmail(requesterEmail);

    // Check it's valid (Should never be false since the user is logged in and we retrieve the email from the token)
    // Just included for safety
    if(requester == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND, "This user account does not exist");
    }

    // Check that the sender and receiver are not the same person
    if(requesterEmail.equals(targetIdea.getUser().getAppUser().getEmail())) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Cannot send a collaboration request to oneself");
    }

    // Check that the sender didn't already send a request to the receiver
    // For now the assumption is that a user can only send one request to another user (Even if the request is declined)
    // TODO: For future sprints, add a timespan after which another request can be sent to the same person if it was declined
    List<CollaborationRequest> requests = collaborationRequestRepository.findCollaborationRequestsByRequesterAndIdea(requester, targetIdea);
    if(requests != null && requests.size() > 0) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "You can only send one collaboration request to this user for this idea");
    }

    // Create & populate the new collaboration request
    CollaborationRequest collabReq = new CollaborationRequest();
    collabReq.setIdea(targetIdea);
    collabReq.setMessage(message);
    collabReq.setAdditionalContact(additionalContact);
    collabReq.setStatus(Status.Pending);
    collabReq.setRequester(requester);

    // Save the collaboration request and return it
    return collaborationRequestRepository.save(collabReq);
  }

}
