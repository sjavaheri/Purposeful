package ca.mcgill.purposeful.features;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.purposeful.dao.*;
import ca.mcgill.purposeful.dto.IdeaRequestDTO;
import ca.mcgill.purposeful.model.*;
import ca.mcgill.purposeful.util.CucumberUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Step Definitions for Modifying an Idea
 *
 * @author Ramin Akhavan
 */
public class ID016_modifyIdeaStepDefinitions {

  @Autowired private TestRestTemplate client;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private DomainRepository domainRepository;

  @Autowired private TopicRepository topicRepository;

  @Autowired private TechnologyRepository technologyRepository;

  @Autowired private IdeaRepository ideaRepository;

  @Autowired private URLRepository urlRepository;

  private ResponseEntity<?> response;

  private HttpHeaders authHeader;
  private String jwtToken;
  private Map<String, String> idMap = new HashMap<String, String>();
  private String message;

  @Given("the database contains the following user accounts:")
  public void theDatabaseContainsTheFollowingUserAccounts(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @And("the database contains the following domain objects:")
  public void theDatabaseContainsTheFollowingDomainObjects(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @And("the database contains the following topic objects:")
  public void theDatabaseContainsTheFollowingTopicObjects(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @And("the database contains the following tech objects:")
  public void theDatabaseContainsTheFollowingTechObjects(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @And("the database contains the following URL objects:")
  public void theDatabaseContainsTheFollowingURLObjects(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, idMap);
  }

  @And("the database contains the following idea objects:")
  public void theDatabaseContainsTheFollowingIdeaObjects(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable(dataTable, idMap);
  }

  @And("I am successfully logged in as the user with email {string} and password {string}")
  public void iAmSuccessfullyLoggedInAsTheUserWithEmailAndPassword(String email, String password) {
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));

    // We don't save this response in the field because we don't need it later
    // In this case we are testing whether the browse ideas response is correct so we only
    // need the token
    ResponseEntity<?> response =
        client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode()); // Making sure the login was successful
    jwtToken = response.getBody().toString(); // Extract the token for future requests
    assertNotNull(jwtToken); // Ensure the token is not null
  }

  @When(
      "the user requests to modify the field {string} to become new value {string} for idea with id {string}")
  public void theUserRequestsToModifyTheFieldToBecomeNewValueForIdeaWithId(
      String field, String new_value, String id) throws JsonProcessingException, JSONException {
    Idea idea = ideaRepository.findIdeaById(idMap.get(id));

    // Not required fields
    String title = null;
    String purpose = null;
    String description = null;
    List<String> domainIds = null;
    List<String> techIds = null;
    List<String> topicIds = null;
    List<String> imgUrls = null;

    // Required fields
    boolean isPaid = idea.isPaid();
    boolean inProgress = idea.isInProgress();
    boolean isPrivate = idea.isPrivate();
    String iconUrl = idea.getIconUrl().getURL();

    // Parameters for String fields
    if (field.equalsIgnoreCase("title")) {
      title = new_value;
    }
    if (field.equalsIgnoreCase("purpose")) {
      purpose = new_value;
    }
    if (field.equalsIgnoreCase("description")) {
      description = new_value;
    }

    // Parameters for mandatory fields
    if (field.equalsIgnoreCase("isPaid")) {
      isPaid = Boolean.valueOf(new_value);
    }
    if (field.equalsIgnoreCase("inProgress")) {
      inProgress = Boolean.valueOf(new_value);
    }
    if (field.equalsIgnoreCase("isPrivate")) {
      isPrivate = Boolean.valueOf(new_value);
    }
    if (field.equalsIgnoreCase("icon URL")) {
      iconUrl = new_value;
    }

    if (field.equalsIgnoreCase("domains")) {
      domainIds = new ArrayList<>();
      for (String single_id : List.of(new_value.split(","))) {
        domainIds.add(idMap.get(single_id));
      }
    }
    if (field.equalsIgnoreCase("topics")) {
      topicIds = new ArrayList<>();
      for (String single_id : List.of(new_value.split(","))) {
        topicIds.add(idMap.get(single_id));
      }
    }
    if (field.equalsIgnoreCase("techs")) {
      techIds = new ArrayList<>();
      for (String single_id : List.of(new_value.split(","))) {
        techIds.add(idMap.get(single_id));
      }
    }
    if (field.equalsIgnoreCase("image URLs")) {
      imgUrls = new ArrayList<>();
      for (String url : List.of(new_value.split(","))) {
        // assertEquals(1, single_id);
        imgUrls.add(url);
      }
    }

    IdeaRequestDTO ideaDTO =
        new IdeaRequestDTO(
            idMap.get(id),
            title,
            purpose,
            description,
            Date.from(Instant.now()),
            isPaid,
            inProgress,
            isPrivate,
            domainIds,
            techIds,
            topicIds,
            imgUrls,
            iconUrl);

    this.authHeader = cucumberUtil.bearerAuthHeader(jwtToken);
    this.authHeader.setContentType(MediaType.APPLICATION_JSON);
    this.authHeader.setAccessControlAllowOrigin("*");

    HttpEntity<?> requestEntity = new HttpEntity<>(ideaDTO, this.authHeader);

    try {
      this.response =
          client.exchange("/api/idea/edit/", HttpMethod.PUT, requestEntity, IdeaRequestDTO.class);
    } catch (Exception e) {
      this.response =
          client.exchange("/api/idea/edit/", HttpMethod.PUT, requestEntity, String.class);
    }
  }

  @Then("the idea with id {string} will have value {string} for the field {string}")
  public void theIdeaWithIdWillHaveValueForTheField(String id, String value, String field)
      throws ParseException {
    String tableId = idMap.get(id);
    Idea idea = ideaRepository.findIdeaById(tableId);

    List<String> objIds = new ArrayList<>();

    // Parameters for String fields
    if (field.equalsIgnoreCase("title")) {
      assertEquals(value, idea.getTitle());
    }
    if (field.equalsIgnoreCase("purpose")) {
      assertEquals(value, idea.getPurpose());
    }
    if (field.equalsIgnoreCase("description")) {
      assertEquals(value, idea.getDescription());
    }

    // Parameters for mandatory fields
    if (field.equalsIgnoreCase("isPaid")) {
      assertEquals(Boolean.valueOf(value), idea.isPaid());
    }
    if (field.equalsIgnoreCase("inProgress")) {
      assertEquals(Boolean.valueOf(value), idea.isInProgress());
    }
    if (field.equalsIgnoreCase("isPrivate")) {
      assertEquals(Boolean.valueOf(value), idea.isPrivate());
    }
    if (field.equalsIgnoreCase("icon URL")) {
      assertEquals(value, idea.getIconUrl().getURL());
    }

    if (field.equalsIgnoreCase("image URLs")) {
      for (URL url : idea.getSupportingImageUrls()) {
        List<String> changedUrls = List.of(value.split(","));
        assertTrue(changedUrls.contains(url.getURL()));
      }
    }

    if (field.equalsIgnoreCase("domains")) {
      for (Domain domain : idea.getDomains()) {
        objIds.add(domain.getId());
      }
    }

    if (field.equalsIgnoreCase("topics")) {
      for (Topic topic : idea.getTopics()) {
        objIds.add(topic.getId());
      }
    }
    if (field.equalsIgnoreCase("techs")) {
      for (Technology technology : idea.getTechs()) {
        objIds.add(technology.getId());
      }
    }

    if (field.equalsIgnoreCase("topics")
        || field.equalsIgnoreCase("techs")
        || field.equalsIgnoreCase("domains")) {
      List<String> newObjIds = List.of(value.split(","));

      for (String newObjId : newObjIds) {
        assertTrue(objIds.contains(idMap.get(newObjId)));
      }
    }
  }

  @When("the user requests to modify the field {string} to become empty for idea with id {string}")
  public void theUserRequestsToModifyTheFieldToBecomeEmptyForIdeaWithId(String field, String id) {
    Idea idea = ideaRepository.findIdeaById(idMap.get(id));

    // Not required fields
    String title = null;
    String purpose = null;
    String description = null;
    List<String> domainIds = null;
    List<String> techIds = null;
    List<String> topicIds = null;
    List<String> imgUrls = null;

    // Required fields
    boolean isPaid = idea.isPaid();
    boolean inProgress = idea.isInProgress();
    boolean isPrivate = idea.isPrivate();
    String iconUrl = idea.getIconUrl().getURL();

    // Parameters for String fields
    if (field.equalsIgnoreCase("title")) {
      title = "";
    }
    if (field.equalsIgnoreCase("purpose")) {
      purpose = "";
    }
    if (field.equalsIgnoreCase("description")) {
      description = "";
    }

    if (field.equalsIgnoreCase("domains")) {
      domainIds = new ArrayList<>();
    }
    if (field.equalsIgnoreCase("topics")) {
      topicIds = new ArrayList<>();
    }
    if (field.equalsIgnoreCase("techs")) {
      techIds = new ArrayList<>();
    }
    if (field.equalsIgnoreCase("image URLs")) {
      imgUrls = new ArrayList<>();
    }

    IdeaRequestDTO ideaDTO =
        new IdeaRequestDTO(
            idMap.get(id),
            title,
            purpose,
            description,
            Date.from(Instant.now()),
            isPaid,
            inProgress,
            isPrivate,
            domainIds,
            techIds,
            topicIds,
            imgUrls,
            iconUrl);

    this.authHeader = cucumberUtil.bearerAuthHeader(jwtToken);
    this.authHeader.setContentType(MediaType.APPLICATION_JSON);
    this.authHeader.setAccessControlAllowOrigin("*");

    HttpEntity<?> requestEntity = new HttpEntity<>(ideaDTO, this.authHeader);

    try {
      this.response =
          client.exchange("/api/idea/edit/", HttpMethod.PUT, requestEntity, IdeaRequestDTO.class);
    } catch (Exception e) {
      this.response =
          client.exchange("/api/idea/edit/", HttpMethod.PUT, requestEntity, String.class);
    }
  }

  @Then("the idea with id {string} will have empty for the field {string}")
  public void theIdeaWithIdWillHaveEmptyForTheField(String id, String field) {
    Idea idea = ideaRepository.findIdeaById(idMap.get(id));
    if (field.equalsIgnoreCase("domains")) {
      assertTrue(idea.getDomains().isEmpty());
    } else if (field.equalsIgnoreCase("topics")) {
      assertTrue(idea.getTopics().isEmpty());
    } else if (field.equalsIgnoreCase("techs")) {
      assertTrue(idea.getTechs().isEmpty());
    } else if (field.equalsIgnoreCase("image URLs")) {
      assertTrue(idea.getSupportingImageUrls().isEmpty());
    }
  }

  @Then("the error message {string} will be thrown with status code {string}")
  public void theErrorMessageWillBeThrownWithStatusCode(String err_message, String status_code) {
    assertEquals(err_message, this.response.getBody());
    assertEquals(status_code, Integer.toString(this.response.getStatusCode().value()));
  }
}
