package ca.mcgill.purposeful.model;

import ca.mcgill.purposeful.configuration.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.GenericGenerator;

/** The AppUser class, the model for all accounts in the database */
@Entity
public class AppUser {

  // ------------------------
  // AppUser Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String lastname;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  // Every AppUser has a set of Authorities that they can be granted
  private Set<Authority> authorities = new HashSet<Authority>();

  /**
   * AppUser constructor
   */
  public AppUser() {}

  /**
   * Returns the id of the AppUser
   * @return the id of the AppUser
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the AppUser
   * @param id the id of the AppUser
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns the email of the AppUser
   * @return the email of the AppUser
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the AppUser
   * @param email the email of the AppUser
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the first name of the AppUser
   * @return the first name of the AppUser
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the first name of the AppUser
   * @param firstname the first name of the AppUser
   */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Returns the last name of the AppUser
   * @return the last name of the AppUser
  */
  public String getLastname() {
    return lastname;
  }

  /**
   * Sets the last name of the AppUser
   * @param lastname the last name of the AppUser
   */
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  /**
   * Returns the password of the AppUser
   * @return the password of the AppUser
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the AppUser
   * @param password the password of the AppUser
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the set of authorities of the AppUser
   * @return the set of authorities of the AppUser
   */
  public Set<Authority> getAuthorities() {
    return authorities;
  }

  /**
   * Sets the set of authorities of the AppUser
   * @param authorities the set of authorities of the AppUser
   */
  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }
}
