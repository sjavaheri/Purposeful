package ca.mcgill.purposeful.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * The Topic class, the model for topics in the database
 */
@Entity
public class Topic {

  // ------------------------
  // Topic Attributes
  // ------------------------

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  // ------------------------
  // Topic Associations
  // ------------------------

  @ManyToMany
  private Set<AppUser> appUsers;

  @ManyToMany
  private Set<Idea> ideas;

  // ------------------------
  // Topic Constructor
  // ------------------------

  public Topic() {
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

  public Set<AppUser> getAppUsers() {
    return appUsers;
  }

  public void setAppUsers(Set<AppUser> appUsers) {
    this.appUsers = appUsers;
  }

  public Set<Idea> getIdeas() {
    return ideas;
  }

  public void setIdeas(Set<Idea> ideas) {
    this.ideas = ideas;
  }
}
