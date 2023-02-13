package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.URL;
import ca.mcgill.purposeful.util.Util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * To test the URL CRUD repository methods.
 *
 * @author Wassim Jabbour
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UrlRepositoryTests {

  @Autowired
  private URLRepository urlRepository;

  /**
   * Clear the database before all tests
   */
  @BeforeAll
  public static void clearDatabaseBefore(@Autowired Util util) {
    util.clearDatabase();
  }

  /**
   * Clear the database after each test
   */
  @AfterEach
  public void clearDatabaseAfter(@Autowired Util util) {
    util.clearDatabase();
  }

  /**
   * Creates a url, saves it in the database and then retrieves it by id and
   * checks that works
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
  }

}
