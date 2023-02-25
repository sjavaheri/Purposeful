package ca.mcgill.purposeful.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

/** The RegularUser class, the model for accounts with regular user authority in the database */
@Entity
public class RegularUser extends Role {

  // ------------------------
  // RegularUser Attributes
  // ------------------------

  @Column(nullable = false)
  private boolean verifiedCompany;

  // ------------------------
  // RegularUser Associations
  // ------------------------

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "regular_user_domain",
      joinColumns = @JoinColumn(name = "app_user_id"),
      inverseJoinColumns = @JoinColumn(name = "domain_id"))
  private Set<Domain> domains;

  // Interests are minimum 2 (2..*). This is enforced in the controller
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "regular_user_topic",
      joinColumns = @JoinColumn(name = "app_user_id"),
      inverseJoinColumns = @JoinColumn(name = "topic_id"))
  private Set<Topic> interests;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "verification_request_id", nullable = true, unique = true)
  private VerificationRequest verificationRequest;

  @OnDelete(action = OnDeleteAction.CASCADE)
  private AppUser appUser;

  // ------------------------
  // RegularUser Constructor
  // ------------------------

  public RegularUser() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  public boolean isVerifiedCompany() {
    return verifiedCompany;
  }

  public void setVerifiedCompany(boolean verifiedCompany) {
    this.verifiedCompany = verifiedCompany;
  }

  public VerificationRequest getVerificationRequest() {
    return verificationRequest;
  }

  public void setVerificationRequest(VerificationRequest verificationRequest) {
    this.verificationRequest = verificationRequest;
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
