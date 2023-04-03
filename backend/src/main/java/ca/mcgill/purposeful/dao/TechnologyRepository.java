package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.Technology;
import org.springframework.data.repository.CrudRepository;

/** Repository for Technology */
public interface TechnologyRepository extends CrudRepository<Technology, Integer> {

  /**
   * Find a technology by its id
   *
   * @param id the id of the technology
   * @return the technology
   */
  Technology findTechnologyById(String id);

  /**
   * Find a technology by its name
   *
   * @param name the name of the technology
   * @return the technology
   */
  Technology findTechnologyByName(String name);
  ;
}
