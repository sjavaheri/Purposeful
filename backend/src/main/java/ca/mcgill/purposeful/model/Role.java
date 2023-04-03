package ca.mcgill.purposeful.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/** The abstract Role class, the abstract model for all account roles in the database */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Role {

  // ------------------------
  // Role Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  // ------------------------
  // Role Associations
  // ------------------------

  @ManyToOne(optional = false)
  @JoinColumn(name = "app_user_id", nullable = false)
  private AppUser appUser;

  // ------------------------
  // Role Constructor
  // ------------------------
  /** Default constructor */
  public Role() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
  /**
   * Get the id of the role
   *
   * @return the id of the role
   */
  public String getId() {
    return id;
  }

  /**
   * Set the id of the role
   *
   * @param id the id of the role
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get the appUser of the role
   *
   * @return the appUser of the role
   */
  public AppUser getAppUser() {
    return appUser;
  }

  /**
   * Set the appUser of the role
   *
   * @param appUser the appUser of the role
   */
  public void setAppUser(AppUser appUser) {
    this.appUser = appUser;
  }
}
