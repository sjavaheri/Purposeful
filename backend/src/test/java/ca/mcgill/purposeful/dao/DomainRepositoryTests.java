package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.util.DatabaseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Domain Repository testing class which initiates a domain, executes the tests, then clears the
 * instance from the database.
 *
 * @author Thibaut Baguette
 */
@SpringBootTest
public class DomainRepositoryTests {

  @Autowired private DomainRepository domainRepository;

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
   * Domain testing method which creates, populates the attributes, and save a domain in the
   * repository.
   *
   * @author Thibaut Baguette
   */
  @Test
  public void testPersistAndLoadDomain() {
    // Create new domain
    Domain domain = new Domain();
    String domainName = "Test Domain";
    domain.setName(domainName);

    // Save domain
    domainRepository.save(domain);

    // Get id
    String domainId = domain.getId();

    // Load domain
    Domain loadedDomain = domainRepository.findDomainById(domainId);

    // Assertions
    assertNotNull(loadedDomain);
    assertEquals(domainName, loadedDomain.getName());
  }

  /**
   * Test that a domain cannot be saved with a duplicate name.
   *
   * @author Thibaut Baguette
   */
  @Test
  public void testDuplicateDomain() {
    // Create new domain
    Domain domain = new Domain();
    String domainName = "Test Domain";
    domain.setName(domainName);

    // Save domain
    domainRepository.save(domain);

    // Create new domain
    Domain domain2 = new Domain();
    domain2.setName(domainName);

    // assertions
    assertThrows(DataIntegrityViolationException.class, () -> domainRepository.save(domain2));
  }

  /**
   * Test that a domain cannot be saved with a null name.
   *
   * @author Thibaut Baguette
   */
  @Test
  public void testNullName() {
    // Create new domain
    Domain domain = new Domain();

    // assertions
    assertThrows(DataIntegrityViolationException.class, () -> domainRepository.save(domain));
  }
}
