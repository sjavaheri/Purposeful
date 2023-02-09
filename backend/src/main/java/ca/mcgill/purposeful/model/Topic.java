package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The Topic class, the model for topics in the database
 */
@Entity
public class Topic {

  // ------------------------
  // Topic Attributes
  // ------------------------

  private String id;

  private String name;

  // ------------------------
  // Topic Constructor
  // ------------------------

  public Topic() {
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

  @Column(nullable = false, unique = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
