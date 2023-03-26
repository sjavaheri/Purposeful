package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.CollaborationResponseRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Status;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** Collaboration response service functions */
@Service
public class CollaborationResponseService {
  @Autowired CollaborationResponseRepository collaborationResponseRepository;

  @Autowired CollaborationRequestRepository collaborationRequestRepository;

  @Autowired IdeaService ideaService;

  @Autowired RegularUserRepository regularUserRepository;

  /**
   * Get the collaboration response for a given idea
   *
   * @param ideaUuid the UUID of the idea
   * @return the collaboration response (or null if there is none)
   * @throws GlobalException if the idea does not have a collaboration request from the user
   * @author Thibaut Baguette
   */
  @Transactional
  public CollaborationResponse getCollaborationResponseForRequesterAndIdea(
      String requesterEmail, String ideaUuid) {
    Idea idea = ideaService.getIdeaById(ideaUuid);
    RegularUser requester = regularUserRepository.findRegularUserByAppUserEmail(requesterEmail);

    if (requester == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "Requester does not exist");
    }

    List<CollaborationRequest> requests =
        collaborationRequestRepository.findCollaborationRequestsByRequesterAndIdea(requester, idea);

    if (requests.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "You did not send a collaboration request for this idea");
    }

    CollaborationRequest request = requests.get(0);

    CollaborationResponse response = request.getCollaborationResponse();

    if (response == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND, "There is no response for this collaboration request");
    }

    return response;
  }

  /**
   * Approve a collaboration request and send out the additional contact information to the
   * requester
   *
   * @param handlerEmail the email of the user accepting / declining the request
   * @param requestId the id of the collaboration request
   * @param additionalContact the additional contact information to send to the requester
   * @param message the message to send to the requester
   * @author Wassim Jabbour
   */
  @Transactional
  public CollaborationResponse approveCollaborationRequest(
      String handlerEmail, String requestId, String additionalContact, String message) {

    // Find the collaboration request
    CollaborationRequest request =
        collaborationRequestRepository.findCollaborationRequestById(requestId);

    // Make sure it isn't null
    if (request == null) {
      throw new GlobalException(HttpStatus.NOT_FOUND, "This collaboration request does not exist");
    }

    // Make sure it doesn't already have a response
    if (request.getCollaborationResponse() != null) {
      throw new GlobalException(
          HttpStatus.CONFLICT, "This collaboration request already has a response");
    }

    // Find the handler account
    RegularUser handler = regularUserRepository.findRegularUserByAppUserEmail(handlerEmail);

    // Check it's valid (Should never be false since the user is logged in and we
    // retrieve the email
    // from the token)
    // Just included for safety
    if (handler == null) {
      throw new GlobalException(HttpStatus.NOT_FOUND, "This user account does not exist");
    }

    // Check if the handler is the owner of the idea to which the request is linked
    if (!request.getIdea().getUser().getId().equals(handler.getId())) {
      throw new GlobalException(HttpStatus.FORBIDDEN, "The handler is not the owner of the idea");
    }

    // Check that both the additional contact and message are not null / empty
    if (additionalContact == null
        || message == null
        || additionalContact.isEmpty()
        || message.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST,
          "Please provide an additional contact and a message to approve this request");
    }

    // Create the response
    CollaborationResponse response = new CollaborationResponse();
    response.setAdditionalContact(additionalContact);
    response.setMessage(message);
    response.setStatus(Status.Approved);

    // Set the response in the request
    request.setCollaborationResponse(response);

    // Save the response & request
    response = collaborationResponseRepository.save(response);
    collaborationRequestRepository.save(request);

    // Return the response
    return response;
  }

  /**
   * Decline a collaboration request without any additional contact information
   *
   * @param handlerEmail the email of the user accepting / declining the request
   * @param requestId the id of the collaboration request
   * @param message the message to send to the requester
   * @author Wassim Jabbour
   */
  @Transactional
  public CollaborationResponse declineCollaborationRequest(
      String handlerEmail, String requestId, String message) {

    // Find the collaboration request
    CollaborationRequest request =
        collaborationRequestRepository.findCollaborationRequestById(requestId);

    // Make sure it isn't null
    if (request == null) {
      throw new GlobalException(HttpStatus.NOT_FOUND, "This collaboration request does not exist");
    }

    // Make sure it doesn't already have a response
    if (request.getCollaborationResponse() != null) {
      throw new GlobalException(
          HttpStatus.CONFLICT, "This collaboration request already has a response");
    }

    // Find the handler account
    RegularUser handler = regularUserRepository.findRegularUserByAppUserEmail(handlerEmail);

    // Check it's valid (Should never be false since the user is logged in and we
    // retrieve the email
    // from the token)
    // Just included for safety
    if (handler == null) {
      throw new GlobalException(HttpStatus.NOT_FOUND, "This user account does not exist");
    }

    // Check if the handler is the owner of the idea to which the request is linked
    if (!request.getIdea().getUser().getId().equals(handler.getId())) {
      throw new GlobalException(HttpStatus.FORBIDDEN, "The handler is not the owner of the idea");
    }

    // Check that the message is not null / empty
    if (message == null || message.isEmpty()) {
      throw new GlobalException(
          HttpStatus.BAD_REQUEST, "Please provide a message to decline this request");
    }

    // Create the response
    CollaborationResponse response = new CollaborationResponse();
    response.setMessage(message);
    response.setStatus(Status.Declined);

    // Set the response in the request
    request.setCollaborationResponse(response);

    // Save the response & request
    response = collaborationResponseRepository.save(response);
    collaborationRequestRepository.save(request);

    // Return the response
    return response;
  }
}
