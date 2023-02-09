package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;

/**
 * The Technology class, the model for all Technologies in the database
 */
@Entity
public class Technology {

  // ------------------------
  // Technology Associations
  // ------------------------

  @ManyToMany(mappedBy = "techs")
  Set<Idea> ideas;

  // ------------------------
  // Technology Attributes
  // ------------------------

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private String name;

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
