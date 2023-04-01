package ca.mcgill.purposeful.dto;

public class CollaborationResponseInformationDTO {

  private String collaborationRequestId;
  private String message;
  private String additionalContact;

  /** Default constructor */
  public CollaborationResponseInformationDTO() {}

  public CollaborationResponseInformationDTO(
      String collaborationRequestId, String message, String additionalContact) {
    this.collaborationRequestId = collaborationRequestId;
    this.message = message;
    this.additionalContact = additionalContact;
  }

  public String getCollaborationRequestId() {
    return collaborationRequestId;
  }

  public void setCollaborationRequestId(String collaborationRequestId) {
    this.collaborationRequestId = collaborationRequestId;
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
