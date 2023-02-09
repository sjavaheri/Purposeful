package ca.mcgill.purposeful.model;

import java.util.HashSet;
import java.util.Set;

import ca.mcgill.purposeful.configuration.Authority;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

/**
 * The AppUser class, the model for all accounts in the database
 */
@Entity
public class AppUser {

  // ------------------------
  // AppUser Attributes
  // ------------------------

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  // ------------------------
  // AppUser Associations
  // ------------------------

  // Every AppUser has a set of Authorities that they can be granted
  @ElementCollection(targetClass = Authority.class)
  @CollectionTable(name = "appuser_authority", joinColumns = @JoinColumn(name = "appuser_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "authorities", nullable = false)
  private Set<Authority> authorities = new HashSet<Authority>();

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", nullable = false, unique = true)
  private Role role;

  @ManyToMany
  @JoinTable(name = "appuser_domain", joinColumns = @JoinColumn(name = "appuser_id"), inverseJoinColumns = @JoinColumn(name = "domain_id"))
  private Set<Domain> domains;

  // Interests are minimum 2 (2..*). This is enforced in the controller
  @ManyToMany
  @JoinTable(name = "appuser_topic", joinColumns = @JoinColumn(name = "appuser_id"), inverseJoinColumns = @JoinColumn(name = "topic_id"))
  private Set<Topic> interests;

  // ------------------------
  // AppUser Constructor
  // ------------------------

  public AppUser() {
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Set<Domain> getDomains() {
    return domains;
  }

  public void setDomains(Set<Domain> domains) {
    this.domains = domains;
  }

  public Set<Topic> getInterests() {
    return interests;
  }

  public void setInterests(Set<Topic> interests) {
    this.interests = interests;
  }
}
