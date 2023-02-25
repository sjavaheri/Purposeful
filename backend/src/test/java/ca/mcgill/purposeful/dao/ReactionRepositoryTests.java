package ca.mcgill.purposeful.dao;

import ca.mcgill.purposeful.model.*;
import ca.mcgill.purposeful.util.DatabaseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReactionRepositoryTests {

  @Autowired private IdeaRepository ideaRepository;

  @Autowired private ReactionRepository reactionRepository;

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private RegularUserRepository regularUserRepository;

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

  @Test
  public void testPersistAndLoadReaction() {

    // create basic URL
    URL url = new URL();
    url.setURL("www.url.com");

    // Create basic idea
    Idea idea = new Idea();
    idea.setDate(Date.from(Instant.now()));
    idea.setTitle("Brilliant Idea");
    idea.setPurpose("huge learning experience");
    idea.setDescription("It's a good idea");
    idea.setIconUrl(url);
    ;

    // Create reaction
    Reaction reaction = new Reaction();
    reaction.setReactionType(Reaction.ReactionType.HighFive);
    reaction.setDate(Date.from(Instant.now()));
    reaction.setIdea(idea);

    // Create app user
    AppUser user = new AppUser();
    user.setEmail("friend@gmail.com");
    user.setFirstname("Rob");
    user.setLastname("Sab");
    user.setPassword("person");

    // Create corresponding regular user
    RegularUser regUser = new RegularUser();
    regUser.setAppUser(user);
    regUser.setVerifiedCompany(false);

    // set user for idea
    idea.setUser(regUser);

    // set user for reaction
    reaction.setRegularUser(regUser);

    // Save objects in database in the right order
    urlRepository.save(url);
    appUserRepository.save(user);
    regularUserRepository.save(regUser);
    ideaRepository.save(idea);
    reactionRepository.save(reaction);

    // Retrieve reaction from database
    String reactId = reaction.getId();

    // Test found reaction in database
    Reaction foundReaction = reactionRepository.findReactionById(reactId);

    assertNotNull(foundReaction);
    assertEquals(foundReaction.getId(), reactId);
    assertEquals(foundReaction.getDate().compareTo(reaction.getDate()), 0);
    assertEquals(foundReaction.getIdea().getId(), idea.getId());
  }
}
