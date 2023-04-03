package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.VerificationRequest;
import org.springframework.data.repository.CrudRepository;

/** Repository for VerificationRequest */
public interface VerificationRequestRepository
    extends CrudRepository<VerificationRequest, Integer> {

  /**
   * Find a verification request by its id
   *
   * @param companyOuiNumber the id of the verification request
   * @return the verification request
   */
  VerificationRequest findVerificationByCompanyOuiNumber(String companyOuiNumber);
}
