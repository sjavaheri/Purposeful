package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Domain;
import org.springframework.data.repository.CrudRepository;

/** Repository for Domain */
public interface DomainRepository extends CrudRepository<Domain, Integer> {

  Domain findDomainById(String id);
    Domain findDomainByName(String name);
}
