package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/** The Technology class, the model for all technologies in the database */
@Entity
public class Technology {

  // ------------------------
  // Technology Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  // ------------------------
  // Technology Constructor
  // ------------------------

  /** Default constructor for the Technology class */
  public Technology() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /**
   * Get the id of the technology
   *
   * @return the id of the technology
   */
  public String getId() {
    return id;
  }

  /**
   * Set the id of the technology
   *
   * @param id the id of the technology
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get the name of the technology
   *
   * @return the name of the technology
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the technology
   *
   * @param name the name of the technology
   */
  public void setName(String name) {
    this.name = name;
  }
}
