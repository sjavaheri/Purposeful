package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The URL class, the model for all URLs in the database
 */
@Entity
public class URL {

  // ------------------------
  // URL Attributes
  // ------------------------

  private String URL;

  // ------------------------
  // URL Constructor
  // ------------------------

  public URL() {

  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
  @Id
  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }
}
