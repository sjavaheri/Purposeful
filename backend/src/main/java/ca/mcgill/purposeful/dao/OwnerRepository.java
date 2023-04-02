package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Owner;
import org.springframework.data.repository.CrudRepository;

/** Repository for Owner */
public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    /**
     * Find an Owner by id
     * @param id - the id of the Owner
     * @return the Owner with the given id
     */
  Owner findOwnerById(String id);

    /**
     * Find an Owner by email
     * @param email - the email of the Owner
     * @return the Owner with the given email
     */
  Owner findOwnerByAppUserEmail(String email);
}
