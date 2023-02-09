package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The Technology class, the model for all Technologies in the database
 */
@Entity
public class Technology {

  // ------------------------
  // Idea Attributes
  // ------------------------

  private String id;

  private String name;

  // ------------------------
  // Idea Constructor
  // ------------------------

  public Technology() {

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
