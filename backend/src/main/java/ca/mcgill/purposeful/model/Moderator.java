package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** The Moderator class, the model for accounts with moderator authority in the database */
@Entity
public class Moderator extends Role {

  // ------------------------
  // Moderator Attributes
  // ------------------------

  // ------------------------
  // Moderator Associations
  // ------------------------

  // Variable hiding (inherited from Role)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AppUser appUser;

  // ------------------------
  // Moderator Constructor
  // ------------------------

  /** Default constructor for the Moderator class */
  public Moderator() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

}
