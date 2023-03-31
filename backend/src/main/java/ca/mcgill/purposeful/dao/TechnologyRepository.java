package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Technology;
import org.springframework.data.repository.CrudRepository;

/** Repository for Technology */
public interface TechnologyRepository extends CrudRepository<Technology, Integer> {

  Technology findTechnologyById(String id);
    Technology findTechnologyByName(String name);
}
