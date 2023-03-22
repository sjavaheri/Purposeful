package ca.mcgill.purposeful.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.model.*;
import ca.mcgill.purposeful.util.DatabaseUtil;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class for testing the CollaborationResponseRepository and the persistence of
 * CollaborationResponse. CollaborationRequestRepository and CollaborationRequest are tested at
 * the same time
 *
 * @author Siger Ma
 */
@SpringBootTest
<<<<<<<< HEAD:backend/src/test/java/ca/mcgill/purposeful/dao/CollaborationResponseRepositoryTests.java
public class CollaborationResponseRepositoryTests {
========
public class CollaborationRepositoryTests {
>>>>>>>> acea776470d1144e30053a46697a9c861f118d62:backend/src/test/java/ca/mcgill/purposeful/dao/CollaborationRepositoryTests.java

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private CollaborationResponseRepository collaborationResponseRepository;

  @Autowired private CollaborationRequestRepository collaborationRequestRepository;

  @Autowired private IdeaRepository ideaRepository;

  @Autowired private RegularUserRepository regularUserRepository;

  @Autowired private URLRepository urlRepository;

  @Autowired private PasswordEncoder passwordEncoder;

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
  public void testPersistAndLoadCollaborationResponse() {
    // Create app user 1
    AppUser user1 = new AppUser();
    user1.setEmail("prof@gmail.com");
    user1.setFirstname("Rob");
    user1.setLastname("Sab");
    user1.setPassword(passwordEncoder.encode("password"));

    // Create corresponding regular user
    RegularUser regUser1 = new RegularUser();
    regUser1.setAppUser(user1);
    regUser1.setVerifiedCompany(false);

    // Create app user 2
    AppUser user2 = new AppUser();
    user2.setEmail("ta@gmail.com");
    user2.setFirstname("Neeraj");
    user2.setLastname("Katiyar");
    user2.setPassword(passwordEncoder.encode("password"));

    // Create corresponding regular user
    RegularUser regUser2 = new RegularUser();
    regUser2.setAppUser(user2);
    regUser2.setVerifiedCompany(false);

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
    idea.setUser(regUser1);

    // Create collaboration request
    CollaborationRequest request = new CollaborationRequest();
    request.setStatus(Status.Pending);
    request.setAdditionalContact("Chat me on Slack URL");
    request.setMessage("I would like to collaborate with you on this idea");
    request.setIdea(idea);
    request.setRequester(regUser2);

    // Save objects to database
    urlRepository.save(url);
    appUserRepository.save(user1);
    regularUserRepository.save(regUser1);
    appUserRepository.save(user2);
    regularUserRepository.save(regUser2);
    ideaRepository.save(idea);
    collaborationRequestRepository.save(request);

    // Assert that the collaboration request is saved
    request = collaborationRequestRepository.findCollaborationRequestById(request.getId());
    assertNotNull(request);
    assertEquals(Status.Pending, request.getStatus());
    assertEquals("Chat me on Slack URL", request.getAdditionalContact());
    assertEquals("I would like to collaborate with you on this idea", request.getMessage());
    assertEquals(idea.getId(), request.getIdea().getId());
    assertEquals(regUser2.getId(), request.getRequester().getId());

    // Create collaboration response
    CollaborationResponse response = new CollaborationResponse();
    response.setAdditionalContact("I prefer Discord URL");
    response.setMessage("Welcome to the team!");
    collaborationResponseRepository.save(response);

    // Update collaboration request
    request.setCollaborationResponse(response);
    request.setStatus(Status.Approved);
    collaborationRequestRepository.save(request);

    // Assert that the collaboration response is saved
    response =
        collaborationResponseRepository.findCollaborationResponseById(response.getId());
    assertNotNull(response);
    assertEquals("I prefer Discord URL", response.getAdditionalContact());
    assertEquals("Welcome to the team!", response.getMessage());

    // Assert that the collaboration request is updated
    request = collaborationRequestRepository.findCollaborationRequestById(request.getId());
    assertNotNull(request);
    assertEquals(Status.Approved, request.getStatus());
    assertEquals(response.getId(), request.getCollaborationResponse().getId());
  }

  @Test
  public void testFindCollaborationRequestsByRequesterAndIdea() {

    // Create app user 1
    AppUser user1 = new AppUser();
    user1.setEmail("prof@gmail.com");
    user1.setFirstname("Rob");
    user1.setLastname("Sab");
    user1.setPassword(passwordEncoder.encode("password"));

    // Create corresponding regular user
    RegularUser regUser1 = new RegularUser();
    regUser1.setAppUser(user1);
    regUser1.setVerifiedCompany(false);

    // Create app user 2
    AppUser user2 = new AppUser();
    user2.setEmail("ta@gmail.com");
    user2.setFirstname("Neeraj");
    user2.setLastname("Katiyar");
    user2.setPassword(passwordEncoder.encode("password"));

    // Create corresponding regular user
    RegularUser regUser2 = new RegularUser();
    regUser2.setAppUser(user2);
    regUser2.setVerifiedCompany(false);

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
    idea.setUser(regUser1);

    // Create collaboration request
    CollaborationRequest request = new CollaborationRequest();
    request.setStatus(Status.Pending);
    request.setAdditionalContact("Chat me on Slack URL");
    request.setMessage("I would like to collaborate with you on this idea");
    request.setIdea(idea);
    request.setRequester(regUser2);

    // Save objects to database
    urlRepository.save(url);
    appUserRepository.save(user1);
    regularUserRepository.save(regUser1);
    appUserRepository.save(user2);
    regularUserRepository.save(regUser2);
    ideaRepository.save(idea);
    collaborationRequestRepository.save(request);

    // Assert that the collaboration request can be retrieved by user and idea
    List<CollaborationRequest> requests = collaborationRequestRepository.findCollaborationRequestsByRequesterAndIdea(regUser2, idea);
    assertNotNull(requests);
    assertEquals(1, requests.size());
    assertEquals(request.getId(), requests.get(0).getId());
  }
}
