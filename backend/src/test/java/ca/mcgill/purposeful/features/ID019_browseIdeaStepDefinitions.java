package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.controller.IdeaController;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.dto.SearchFilterDTO;
import ca.mcgill.purposeful.service.IdeaService;
import ca.mcgill.purposeful.util.CucumberUtil;
import ca.mcgill.purposeful.util.DatabaseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Step definitions for the ID019_BrowseIdea.feature file
 *
 * @author Wassim Jabbour
 */
public class ID019_browseIdeaStepDefinitions {

  @Autowired private TestRestTemplate client;

  @Autowired private IdeaController ideaController;

  @Autowired private DatabaseUtil databaseUtil;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired private DomainRepository domainRepository;

  @Autowired private TopicRepository topicRepository;

  @Autowired private TechnologyRepository technologyRepository;

  @Autowired private IdeaService ideaService;

  // token to store once user is logged in
  private String jwtToken;

  // field for catching the response of the http request
  private ArrayList<IdeaDTO> returnedIdeas;

  private ResponseEntity<String> erroneousResponse;

  // declare a map to store the ids of the users created in the database
  private Map<String, String> idMap;

  @Given("the id map is initialized")
  public void theIdMapIsInitialized() {
    idMap = new HashMap<>();
  }

  @And("the database contains the following RegularUser accounts \\(Strategy1):")
  public void theDatabaseContainsTheFollowingRegularUserAccounts(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @And("the database contains the following domains \\(Strategy1):")
  public void theDatabaseContainsTheFollowingDomains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @And("the database contains the following topics \\(Strategy1):")
  public void theDatabaseContainsTheFollowingTopics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @And("the database contains the following techs \\(Strategy1):")
  public void theDatabaseContainsTheFollowingTechs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @And("the database contains the following ideas \\(Strategy1):")
  public void theDatabaseContainsTheFollowingIdeas(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable1(dataTable, idMap);
  }

  @And("I am logged in as the user with email {string} and password {string}")
  public void iAmLoggedInAsTheUserWithEmailAndPassword(String email, String password) {
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));

    // We don't save this response in the field because we don't need it later
    // In this case we are testing whether the browse ideas response is correct so we only
    // need the token
    ResponseEntity<?> response =
        client.exchange("/login", HttpMethod.POST, requestEntity, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode()); // Making sure the login was successful
    jwtToken = response.getBody().toString(); // Extract the token for future requests
    assertNotNull(jwtToken); // Ensure the token is not null
  }

  @When(
      "the user requests to browse ideas by domains {string}, topics {string}, and techs {string}")
  public void theUserRequestsToBrowseIdeasByDomainsTopicsAndTechs(
      String domainIds, String TopicIds, String techIds)
      throws JsonProcessingException, JSONException {

    /*
     * Start by extracting the lists of domains, topics, and techs from the idMap
     */

    // Create lists for extraction
    ArrayList<String> domains;
    ArrayList<String> topics;
    ArrayList<String> techs;

    // Extract domains
    if (domainIds.equals("null")) {
      domains = null;
    } else {
      domains = new ArrayList<>();
      for (String domainId : domainIds.split(",")) {
        domains.add(domainRepository.findDomainById(idMap.get(domainId)).getName());
      }
    }

    // Extract topics
    if (TopicIds.equals("null")) {
      topics = null;
    } else {
      topics = new ArrayList<>();
      for (String topicId : TopicIds.split(",")) {
        topics.add(topicRepository.findTopicById(idMap.get(topicId)).getName());
      }
    }

    // Extract techs
    if (techIds.equals("null")) {
      techs = null;
    } else {
      techs = new ArrayList<>();
      for (String techId : techIds.split(",")) {
        techs.add(technologyRepository.findTechnologyById(idMap.get(techId)).getName());
      }
    }

    // Create a DTO to send
    SearchFilterDTO searchFilterDTO = new SearchFilterDTO(domains, topics, techs);

    /*
     * Now we can create and make the request
     */

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(searchFilterDTO, header);

    // Save the response in the current class (This is the response we are trying to test here)
    var response = client.exchange("/api/idea", HttpMethod.POST, requestEntity, ArrayList.class);

    // Extract returned lists
    ObjectMapper mapper = new ObjectMapper();
    returnedIdeas =
        mapper.convertValue(response.getBody(), new TypeReference<ArrayList<IdeaDTO>>() {});
  }

  @Then("the user shall have access to the ideas with ids {string}")
  public void theUserShallHaveAccessToTheIdeasWithIds(String orderedIds) {

    List<String> orderedIdsList = Arrays.asList(orderedIds.split(","));

    // Check the size is as expected
    assertEquals(orderedIdsList.size(), returnedIdeas.size());

    // Check that the returned ideas are the same as the ones we requested
    // We check the title because it is unique for this set of tests since DTOs don't have ids
    for (int i = 0; i < orderedIdsList.size(); i++) {
      assertEquals(
          ideaService.getIdeaById(idMap.get(orderedIdsList.get(i))).getTitle(),
          returnedIdeas.get(i).getTitle());
    }
  }

  @When(
      "the user erroneously requests to browse ideas by domains with id {string}, topic with id {string}, and tech with id {string}")
  public void theUserErroneouslyRequestsToBrowseIdeasByDomainsWithIdTopicWithIdAndTechWithId(
      String domainId, String topicId, String techId) {

    // Create the dto lists
    ArrayList<String> domains = new ArrayList<>();
    ArrayList<String> topics = new ArrayList<>();
    ArrayList<String> techs = new ArrayList<>();
    domains.add(domainRepository.findDomainById(idMap.get(domainId)).getName());
    topics.add(topicRepository.findTopicById(idMap.get(topicId)).getName());
    techs.add(technologyRepository.findTechnologyById(idMap.get(techId)).getName());

    // Create the dto
    SearchFilterDTO searchFilterDTO = new SearchFilterDTO(domains, topics, techs);

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(searchFilterDTO, header);

    // Save the response in the current class (This is the response we are trying to test here)
    // Note the string return type
    erroneousResponse = client.exchange("/api/idea", HttpMethod.POST, requestEntity, String.class);
  }

  @Then("the user shall receive the error message {string} with status {string}")
  public void theUserShallReceiveTheErrorMessageWithStatus(String error, String status) {
    assertEquals(HttpStatus.valueOf(Integer.parseInt(status)), erroneousResponse.getStatusCode());
    assertEquals(error, erroneousResponse.getBody());
  }
}
