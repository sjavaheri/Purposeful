package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

/**
 * The Technology class, the model for all technologies in the database
 */
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
  // Technology Associations
  // ------------------------

  @ManyToMany
  Set<Idea> ideas;

  // ------------------------
  // Technology Constructor
  // ------------------------

  public Technology() {
  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
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

  public Set<Idea> getIdeas() {
    return ideas;
  }

  public void setIdeas(Set<Idea> ideas) {
    this.ideas = ideas;
  }
}
