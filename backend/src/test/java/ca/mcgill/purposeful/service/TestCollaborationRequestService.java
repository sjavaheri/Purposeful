package ca.mcgill.purposeful.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

/**
 * Test the collaboration request service
 *
 * @author Wassim Jabbour
 */
@ExtendWith(MockitoExtension.class)
public class TestCollaborationRequestService {

  @Mock private CollaborationRequestRepository collaborationRequestRepository;
  @Mock private RegularUserRepository regularUserRepository;
  @Mock private IdeaService ideaService;
  @InjectMocks private CollaborationRequestService collaborationRequestService;

  // Set the mock output of each function in the repository
  @BeforeEach
  public void setMockOutput() {
    lenient()
        .when(collaborationRequestRepository.save(any(CollaborationRequest.class)))
        .thenAnswer(MockRepository::save);
    lenient().when(ideaService.getIdeaById(anyString())).thenAnswer(MockRepository::getIdeaById);
    lenient()
        .when(regularUserRepository.findRegularUserByAppUserEmail(anyString()))
        .thenAnswer(MockRepository::findRegularUserByAppUserEmail);
    lenient()
        .when(
            collaborationRequestRepository.findCollaborationRequestsByRequesterAndIdea(
                any(RegularUser.class), any(Idea.class)))
        .thenAnswer(MockRepository::findCollaborationRequestsByRequesterAndIdea);
    lenient()
        .when(collaborationRequestRepository.findCollaborationRequestsByIdea(any(Idea.class)))
        .thenAnswer(MockRepository::findCollaborationRequestsByIdea);
  }

  // Test the success case
  @Test
  public void testSendCollaborationRequest_Success() {
    try {

      // Send a request between 2 users that have nothing between them yet
      CollaborationRequest collabReq =
          collaborationRequestService.sendCollaborationRequest(
              MockDatabase.appUser2.getEmail(),
              MockDatabase.idea1.getId(),
              "Test message",
              "Test additional contact");

      // Ensure the correct collaboration request was created and returned
      assertNotNull(collabReq);
      assertEquals(collabReq.getRequester().getId(), MockDatabase.user2.getId());
      assertEquals(collabReq.getIdea().getId(), MockDatabase.idea1.getId());
      assertEquals(collabReq.getMessage(), "Test message");
      assertEquals(collabReq.getAdditionalContact(), "Test additional contact");

    } catch (GlobalException e) {
      fail(); // Shouldn't get here
    }
  }

  // Test the failure case of sending a request to yourself
  @Test
  public void testSendCollaborationRequest_SelfRequest() {
    try {

      // Send a request between 2 users that have nothing between them yet
      collaborationRequestService.sendCollaborationRequest(
          MockDatabase.appUser1.getEmail(),
          MockDatabase.idea1.getId(),
          "Test message",
          "Test additional contact");

      // Shouldn't succeed
      fail();

    } catch (GlobalException e) {
      // Check the correct error message is returned
      assertEquals("Cannot send a collaboration request to oneself", e.getMessage());
    }
  }

  // Test the failure case of sending a request for an idea to which you already sent a request
  @Test
  public void testSendCollaborationRequest_RepeatedRequest() {
    try {

      // Send a request between 2 users that have nothing between them yet
      collaborationRequestService.sendCollaborationRequest(
          MockDatabase.appUser1.getEmail(),
          MockDatabase.idea2.getId(),
          "Test message",
          "Test additional contact");

      // Shouldn't succeed
      fail();

    } catch (GlobalException e) {
      // Check the correct error message is returned
      assertEquals(
          "You can only send one collaboration request to this user for this idea", e.getMessage());
    }
  }

  // Test the success case
  @Test
  public void testGetCollaborationRequestsByIdea_Success() {
    try {

      // Send a request
      List<CollaborationRequest> collabReqs =
          collaborationRequestService.getCollaborationRequestsByIdea(
              MockDatabase.appUser2.getEmail(), MockDatabase.idea2.getId());

      // Ensure no requests are returned
      assertNotNull(collabReqs);
      assertEquals(1, collabReqs.size());
      assertEquals(collabReqs.get(0).getId(), MockDatabase.collaborationRequest1.getId());

    } catch (GlobalException e) {
      fail(); // Shouldn't get here
    }
  }

  // Test the alternative case
  @Test
  public void testGetCollaborationRequestsByIdea_NoRequests() {
    try {

      // Send a request
      List<CollaborationRequest> collabReqs =
          collaborationRequestService.getCollaborationRequestsByIdea(
              MockDatabase.appUser1.getEmail(), MockDatabase.idea1.getId());

      // Ensure no requests are returned
      assertNotNull(collabReqs);
      assertEquals(0, collabReqs.size());

    } catch (GlobalException e) {
      fail(); // Shouldn't get here
    }
  }

  // Test the failure case
  @Test
  public void testGetCollaborationRequestsByIdea_NotOwner() {
    try {

      // Send a request
      List<CollaborationRequest> collabReqs =
          collaborationRequestService.getCollaborationRequestsByIdea(
              MockDatabase.appUser2.getEmail(), MockDatabase.idea1.getId());

      // Shouldn't succeed
      fail();

    } catch (GlobalException e) {
      // Check the correct error message is returned
      assertEquals(
          "Only the owner of the idea can view its collaboration requests", e.getMessage());
    }
  }

  /** This class holds all of the mock objects of the database */
  static final class MockDatabase {

    /** Create mock objects here * */

    // Ideas
    static Idea idea1 = new Idea();

    static Idea idea2 = new Idea();

    // Users
    static AppUser appUser1 = new AppUser();
    static RegularUser user1 = new RegularUser();

    static AppUser appUser2 = new AppUser();
    static RegularUser user2 = new RegularUser();

    // Domains
    static Domain domain1 = new Domain();

    // Domain groups (A set of multiple of the above domains)
    static HashSet<Domain> domainGroup1 = new HashSet<>();

    // Topics
    static Topic topic1 = new Topic();

    // Topic groups (A set of multiple of the above topics)
    static HashSet<Topic> topicGroup1 = new HashSet<>();

    // Techs
    static Technology tech1 = new Technology();

    // Tech groups (A set of multiple of the above techs)
    static HashSet<Technology> techGroup1 = new HashSet<>();

    // Collaboration requests
    static CollaborationRequest collaborationRequest1 = new CollaborationRequest();

    /**
     * Initialize fields here
     *
     * @author Wassim Jabbour
     */
    static {

      // Initialize user
      appUser1.setEmail("example1@gmail.com");
      user1.setAppUser(appUser1);

      // Initialize user
      appUser2.setEmail("example2@gmail.com");
      user2.setAppUser(appUser2);

      // Initialize topics
      topic1.setId(UUID.randomUUID().toString());
      topic1.setName("Music");

      // Initialize topic groups by merging topics
      topicGroup1.add(topic1);

      // Initialize domains
      domain1.setId(UUID.randomUUID().toString());
      domain1.setName("Software Engineering");

      // Initialize domain groups by merging domains
      domainGroup1.add(domain1);

      // Initialize techs
      tech1.setId(UUID.randomUUID().toString());
      tech1.setName("Java");

      // Initialize tech groups by merging techs
      techGroup1.add(tech1);

      // Initialize ideas
      idea1.setId(UUID.randomUUID().toString());
      idea1.setDate(new Date(10000)); // 10000 seconds since 1970 (Other constructors are
      // deprecated)
      idea1.setDescription("Cool web application for playing chess");
      idea1.setDomains(domainGroup1);
      idea1.setTopics(topicGroup1);
      idea1.setTechs(techGroup1);
      idea1.setUser(user1);

      idea2.setId(UUID.randomUUID().toString());
      idea2.setDate(new Date(10000)); // 10000 seconds since 1970 (Other constructors are
      // deprecated)
      idea2.setDescription("Another cool web application for playing chess");
      idea2.setDomains(domainGroup1);
      idea2.setTopics(topicGroup1);
      idea2.setTechs(techGroup1);
      idea2.setUser(user2);

      // Initialize collaboration requests
      collaborationRequest1.setId(UUID.randomUUID().toString());
      collaborationRequest1.setRequester(user1);
      collaborationRequest1.setIdea(idea2);
      collaborationRequest1.setAdditionalContact("123-123-1234");
      collaborationRequest1.setMessage("Hi, let me collaborate with you please!");
    }
  }

  /**
   * This class holds all of the mock methods of the CRUD repositories
   *
   * @author Wassim Jabbour
   */
  class MockRepository {

    static Idea getIdeaById(InvocationOnMock invocation) {
      String id = invocation.getArgument(0);
      if (id == null || id.isEmpty()) {
        throw new GlobalException(
            HttpStatus.BAD_REQUEST, "Please enter a valid UUID. UUID cannot be empty.");
      } else if (id.equals(MockDatabase.idea1.getId())) {
        return MockDatabase.idea1;
      } else if (id.equals(MockDatabase.idea2.getId())) {
        return MockDatabase.idea2;
      } else {
        throw new GlobalException(
            HttpStatus.BAD_REQUEST, "Idea with UUID " + id + " does not exist.");
      }
    }

    static RegularUser findRegularUserByAppUserEmail(InvocationOnMock invocation) {
      String email = invocation.getArgument(0);

      if (email.equals(MockDatabase.user1.getAppUser().getEmail())) {
        return MockDatabase.user1;
      } else if (email.equals(MockDatabase.user2.getAppUser().getEmail())) {
        return MockDatabase.user2;
      } else {
        return null; // This is a repository method mock, doesn't throw exceptions
      }
    }

    static List<CollaborationRequest> findCollaborationRequestsByRequesterAndIdea(
        InvocationOnMock invocation) {
      RegularUser requester = invocation.getArgument(0);
      Idea targetIdea = invocation.getArgument(1);
      if (requester.getAppUser().getEmail().equals(MockDatabase.user1.getAppUser().getEmail())
          && targetIdea.getId().equals(MockDatabase.idea2.getId())) {
        return List.of(MockDatabase.collaborationRequest1);
      } else {
        return List.of(); // This is a repository method mock, doesn't throw exceptions
      }
    }

    static CollaborationRequest save(InvocationOnMock invocation) {
      return invocation.getArgument(0);
    }

    static List<CollaborationRequest> findCollaborationRequestsByIdea(InvocationOnMock invocation) {
      Idea idea = invocation.getArgument(0);
      if (idea.getId().equals(MockDatabase.idea2.getId())) {
        return List.of(MockDatabase.collaborationRequest1);
      } else {
        return List.of();
      }
    }
  }
}
