package ca.mcgill.purposeful.model;

import jakarta.persistence.*;
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

  @Column(nullable = false)
  private Status status;

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
  @JoinColumn(name = "collaboration_confirmation_id", nullable = true)
  private CollaborationResponse collaborationResponse;

  // ------------------------
  // CollaborationRequest Constructor
  // ------------------------

  public CollaborationRequest() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getAdditionalContact() {
    return additionalContact;
  }

  public void setAdditionalContact(String additionalContact) {
    this.additionalContact = additionalContact;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public RegularUser getRequester() {
    return requester;
  }

  public void setRequester(RegularUser requester) {
    this.requester = requester;
  }

  public Idea getIdea() {
    return idea;
  }

  public void setIdea(Idea idea) {
    this.idea = idea;
  }

  public CollaborationResponse getCollaborationResponse() {
    return collaborationResponse;
  }

  public void setCollaborationResponse(CollaborationResponse collaborationResponse) {
    this.collaborationResponse = collaborationResponse;
  }
}
