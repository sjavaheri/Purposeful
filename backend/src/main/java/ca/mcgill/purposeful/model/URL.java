package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/** The URL class, the model for all URLs in the database */
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

  @Column(nullable = false)
  private boolean presetIcon;

  // ------------------------
  // URL Constructor
  // ------------------------

  /** Default constructor of the URL class */
  public URL() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /**
   * Getter method for the id attribute
   *
   * @return the id of the URL
   */
  public String getId() {
    return id;
  }

  /**
   * Setter method for the id attribute
   *
   * @param id the id of the URL
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Getter method for the URL attribute
   *
   * @return the URL
   */
  public String getURL() {
    return URL;
  }

  /**
   * Setter method for the URL attribute
   *
   * @param URL the URL
   */
  public void setURL(String URL) {
    this.URL = URL;
  }

  /**
   * Getter method for the presetIcon attribute
   *
   * @return true if the URL has a preset icon, false otherwise
   */
  public boolean isPresetIcon() {
    return presetIcon;
  }

  /**
   * Setter method for the presetIcon attribute
   *
   * @param presetIcon true if the URL has a preset icon, false otherwise
   */
  public void setPresetIcon(boolean presetIcon) {
    this.presetIcon = presetIcon;
  }
}
