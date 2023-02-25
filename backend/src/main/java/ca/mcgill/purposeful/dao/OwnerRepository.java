package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Owner;
import org.springframework.data.repository.CrudRepository;

/** Repository for Owner */
public interface OwnerRepository extends CrudRepository<Owner, Integer> {

  Owner findOwnerById(String id);

  Owner findOwnerByAppUserEmail(String email);
}
