package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/** The VerificationRequest class, the model for all verification requests in the database */
@Entity
public class VerificationRequest {

  // ------------------------
  // VerificationRequest Attributes
  // ------------------------

  @Id private String companyOuiNumber;

  @Column(nullable = false)
  private String companyName;

  @Column(nullable = false)
  private String explanation;

  @Column(nullable = false)
  private String supportingDocumentUrl;

  @Column(nullable = false)
  private Status status;

  // ------------------------
  // VerificationRequest Constructor
  // ------------------------

  /**
   * The default constructor for the VerificationRequest class
   */
  public VerificationRequest() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /**
   * Gets the companyOuiNumber of the VerificationRequest
   * @return the companyOuiNumber of the VerificationRequest
   */
  public String getCompanyOuiNumber() {
    return companyOuiNumber;
  }

    /**
     * Sets the companyOuiNumber of the VerificationRequest
     * @param companyOuiNumber the companyOuiNumber of the VerificationRequest
     */
  public void setCompanyOuiNumber(String companyOuiNumber) {
    this.companyOuiNumber = companyOuiNumber;
  }

  /**
   * Gets the companyName of the VerificationRequest
   * @return the companyName of the VerificationRequest
   */
  public String getCompanyName() {
    return companyName;
  }

    /**
     * Sets the companyName of the VerificationRequest
     * @param companyName the companyName of the VerificationRequest
     */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

    /**
     * Gets the explanation of the VerificationRequest
     * @return the explanation of the VerificationRequest
     */
  public String getExplanation() {
    return explanation;
  }

    /**
     * Sets the explanation of the VerificationRequest
     * @param explanation the explanation of the VerificationRequest
     */
  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

    /**
     * Gets the supportingDocumentUrl of the VerificationRequest
     * @return the supportingDocumentUrl of the VerificationRequest
     */
  public String getSupportingDocumentUrl() {
    return supportingDocumentUrl;
  }

    /**
     * Sets the supportingDocumentUrl of the VerificationRequest
     * @param supportingDocumentUrl the supportingDocumentUrl of the VerificationRequest
     */
  public void setSupportingDocumentUrl(String supportingDocumentUrl) {
    this.supportingDocumentUrl = supportingDocumentUrl;
  }

    /**
     * Gets the status of the VerificationRequest
     * @return the status of the VerificationRequest
     */
  public Status getStatus() {
    return status;
  }

    /**
     * Sets the status of the VerificationRequest
     * @param status the status of the VerificationRequest
     */
  public void setStatus(Status status) {
    this.status = status;
  }
}
