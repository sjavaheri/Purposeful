package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * The URL class, the model for all URLs in the database
 */
@Entity
public class URL {

  // ------------------------
  // URL Associations
  // ------------------------

  @ManyToOne(optional = true)
  private Idea idea;

  // ------------------------
  // URL Attributes
  // ------------------------

  @Id
  private String URL;

  // ------------------------
  // URL Constructor
  // ------------------------

  public URL() {

  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public Idea getIdea() {
    return idea;
  }

  public void setIdea(Idea idea) {
    this.idea = idea;
  }
}
