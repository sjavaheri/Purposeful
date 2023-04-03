package ca.mcgill.purposeful.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The CollaborationRequest class, the model for regular user's collaboration requests to an idea in
 * the database
 */
@Entity
public class CollaborationRequest {

  // ------------------------
  // CollaborationRequest Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = true)
  private String additionalContact;

  @Column(nullable = false)
  private String message;

  // ------------------------
  // CollaborationRequest Associations
  // ------------------------

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "requester_id", nullable = false)
  private RegularUser requester;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idea_id", nullable = false)
  private Idea idea;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "collaboration_response_id", nullable = true)
  private CollaborationResponse collaborationResponse;

  // ------------------------
  // CollaborationRequest Constructor
  // ------------------------

  /**
   * CollaborationRequest constructor
   */
  public CollaborationRequest() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /**
   * Returns the id of the CollaborationRequest
   * @return the id of the CollaborationRequest
   */
  public String getId() {
    return id;
  }

    /**
     * Sets the id of the CollaborationRequest
     * @param id the id of the CollaborationRequest
     */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns the additional contact of the CollaborationRequest
   * @return the additional contact of the CollaborationRequest
   */
  public String getAdditionalContact() {
    return additionalContact;
  }

  /**
   * Sets the additional contact of the CollaborationRequest
   * @param additionalContact the additional contact of the CollaborationRequest
   */
  public void setAdditionalContact(String additionalContact) {
    this.additionalContact = additionalContact;
  }

  /**
   * Returns the message of the CollaborationRequest
   * @return the message of the CollaborationRequest
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message of the CollaborationRequest
   * @param message the message of the CollaborationRequest
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Returns the requester of the CollaborationRequest
   * @return the requester of the CollaborationRequest
   */
  public RegularUser getRequester() {
    return requester;
  }

  /**
   * Sets the requester of the CollaborationRequest
   * @param requester the requester of the CollaborationRequest
   */
  public void setRequester(RegularUser requester) {
    this.requester = requester;
  }

  /**
   * Returns the idea of the CollaborationRequest
   * @return the idea of the CollaborationRequest
   */
  public Idea getIdea() {
    return idea;
  }

  /**
   * Sets the idea of the CollaborationRequest
   * @param idea the idea of the CollaborationRequest
   */
  public void setIdea(Idea idea) {
    this.idea = idea;
  }

  /**
   * Returns the collaboration response of the CollaborationRequest
   * @return the collaboration response of the CollaborationRequest
   */
  public CollaborationResponse getCollaborationResponse() {
    return collaborationResponse;
  }

  /**
   * Sets the collaboration response of the CollaborationRequest
   * @param collaborationResponse the collaboration response of the CollaborationRequest
   */
  public void setCollaborationResponse(CollaborationResponse collaborationResponse) {
    this.collaborationResponse = collaborationResponse;
  }
}
