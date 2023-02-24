package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.URL;
import ca.mcgill.purposeful.util.DatabaseUtil;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class IdeaRepositoryTests {

  @Autowired
  private IdeaRepository ideaRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private URLRepository urlRepository;

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private RegularUserRepository regularUserRepository;

  /**
   * Clear the database before all tests
   */
  @BeforeAll
  public static void clearDatabaseBefore(@Autowired DatabaseUtil util) {
    util.clearDatabase();
  }

  /**
   * Clear the database after each test
   */
  @AfterEach
  public void clearDatabaseAfter(@Autowired DatabaseUtil util) {
    util.clearDatabase();
  }

  @Test
  public void testPersistAndLoadIdea() {

    // MANDATORY CLASS TESTS

    URL url = new URL();
    url.setURL("testURL");
    url.setPresetIcon(false);

    AppUser user = new AppUser();
    user.setEmail("test@mail.purposeful.com");
    user.setFirstname("Rob");
    user.setLastname("Sab");
    user.setPassword(passwordEncoder.encode("something"));

    RegularUser regUser = new RegularUser();
    regUser.setAppUser(user);
    regUser.setVerifiedCompany(false);

    // create the idea and set its attributes
    Idea idea = new Idea();
    idea.setDate(Date.from(Instant.now()));
    idea.setTitle("my idea");
    idea.setDescription("This is a test idea.");
    idea.setPurpose("testing");
    idea.setIconUrl(url);
    idea.setUser(regUser);
    // save the idea
    urlRepository.save(url);
    appUserRepository.save(user);
    regularUserRepository.save(regUser);
    ideaRepository.save(idea);

    // get the ideaId then save it to a variable
    String ideaId = idea.getId();

    // set other copy of idea to null
    Idea idea2 = null;

    // get the idea back from the database as idea2 using the Id
    idea2 = ideaRepository.findIdeaById(ideaId);

    // make sure idea2 is not null
    assertNotNull(idea2);

    // make sure the database idea's attributes correspond to the created idea
    // attributes
    assertEquals(idea2.getId(), ideaId);
    assertEquals(idea2.getDate().compareTo(idea.getDate()), 0);
    assertEquals(idea2.getTitle(), idea.getTitle());
    assertEquals(idea2.getDescription(), idea.getDescription());
    assertEquals(idea2.getPurpose(), idea.getPurpose());
    assertEquals(idea2.getUser().getId(), idea.getUser().getId());
    assertEquals(idea2.getIconUrl().getURL(), idea.getIconUrl().getURL());
  }
}
