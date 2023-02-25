package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** The Owner class, the model for accounts with owner authority in the database */
@Entity
public class Owner extends Role {

  // ------------------------
  // Owner Attributes
  // ------------------------

  // ------------------------
  // Owner Associations
  // ------------------------

  @OnDelete(action = OnDeleteAction.CASCADE)
  private AppUser appUser;

  // ------------------------
  // Owner Constructor
  // ------------------------

  public Owner() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

}
