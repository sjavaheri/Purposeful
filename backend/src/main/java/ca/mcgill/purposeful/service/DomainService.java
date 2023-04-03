package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Domain;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** Service functions of the Reaction class */
@Service
public class DomainService {

  /*
   * CRUD repos
   */

  @Autowired DomainRepository domainRepository;

  /**
   * Method to retrieve all existing domains from the database.
   *
   * @return the domains in the database.
   * @author Adam Kazma
   */
  @Transactional
  public List<Domain> getAllDomains() {

    List<Domain> domains = (List<Domain>) domainRepository.findAll();

    if (domains == null) {
      throw new GlobalException(HttpStatus.NOT_FOUND, "Could not fetch domains from database.");
    }

    return domains;
  }
}
