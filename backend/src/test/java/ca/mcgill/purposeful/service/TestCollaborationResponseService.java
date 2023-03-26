package ca.mcgill.purposeful.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.CollaborationResponseRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Status;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Tests for the collaboration response service */
@ExtendWith(MockitoExtension.class)
public class TestCollaborationResponseService {
  @Mock private CollaborationResponseRepository collaborationResponseRepository;
  @Mock private CollaborationRequestRepository collaborationRequestRepository;

  @Mock private IdeaService ideaService;

  @Mock private RegularUserRepository regularUserRepository;

  @InjectMocks private CollaborationResponseService collaborationResponseService;

  // Fields pertaining to testing view collaboration response
  private static final String REQUESTER_EMAIL = "REQUESTER_EMAIL";
  private static final String IDEA_ID = "IDEA_ID";
  private static final Idea IDEA = new Idea();
  private static final RegularUser REQUESTER = new RegularUser();
  private static final String RESPONSE_MESSAGE = "This is a response message.";
  private static final String ADDITIONAL_CONTACT = "123-456-7890";

  private static final String REQUESTER_EMAIL_NO_RESPONSE = "REQUESTER_EMAIL_NO_RESPONSE";
  private static final String IDEA_ID_NO_RESPONSE = "IDEA_ID_NO_RESPONSE";
  private static final RegularUser REQUESTER_NO_RESPONSE = new RegularUser();
  private static final Idea IDEA_NO_RESPONSE = new Idea();

  private static final String REQUESTER_EMAIL_NO_REQUEST = "REQUESTER_EMAIL_NO_REQUEST";
  private static final String IDEA_ID_NO_REQUEST = "IDEA_ID_NO_REQUEST";
  private static final RegularUser REQUESTER_NO_REQUEST = new RegularUser();
  private static final Idea IDEA_NO_REQUEST = new Idea();

  // Fields pertaining to testing handling a collaboration request

  // Ideas
  static Idea idea1 = new Idea();
  static Idea idea2 = new Idea();

  // Users
  static AppUser appUser1 = new AppUser();
  static RegularUser user1 = new RegularUser();
  static final String USER1_EMAIL = "example1@gmail.com";

  static AppUser appUser2 = new AppUser();
  static RegularUser user2 = new RegularUser();
  static final String USER2_EMAIL = "example2@gmail.com";

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

  // Field initializations
  static {

    // Initialize user
    appUser1.setEmail(USER1_EMAIL);
    user1.setAppUser(appUser1);
    user1.setId(UUID.randomUUID().toString());

    // Initialize user
    appUser2.setEmail(USER2_EMAIL);
    user2.setAppUser(appUser2);
    user2.setId(UUID.randomUUID().toString());

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

  @BeforeEach
  public void setMockOutput() {
    lenient()
        .when(
            collaborationRequestRepository.findCollaborationRequestsByRequesterAndIdea(
                any(RegularUser.class), any(Idea.class)))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              RegularUser user = invocation.getArgument(0);
              Idea idea = invocation.getArgument(1);
              if (user.equals(REQUESTER) && idea.equals(IDEA)) {
                CollaborationResponse response = new CollaborationResponse();
                response.setMessage(RESPONSE_MESSAGE);
                response.setAdditionalContact(ADDITIONAL_CONTACT);
                CollaborationRequest request = new CollaborationRequest();
                request.setCollaborationResponse(response);
                ArrayList<CollaborationRequest> requests = new ArrayList<>();
                requests.add(request);
                return requests;
              } else if (user.equals(REQUESTER_NO_RESPONSE) && idea.equals(IDEA_NO_RESPONSE)) {
                CollaborationRequest request = new CollaborationRequest();
                ArrayList<CollaborationRequest> requests = new ArrayList<>();
                requests.add(request);
                return requests;
              } else if (user.equals(REQUESTER_NO_REQUEST) && idea.equals(IDEA_NO_REQUEST)) {
                return new ArrayList<>();
              }
              return null;
            });
    lenient()
        .when(ideaService.getIdeaById(anyString()))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              switch (invocation.getArgument(0).toString()) {
                case IDEA_ID:
                  return IDEA;
                case IDEA_ID_NO_RESPONSE:
                  return IDEA_NO_RESPONSE;
                case IDEA_ID_NO_REQUEST:
                  return IDEA_NO_REQUEST;
                default:
                  return null;
              }
            });
    lenient()
        .when(regularUserRepository.findRegularUserByAppUserEmail(anyString()))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              switch (invocation.getArgument(0).toString()) {
                case REQUESTER_EMAIL:
                  return REQUESTER;
                case REQUESTER_EMAIL_NO_RESPONSE:
                  return REQUESTER_NO_RESPONSE;
                case REQUESTER_EMAIL_NO_REQUEST:
                  return REQUESTER_NO_REQUEST;
                case USER1_EMAIL:
                  return user1;
                case USER2_EMAIL:
                  return user2;
                default:
                  return null;
              }
            });
    lenient()
        .when(collaborationRequestRepository.findCollaborationRequestById(anyString()))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              String id = invocation.getArgument(0).toString();
              if (id.equals(collaborationRequest1.getId())) {
                return collaborationRequest1;
              } else {
                return null;
              }
            });
    lenient()
        .when(collaborationRequestRepository.save(any(CollaborationRequest.class)))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              CollaborationRequest request = invocation.getArgument(0);
              request.setId(UUID.randomUUID().toString());
              return request;
            });
    lenient()
        .when(collaborationResponseRepository.save(any(CollaborationResponse.class)))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              CollaborationResponse response = invocation.getArgument(0);
              response.setId(UUID.randomUUID().toString());
              return response;
            });
  }

  /**
   * Test that the collaboration response is returned when it exists
   *
   * @author Thibaut Baguette
   */
  @Test
  public void testViewCollaborationResponse_successful() {
    CollaborationResponse response =
        collaborationResponseService.getCollaborationResponseForRequesterAndIdea(
            REQUESTER_EMAIL, IDEA_ID);

    assertNotNull(response);
    assertEquals(RESPONSE_MESSAGE, response.getMessage());
    assertEquals(ADDITIONAL_CONTACT, response.getAdditionalContact());
  }

  /**
   * Test that null is returned when there is no collaboration response
   *
   * @author Thibaut Baguette
   */
  @Test
  public void testViewCollaborationResponse_alternate() {
    CollaborationResponse response =
        collaborationResponseService.getCollaborationResponseForRequesterAndIdea(
            REQUESTER_EMAIL_NO_RESPONSE, IDEA_ID_NO_RESPONSE);

    assertNull(response);
  }

  /**
   * Test that an exception is thrown when the idea has no collaboration request
   *
   * @author Thibaut Baguette
   */
  @Test
  public void testViewCollaborationResponse_error() {
    assertThrows(
        GlobalException.class,
        () ->
            collaborationResponseService.getCollaborationResponseForRequesterAndIdea(
                REQUESTER_EMAIL_NO_REQUEST, IDEA_ID_NO_REQUEST));
  }

  /** Test that a collaboration request can be accepted by the idea owner */
  @Test
  public void approveCollaborationRequest_Success() {
    try {
      CollaborationResponse response =
          collaborationResponseService.approveCollaborationRequest(
              USER2_EMAIL, collaborationRequest1.getId(), "123-123-1234", "Let's collaborate!");

      assertEquals("123-123-1234", response.getAdditionalContact());
      assertEquals("Let's collaborate!", response.getMessage());
      assertEquals(Status.Approved, response.getStatus());
      assertEquals(collaborationRequest1.getCollaborationResponse().getId(), response.getId());
    } catch (GlobalException e) {
      fail();
    }
  }

  /** Test that a collaboration request can be declined by the idea owner */
  @Test
  public void declineCollaborationRequest_Success() {
    try {
      CollaborationResponse response =
          collaborationResponseService.declineCollaborationRequest(
              USER2_EMAIL, collaborationRequest1.getId(), "Sorry :( I'm not interested");

      assertNull(response.getAdditionalContact());
      assertEquals("Sorry :( I'm not interested", response.getMessage());
      assertEquals(Status.Declined, response.getStatus());
      assertEquals(collaborationRequest1.getCollaborationResponse().getId(), response.getId());
    } catch (GlobalException e) {
      fail();
    }
  }

  /** Test that a collaboration request cannot be accepted by someone who isn't its owner */
  @Test
  public void approveCollaborationRequest_HandlerNotOwnerOfIdea() {
    try {
      CollaborationResponse response =
          collaborationResponseService.approveCollaborationRequest(
              USER1_EMAIL, collaborationRequest1.getId(), "123-123-1234", "Let's collaborate!");

      // Shouldn't get here
      fail();
    } catch (GlobalException e) {
      assertEquals("The handler is not the owner of the idea", e.getMessage());
    }
  }

  /** Test that a collaboration request cannot be declined by someone who isn't its owner */
  @Test
  public void declineCollaborationRequest_HandlerNotOwnerOfIdea() {
    try {
      CollaborationResponse response =
          collaborationResponseService.declineCollaborationRequest(
              USER1_EMAIL, collaborationRequest1.getId(), "Sorry :( I'm not interested");

      // Shouldn't get here
      fail();
    } catch (GlobalException e) {
      assertEquals("The handler is not the owner of the idea", e.getMessage());
    }
  }

  /** Test that a collaboration request cannot be accepted with an empty message */
  @Test
  public void approveCollaborationRequest_EmptyMessage() {
    try {
      CollaborationResponse response =
          collaborationResponseService.approveCollaborationRequest(
              USER2_EMAIL, collaborationRequest1.getId(), "123-123-1234", "");

      // Shouldn't get here
      fail();
    } catch (GlobalException e) {
      assertEquals(
          "Please provide an additional contact and a message to approve this request",
          e.getMessage());
    }
  }

  /** Test that a collaboration request cannot be accepted with an empty additional contact */
  @Test
  public void approveCollaborationRequest_EmptyAdditionalContact() {
    try {
      CollaborationResponse response =
          collaborationResponseService.approveCollaborationRequest(
              USER2_EMAIL, collaborationRequest1.getId(), "", "Let's collaborate!");

      // Shouldn't get here
      fail();
    } catch (GlobalException e) {
      assertEquals(
          "Please provide an additional contact and a message to approve this request",
          e.getMessage());
    }
  }

  /** Test that a collaboration request cannot be declined with an empty message */
  @Test
  public void declineCollaborationRequest_EmptyMessage() {
    try {
      CollaborationResponse response =
          collaborationResponseService.declineCollaborationRequest(
              USER2_EMAIL, collaborationRequest1.getId(), "");

      // Shouldn't get here
      fail();
    } catch (GlobalException e) {
      assertEquals("Please provide a message to decline this request", e.getMessage());
    }
  }
}
