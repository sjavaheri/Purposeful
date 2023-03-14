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

  // ------------------------
  // AppUser Constructor
  // ------------------------

  public AppUser() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }
}
