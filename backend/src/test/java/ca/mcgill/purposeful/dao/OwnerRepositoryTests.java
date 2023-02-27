package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Owner;
import ca.mcgill.purposeful.util.DatabaseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Owner Repository testing class which initiates an owner and an AppUser repository, executes the
 * tests, then clears each instance from the database.
 */
@SpringBootTest
public class OwnerRepositoryTests {

  // the repository we are testing
  @Autowired private OwnerRepository ownerRepository;

  // also create an appUser
  @Autowired private AppUserRepository appUserRepository;

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
   * Owner testing method which creates, populates the attributes, sets associations, and saves each
   * owner and appUser object and identifier. It can then test to make sure each object reached from
   * the owner found in the repository is not null and that each initially saved Id corresponds to
   * the one reached from the repository.
   */
  @Test
  public void testPersistAndLoadOwner() {

    // MANDATORY CLASS TESTS

    // create the appUser and fill its properties
    AppUser appUser = new AppUser();

    Set<Authority> authorities = new HashSet<Authority>();
    authorities.add(Authority.Owner);

    appUser.setAuthorities(authorities);
    appUser.setEmail("peter.griffin@mcgill.ca");
    appUser.setFirstname("Peter");
    appUser.setLastname("Griffin");
    appUser.setPassword("verySecurePassword123");
    appUser.setAuthorities(authorities);

    // save the appUser
    appUserRepository.save(appUser);

    // create the owner
    Owner owner = new Owner();
    owner.setAppUser(appUser);

    // save the owner
    ownerRepository.save(owner);

    // get the id of owner and appUser then save into variables
    String ownerId = owner.getId();
    String appUserId = appUser.getId();

    // set owner and appUser to null
    owner = null;
    appUser = null;

    // get the owner back from the database using the Id
    owner = ownerRepository.findOwnerById(ownerId);

    // make sure owner and appUser are not null
    assertNotNull(owner);
    assertNotNull(owner.getAppUser());

    // make sure the created ownerId and appUserId match those in the database
    assertEquals(ownerId, owner.getId());
    assertEquals(appUserId, owner.getAppUser().getId());
  }
}
