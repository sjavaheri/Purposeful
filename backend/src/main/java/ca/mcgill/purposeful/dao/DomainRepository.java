package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Domain;
import org.springframework.data.repository.CrudRepository;

/** Repository for Domain */
public interface DomainRepository extends CrudRepository<Domain, Integer> {

  /**
   * Find a Domain by id
   * 
   * @param id - the id of the Domain
   * @return the Domain with the given id
   */
  Domain findDomainById(String id);

  /**
   * Find a Domain by name
   * 
   * @param name - the name of the Domain
   * @return the Domain with the given name
   */
  Domain findDomainByName(String name);
}
