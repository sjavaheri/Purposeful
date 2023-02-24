package ca.mcgill.purposeful.model;

import ca.mcgill.purposeful.configuration.Authority;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  //  @Column(unique = true, nullable = false)
  //  private String username;
  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String lastname;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  // ------------------------
  // AppUser Associations
  // ------------------------

  // Every AppUser has a set of Authorities that they can be granted
  //  @ElementCollection(targetClass = Authority.class)
  //  @CollectionTable(name = "app_user_authority", joinColumns = @JoinColumn(name = "app_user_id"))
  //  @Enumerated(EnumType.STRING)
  //  @Column(name = "authorities", nullable = false)
  private Set<Authority> authorities = new HashSet<Authority>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "appUser")
  private List<Role> roles;

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

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
}
