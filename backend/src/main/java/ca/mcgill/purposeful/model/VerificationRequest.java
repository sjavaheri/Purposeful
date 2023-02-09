package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The Technology class, the model for all Technologies in the database
 */
@Entity
public class VerificationRequest {

  // ------------------------
  // Enumerations
  // ------------------------

  @Id
  private String companyOuiNumber;

  // ------------------------
  // VerificationRequest Attributes
  // ------------------------
  private String companyName;
  private String explanation;
  private String supportingDocumentUrl;
  @Column(nullable = false)
  private Status status;

  public VerificationRequest() {

  }

  // ------------------------
  // VerificationRequest Constructor
  // ------------------------

  public String getCompanyOuiNumber() {
    return companyOuiNumber;
  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  public void setCompanyOuiNumber(String companyOuiNumber) {
    this.companyOuiNumber = companyOuiNumber;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public String getSupportingDocumentUrl() {
    return supportingDocumentUrl;
  }

  public void setSupportingDocumentUrl(String supportingDocumentUrl) {
    this.supportingDocumentUrl = supportingDocumentUrl;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public enum Status {
    Pending, Approved, Refused
  }
}
