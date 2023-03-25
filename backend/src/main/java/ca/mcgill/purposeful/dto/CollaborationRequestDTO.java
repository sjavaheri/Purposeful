package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.CollaborationRequest;

/**
 * Data transfer object for the CollaborationRequest class
 *
 * @author Wassim Jabbour
 */
public class CollaborationRequestDTO {

  private IdeaDTO idea;
  private String message;
  private String additionalContact;

  public CollaborationRequestDTO() {}

  public CollaborationRequestDTO(IdeaDTO idea, String message, String additionalContact) {
    this.idea = idea;
    this.message = message;
    this.additionalContact = additionalContact;
  }

  public CollaborationRequestDTO(CollaborationRequest collaborationRequest) {
    this.idea = new IdeaDTO(collaborationRequest.getIdea());
    this.message = collaborationRequest.getMessage();
    this.additionalContact = collaborationRequest.getAdditionalContact();
  }

  public IdeaDTO getIdea() {
    return idea;
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
}
