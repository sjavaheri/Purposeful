package ca.mcgill.purposeful.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The URL class, the model for all URLs in the database
 */
@Entity
public class URL {

  // ------------------------
  // URL Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = false, unique = false)
  private String URL;

  // ------------------------
  // URL Associations
  // ------------------------

  // Null when URL is an icon of an idea
  @ManyToOne(optional = true)
  @JoinColumn(name = "idea_id", nullable = true)
  private Idea idea;

  // ------------------------
  // URL Constructor
  // ------------------------

  public URL() {
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
