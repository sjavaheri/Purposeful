package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * The abstract Role class, the abstract model for all account roles in the database
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Role {

  // ------------------------
  // Role Attributes
  // ------------------------

  private String id;

  // ------------------------
  // Role Constructor
  // ------------------------

  public Role() {
  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
