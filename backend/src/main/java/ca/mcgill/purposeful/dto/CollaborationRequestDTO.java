package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.CollaborationRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for the CollaborationRequest class
 *
 * @author Wassim Jabbour
 */
public class CollaborationRequestDTO {

  private String id;
  private String ideaId;
  private String message;
  private String additionalContact;
  private boolean hasResponse;

  /**
   * Default constructor for CollaborationRequestDTO
   */
  public CollaborationRequestDTO() {}

  /**
   * Constructor for CollaborationRequestDTO
   * @param id id of the collaboration request
   * @param ideaId id of the idea
   * @param message message of the collaboration request
   * @param additionalContact additional contacts
   * @param hasResponse whether the collaboration request has a response
   */
  public CollaborationRequestDTO(
      String id, String ideaId, String message, String additionalContact, boolean hasResponse) {
    this.id = id;
    this.ideaId = ideaId;
    this.message = message;
    this.additionalContact = additionalContact;
    this.hasResponse = hasResponse;
  }

    /**
     * Constructor for CollaborationRequestDTO
     * @param collaborationRequest collaboration request
     */
  public CollaborationRequestDTO(CollaborationRequest collaborationRequest) {
    this.id = collaborationRequest.getId();
    this.ideaId = collaborationRequest.getIdea().getId();
    this.message = collaborationRequest.getMessage();
    this.additionalContact = collaborationRequest.getAdditionalContact();
    this.hasResponse = collaborationRequest.getCollaborationResponse() != null;
  }

  /**
   * Get the id of the collaboration request
   * @return id of the collaboration request
   */
  public String getId() {
    return id;
  }

    /**
     * Set the id of the collaboration request
     * @param id id of the collaboration request
     */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get the id of the idea
   * @return  id of the idea
   */
  public String getIdeaId() {
    return ideaId;
  }

    /**
     * Set the id of the idea
     * @param ideaId id of the idea
     */
  public void setIdeaId(String ideaId) {
    this.ideaId = ideaId;
  }

    /**
     * Get the message of the collaboration request
     * @return message of the collaboration request
     */
  public String getMessage() {
    return message;
  }

    /**
     * Set the message of the collaboration request
     * @param message message of the collaboration request
     */
  public void setMessage(String message) {
    this.message = message;
  }

    /**
     * Get the additional contacts
     * @return additional contacts
     */
  public String getAdditionalContact() {
    return additionalContact;
  }

    /**
     * Set the additional contacts
     * @param additionalContact additional contacts
     */
  public void setAdditionalContact(String additionalContact) {
    this.additionalContact = additionalContact;
  }

    /**
     * Get whether the collaboration request has a response
     * @return whether the collaboration request has a response
     */
  public boolean isHasResponse() {
    return hasResponse;
  }

    /**
     * Set whether the collaboration request has a response
     * @param hasResponse whether the collaboration request has a response
     */
  public void setHasResponse(boolean hasResponse) {
    this.hasResponse = hasResponse;
  }


  /**
   * Converts a list of collaboration requests to DTOs
   *
   * @param collaborationRequests list of collaboration requests
   * @return list of collaboration request DTOs
   * @author Wassim Jabbour
   */
  public static List<CollaborationRequestDTO> convertToDTO(
      List<CollaborationRequest> collaborationRequests) {
    ArrayList<CollaborationRequestDTO> dtoList = new ArrayList<>();

    for (CollaborationRequest req : collaborationRequests) {
      dtoList.add(new CollaborationRequestDTO(req));
    }

    return dtoList;
  }


}
