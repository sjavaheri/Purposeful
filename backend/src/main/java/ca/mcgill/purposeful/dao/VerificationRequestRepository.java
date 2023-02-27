package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.VerificationRequest;
import org.springframework.data.repository.CrudRepository;

/** Repository for VerificationRequest */
public interface VerificationRequestRepository
    extends CrudRepository<VerificationRequest, Integer> {

  VerificationRequest findVerificationByCompanyOuiNumber(String companyOuiNumber);
}
