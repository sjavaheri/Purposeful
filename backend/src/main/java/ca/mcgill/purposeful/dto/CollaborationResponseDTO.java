package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Status;

/**
 * Data transfer object for the CollaborationResponse class
 *
 * @author Thibaut Baguette
 */
public class CollaborationResponseDTO {

  private String id;
  private String message;
  private String additionalContact;
  private Status status;

  /** Default constructor */
  public CollaborationResponseDTO() {}

  /**
   * Constructor for CollaborationResponseDTO
   *
   * @param id - the id of the collaboration response
   * @param message - the message of the collaboration response
   * @param additionalContact - the additional contact of the collaboration response
   * @author Thibaut Baguette
   */
  public CollaborationResponseDTO(
      String id, String message, String additionalContact, Status status) {
    this.id = id;
    this.message = message;
    this.additionalContact = additionalContact;
    this.status = status;
  }

  /**
   * Constructor for CollaborationResponseDTO
   *
   * @param collaborationResponse - the collaboration response
   * @author Thibaut Baguette
   */
  public CollaborationResponseDTO(CollaborationResponse collaborationResponse) {
    this.id = collaborationResponse.getId();
    this.message = collaborationResponse.getMessage();
    this.additionalContact = collaborationResponse.getAdditionalContact();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
