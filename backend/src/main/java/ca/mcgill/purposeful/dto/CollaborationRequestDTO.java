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
  private IdeaDTO idea;
  private String message;
  private String additionalContact;
  private boolean hasResponse;

  public CollaborationRequestDTO() {}

  public CollaborationRequestDTO(
      String id, IdeaDTO idea, String message, String additionalContact, boolean hasResponse) {
    this.id = id;
    this.idea = idea;
    this.message = message;
    this.additionalContact = additionalContact;
    this.hasResponse = hasResponse;
  }

  public CollaborationRequestDTO(CollaborationRequest collaborationRequest) {
    this.id = collaborationRequest.getId();
    this.idea = new IdeaDTO(collaborationRequest.getIdea());
    this.message = collaborationRequest.getMessage();
    this.additionalContact = collaborationRequest.getAdditionalContact();
    this.hasResponse = collaborationRequest.getCollaborationResponse() != null;
  }

  public IdeaDTO getIdea() {
    return idea;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setIdea(IdeaDTO idea) {
    this.idea = idea;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getAdditionalContact() {
    return additionalContact;
  }

  public void setAdditionalContact(String additionalContact) {
    this.additionalContact = additionalContact;
  }

  public boolean isHasResponse() {
    return hasResponse;
  }

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
