package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/** The Topic class, the model for topics in the database */
@Entity
public class Topic {

  // ------------------------
  // Topic Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  // ------------------------
  // Topic Constructor
  // ------------------------

  /** Default constructor of the Topic class */
  public Topic() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
  /**
   * Getter method for the id attribute
   *
   * @return the id of the topic
   */
  public String getId() {
    return id;
  }

  /**
   * Setter method for the id attribute
   *
   * @param id the id of the topic
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Getter method for the name attribute
   *
   * @return the name of the topic
   */
  public String getName() {
    return name;
  }

  /**
   * Setter method for the name attribute
   *
   * @param name the name of the topic
   */
  public void setName(String name) {
    this.name = name;
  }
}
