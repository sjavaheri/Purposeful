package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Role;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * AppUser Repository testing class which initiates an appUser with all its
 * attributes, executes the tests, then clears each instance from the database.
 * 
 * @author Sasha Denouvilliez-Pech
 */
@ExtendWith(SpringExtension.class)
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
   * AppUser testing method that creates and persists an AppUser instance
   * with all its fields and all its associations persisted in the database.
   */
  @Test
  public void testPersistAndLoadAppUser() {

    // create the appUser and fill its properties
    AppUser appUser = new AppUser();
    Set<Authority> authorities = new HashSet<Authority>();
    authorities.add(Authority.Owner);
    appUser.setAuthorities(authorities);
    appUser.setEmail("peter.griffin@mcgill.ca");
    appUser.setUsername("peterGriffin123");
    appUser.setPassword(passwordEncoder.encode("moderator"));

    // Create a regular user
    var regularUser = new RegularUser();
    regularUser.setVerifiedCompany(false);
    regularUser.setAppUser(appUser);
    var regularUserList = new ArrayList<Role>();
    regularUserList.add(regularUser);
    appUser.setRole(regularUserList);
    appUser = appUserRepository.save(appUser);
    regularUser = regularUserRepository.save(regularUser);

    // Create and persist multiple domains
    var domainSet = new HashSet<Domain>();
    var domainNames = new String[] { "hello", "world", "science" };
    for (String name : domainNames) {
      var domain = new Domain();
      domain.setName(name);
      // save the domain
      domain = domainRepository.save(domain);
      domainSet.add(domain);
    }
    appUser.setDomains(domainSet);

    // Create and persist multiple topics
    var topicSet = new HashSet<Topic>();
    var topicNames = new String[] { "hello", "world", "science" };
    for (String name : topicNames) {
      var topic = new Topic();
      topic.setName(name);
      // save the domain
      topic = topicRepository.save(topic);
      topicSet.add(topic);
    }
    appUser.setInterests(topicSet);

    // save the appUser
    appUser = appUserRepository.save(appUser);

    // Assertions
    var appUserFromDB = appUserRepository.findAppUserById(appUser.getId());
    assertEquals(appUser.getId(), appUserFromDB.getId());
    assertEquals("peterGriffin123", appUserFromDB.getUsername());
    assertEquals(regularUser.getId(), appUser.getRole().get(0).getId());
    assertEquals(domainSet.size(), appUserFromDB.getDomains().size());
    assertEquals(topicSet.size(), appUserFromDB.getInterests().size());
  }
}
