package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * The CollaborationResponse class, the model for regular user's collaboration response to a request
 * in the database
 */
@Entity
public class CollaborationResponse {
  // ------------------------
  // CollaborationResponse Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = true)
  private String additionalContact;

  @Column(nullable = false)
  private String message;

  @Column(nullable = false)
  private Status status;

  // ------------------------
  // CollaborationResponse Constructor
  // ------------------------

  /**
   * Default constructor
   */
  public CollaborationResponse() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /**
   * Get the id of the collaboration response
   * @return the id of the collaboration response
   */
  public String getId() {
    return id;
  }

  /**
   * Set the id of the collaboration response
   * @param id the id of the collaboration response
   */
  public void setId(String id) {
    this.id = id;
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
   * @param additionalContact the additional contact of the collaboration response
   */
  public void setAdditionalContact(String additionalContact) {
    this.additionalContact = additionalContact;
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
   * @param message the message of the collaboration response
   */
  public void setMessage(String message) {
    this.message = message;
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
   * @param status the status of the collaboration response
   */
  public void setStatus(Status status) {
    this.status = status;
  }
}
