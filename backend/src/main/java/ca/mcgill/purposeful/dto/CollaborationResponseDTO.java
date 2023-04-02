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
   * @param status - the status of the collaboration response
   *
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
    this.status = collaborationResponse.getStatus();
  }

  /**
   * Get the id of the collaboration response
   * @return the id of the collaboration response
   */
  public String getId() {
    return id;
  }

  /**
   * Set the id of the collaboration response
   * @param id - the id of the collaboration response
   */
  public void setId(String id) {
    this.id = id;
  }

    /**
     * Get the message of the collaboration response
     * @return the message of the collaboration response
     */
  public String getMessage() {
    return message;
  }

    /**
     * Set the message of the collaboration response
     * @param message - the message of the collaboration response
     */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Get the additional contact of the collaboration response
   * @return the additional contact of the collaboration response
   */
  public String getAdditionalContact() {
    return additionalContact;
  }

    /**
     * Set the additional contact of the collaboration response
     * @param additionalContact - the additional contact of the collaboration response
     */
  public void setAdditionalContact(String additionalContact) {
    this.additionalContact = additionalContact;
  }

  /**
   * Get the status of the collaboration response
   * @return the status of the collaboration response
   */
  public Status getStatus() {
    return status;
  }

    /**
     * Set the status of the collaboration response
     * @param status - the status of the collaboration response
     */
  public void setStatus(Status status) {
    this.status = status;
  }
}
