package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Role;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.util.Util;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AppUser Repository testing class which initiates an appUser with all its attributes, executes the
 * tests, then clears each instance from the database.
 *
 * @author Sasha Denouvilliez-Pech
 */
@SpringBootTest
public class AppUserRepositoryTests {

  @Autowired
  private AppUserRepository appUserRepository;

  // Associations
  @Autowired
  private DomainRepository domainRepository;
  @Autowired
  private TopicRepository topicRepository;
  @Autowired
  private RegularUserRepository regularUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

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
   * AppUser testing method that creates and persists an AppUser instance with all its fields and
   * all its associations persisted in the database.
   */
  @Test
  public void testPersistAndLoadAppUser() {

    // create the appUser and fill its properties
    AppUser appUser = new AppUser();
    Set<Authority> authorities = new HashSet<Authority>();
    authorities.add(Authority.Owner);
    appUser.setAuthorities(authorities);
    appUser.setEmail("peter.griffin@mcgill.ca");
    appUser.setFirstname("peter");
    appUser.setLastname("Griffin");
    appUser.setPassword(passwordEncoder.encode("moderator"));

    // Create a regular user
    var regularUser = new RegularUser();
    regularUser.setVerifiedCompany(false);
    var regularUserList = new ArrayList<Role>();
    regularUserList.add(regularUser);

    // Create and persist multiple domains
    var domainSet = new HashSet<Domain>();
    var domainNames = new String[]{"hello", "world", "science"};
    for (String name : domainNames) {
      var domain = new Domain();
      domain.setName(name);
      // save the domain
      domain = domainRepository.save(domain);
      domainSet.add(domain);
    }
    regularUser.setDomains(domainSet);

    // Create and persist multiple topics
    var topicSet = new HashSet<Topic>();
    var topicNames = new String[]{"hello", "world", "science"};
    for (String name : topicNames) {
      var topic = new Topic();
      topic.setName(name);
      // save the domain
      topic = topicRepository.save(topic);
      topicSet.add(topic);
    }
    regularUser.setInterests(topicSet);

    regularUser.setAppUser(appUser);
    appUser.setRole(regularUserList);
    // save the appUser
    appUser = appUserRepository.save(appUser);
    regularUser = regularUserRepository.save(regularUser);

    // Assertions
    var appUserFromDB = appUserRepository.findAppUserById(appUser.getId());
    var regularUserFromDB = regularUserRepository.findRegularUserById(
        appUserFromDB.getRole().get(0).getId());
    assertEquals(appUser.getId(), appUserFromDB.getId());
    assertEquals("peter", appUserFromDB.getFirstname());
    assertEquals("Griffin", appUserFromDB.getLastname());
    assertEquals(regularUser.getId(), regularUserFromDB.getId());
    assertEquals(domainSet.size(), regularUserFromDB.getDomains().size());
    assertEquals(topicSet.size(), regularUserFromDB.getInterests().size());
  }
}
