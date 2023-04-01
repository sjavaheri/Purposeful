package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.model.URL;
import ca.mcgill.purposeful.util.DatabaseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * To test the URL CRUD repository methods.
 *
 * @author Wassim Jabbour
 */
@SpringBootTest
public class UrlRepositoryTests {

  @Autowired private URLRepository urlRepository;

  /** Clear the database before all tests */
  @BeforeAll
  public static void clearDatabaseBefore(@Autowired DatabaseUtil util) {
    util.clearDatabase();
  }

  /** Clear the database after each test */
  @AfterEach
  public void clearDatabaseAfter(@Autowired DatabaseUtil util) {
    util.clearDatabase();
  }

  /**
   * Creates a url, saves it in the database and then retrieves it by id and checks that works
   *
   * @author Wassim Jabbour
   */
  @Test
  public void testPersistAndLoadUrlById() {

    // Initialize the url
    String str = "www.test.com";

    // Create a url
    URL url = new URL();
    url.setURL(str);

    // Save the url and extract the id
    urlRepository.save(url);
    String id = url.getId();

    // Set the url back to null
    url = null;

    // Check it can be retrieved
    url = urlRepository.findURLById(id);
    assertNotNull(url);
    assertEquals(str, url.getURL());
    assertEquals(id, url.getId());

    // check a URL can be found by its URL
    url = null;
    url = urlRepository.findURLByURL(str).get(0);
    assertNotNull(url);
    assertEquals(str, url.getURL());
    assertEquals(id, url.getId());


  }
}
