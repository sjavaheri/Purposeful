package ca.mcgill.purposeful.dto;

/**
 * Data transfer object for the CollaborationResponseInformation class
 */
public class CollaborationResponseInformationDTO {

  private String collaborationRequestId;
  private String message;
  private String additionalContact;

  /** Default constructor */
  public CollaborationResponseInformationDTO() {}

  /**
   * Constructor
   *
   * @param collaborationRequestId Collaboration request id
   * @param message Message
   * @param additionalContact Additional contact
   */
  public CollaborationResponseInformationDTO(
      String collaborationRequestId, String message, String additionalContact) {
    this.collaborationRequestId = collaborationRequestId;
    this.message = message;
    this.additionalContact = additionalContact;
  }

  /**
   * Get collaboration request id
   * @return collaboration request id
   */
  public String getCollaborationRequestId() {
    return collaborationRequestId;
  }

    /**
     * Set collaboration request id
     * @param collaborationRequestId collaboration request id
     */
  public void setCollaborationRequestId(String collaborationRequestId) {
    this.collaborationRequestId = collaborationRequestId;
  }

    /**
     * Get message
     * @return message
     */
  public String getMessage() {
    return message;
  }

    /**
     * Set message
     * @param message message
     */
  public void setMessage(String message) {
    this.message = message;
  }

    /**
     * Get additional contact
     * @return additional contact
     */
  public String getAdditionalContact() {
    return additionalContact;
  }

    /**
     * Set additional contact
     * @param additionalContact additional contact
     */
  public void setAdditionalContact(String additionalContact) {
    this.additionalContact = additionalContact;
  }
}
