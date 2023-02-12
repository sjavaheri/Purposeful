package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.RegularUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test the persistence layer for the RegularUserRepository. Testing reading and writing of objects,
 * attributes and references to the database.
 *
 * @author Siger Ma
 */
@SpringBootTest
public class RegularUserRepositoryTests {

  @Autowired
  private RegularUserRepository regularUserRepository;

  @Autowired
  private AppUserRepository appUserRepository;

  @AfterEach
  public void clearDatabase() {
    appUserRepository.deleteAll();
    regularUserRepository.deleteAll();
  }

  @Test
  public void testPersistRegularUser() {
    // Create a mew AppUser
    AppUser appUser = new AppUser();
    appUser.setEmail("regular.user@email.com");
    appUser.setUsername("RegularUser");
    appUser.setPassword("password");
    appUser.getAuthorities().add(Authority.User);
    appUserRepository.save(appUser);

    // Create a new RegularUser
    RegularUser regularUser = new RegularUser();
    regularUser.setVerifiedCompany(false);
    regularUser.setAppUser(appUser);
    regularUserRepository.save(regularUser);

    // Read the RegularUser from the database
    String regularUserId = regularUser.getId();
    String appUserId = appUser.getId();
    regularUser = null;
    appUser = null;
    regularUser = regularUserRepository.findRegularUserById(regularUserId);
    appUser = appUserRepository.findAppUserById(appUserId);

    // Check that the RegularUser was persisted
    assertNotNull(regularUser);
    assertNotNull(appUser);
    assertEquals(regularUserId, regularUser.getId());
    assertEquals(appUserId, appUser.getId());
    assertEquals(false, regularUser.isVerifiedCompany());
    assertEquals(appUserId, regularUser.getAppUser().getId());
  }
}
