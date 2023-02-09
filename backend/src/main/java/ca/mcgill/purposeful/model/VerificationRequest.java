package ca.mcgill.purposeful.model;

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

  private String companyOuiNumber;

  // ------------------------
  // VerificationRequest Attributes
  // ------------------------
  private String companyName;
  private String explanation;
  private String supportingDocumentUrl;
  private Status status;

  public VerificationRequest() {

  }

  // ------------------------
  // VerificationRequest Constructor
  // ------------------------

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
  @Id

  public String getCompanyOuiNumber() {
    return companyOuiNumber;
  }

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

  public enum Status {Pending, Approved, Refused}
}
