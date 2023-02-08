package ca.mcgill.purposeful.model;

import java.util.HashSet;
import java.util.Set;

import ca.mcgill.purposeful.configuration.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * The AppUser class, the model for all accounts in the database
 */
@Entity
public class AppUser {

  // ------------------------
  // AppUser Attributes
  // ------------------------

  private String email;

  private String username;

  private String password;

  // Every AppUser has a set of Authorities that they can be granted
  private Set<Authority> authorities = new HashSet<Authority>();

  // ------------------------
  // AppUser Constructor
  // ------------------------

  public AppUser() {
  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  @Id
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(unique = true, nullable = false)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @OneToMany(fetch = FetchType.EAGER)
  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

}
