package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * The abstract Role class, the abstract model for all account roles in the database
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Role {

  // ------------------------
  // Role Constructor
  // ------------------------

  public Role() {
  }

}
