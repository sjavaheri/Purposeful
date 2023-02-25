package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.URL;
import org.springframework.data.repository.CrudRepository;

/** Repository for URL */
public interface URLRepository extends CrudRepository<URL, Integer> {

  URL findURLById(String id);
}
