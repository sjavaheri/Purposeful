package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.URL;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

/** Repository for URL */
public interface URLRepository extends CrudRepository<URL, Integer> {

  /**
   * Find a URL by its id
   *
   * @param id the id of the URL
   * @return the URL
   */
  URL findURLById(String id);

  /**
   * Find a URL by its URL
   *
   * @param URL the URL of the URL
   * @return the URL
   */
  ArrayList<URL> findURLByURL(String URL);
}
