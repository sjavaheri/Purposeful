package ca.mcgill.purposeful.model;

import jakarta.persistence.*;
import java.util.Set;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  // Variable hiding (inherited from Role)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AppUser appUser;

  // ------------------------
  // RegularUser Constructor
  // ------------------------

  /**
   * Default constructor for RegularUser
   */
  public RegularUser() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /**
   * Check if the user is a verified company
   * @return boolean value of verifiedCompany
   */
  public boolean isVerifiedCompany() {
    return verifiedCompany;
  }

    /**
     * Set the verifiedCompany attribute
     * @param verifiedCompany boolean value of verifiedCompany
     */
  public void setVerifiedCompany(boolean verifiedCompany) {
    this.verifiedCompany = verifiedCompany;
  }

  /**
   * Get the verification request
   * @return the verification request of the user
   */
  public VerificationRequest getVerificationRequest() {
    return verificationRequest;
  }

    /**
     * Set the verification request
     * @param verificationRequest the verification request of the user
     */
  public void setVerificationRequest(VerificationRequest verificationRequest) {
    this.verificationRequest = verificationRequest;
  }

  /**
   * Get domains of the user
   * @return the domains of the user
   */
  public Set<Domain> getDomains() {
    return domains;
  }

    /**
     * Set the domains of the user
     * @param domains the domains of the user
     */
  public void setDomains(Set<Domain> domains) {
    this.domains = domains;
  }

    /**
     * Get the interests of the user
     * @return the interests of the user
     */
  public Set<Topic> getInterests() {
    return interests;
  }

  /**
   * Set the interests of the user
   * @param interests the interests of the user
   */
  public void setInterests(Set<Topic> interests) {
    this.interests = interests;
  }
}
