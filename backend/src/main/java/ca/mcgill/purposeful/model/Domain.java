package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/** The Domain class, the model for domains in the database */
@Entity
public class Domain {

  // ------------------------
  // Domain Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  // ------------------------
  // Domain Constructor
  // ------------------------

  /** Default constructor */
  public Domain() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /** Get the id of the domain
   * @return the id of the domain
   * */
  public String getId() {
    return id;
  }

  /** Set the id of the domain
   * @param id the id of the domain
   * */
  public void setId(String id) {
    this.id = id;
  }

  /** Get the name of the domain
   * @return the name of the domain
   * */
  public String getName() {
    return name;
  }

  /** Set the name of the domain
   * @param name the name of the domain
   * */
  public void setName(String name) {
    this.name = name;
  }
}
