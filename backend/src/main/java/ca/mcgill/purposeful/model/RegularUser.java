package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * The RegularUser class, the model for accounts with regular user authority in the database
 */
@Entity
public class RegularUser extends Role {

  // ------------------------
  // RegularUser Attributes
  // ------------------------

  @Column(nullable = false)
  private boolean verifiedCompany;

  // ------------------------
  // RegularUser Constructor
  // ------------------------

  public RegularUser() {
  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  public boolean isVerifiedCompany() {
    return verifiedCompany;
  }

  public void setVerifiedCompany(boolean verifiedCompany) {
    this.verifiedCompany = verifiedCompany;
  }

}
