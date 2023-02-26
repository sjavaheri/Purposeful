package ca.mcgill.purposeful.features;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.ReactionRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dto.ReactionDTO;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Step definitions for the ID021_userHighFiveIdea.feature file
 *
 * @author Athmane Benarous
 */
public class ID021_userHighFiveIdeaStepDefinitions {

  @Autowired
  private TestRestTemplate client;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private CucumberUtil cucumberUtil;

  @Autowired
  AppUserRepository appUserRepository;

  @Autowired
  ReactionRepository reactionRepository;

  @Autowired
  RegularUserRepository regularUserRepository;

  private HttpHeaders authHeader;
  private ResponseEntity<?> response;
  private Map<String, String> idMap = new HashMap<String, String>();

  @Given("the database contains the following users before high fiving an idea:")
  public void theDatabaseContainsTheFollowingUsers(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @Given("the database contains the following domains before high fiving an idea:")
  public void theDatabaseContainsTheFollowingDomains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following topics before high fiving an idea:")
  public void theDatabaseContainsTheFollowingTopics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following techs before high fiving an idea:")
  public void theDatabaseContainsTheFollowingTechs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following URLs before high fiving an idea:")
  public void theDatabaseContainsTheFollowingURLs(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following ideas before high fiving an idea:")
  public void theDatabaseContainsTheFollowingIdeas(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable2(dataTable, idMap);
  }

  @Given("the database contains the following reactions before high fiving an idea:")
  public void theDatabaseContainsTheFollowingReactions(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable2(dataTable, idMap);
  }

  @Given("I am logged in before high fiving an idea")
  public void iAmLoggedInBeforeHighFivingAnIdea() {
    AppUser appUser = new AppUser();
    String email = "luke.skywalker@spacemail.galaxy";
    String password = "P@ssw0rd";
    appUser.setFirstname("Luke");
    appUser.setLastname("Skywalker");
    appUser.setEmail(email);
    appUser.setPassword(passwordEncoder.encode(password));
    // set the authorities of the app user
    Set<Authority> setOfAuthorities = new HashSet<Authority>();
    Authority authority = Authority.valueOf("User");
    setOfAuthorities.add(authority);
    // add the app user to the list of app users
    appUser.setAuthorities(setOfAuthorities);
    appUserRepository.save(appUser);

    HttpEntity<String> request = new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    ResponseEntity<String> response = client.exchange("/api/login", HttpMethod.POST, request,
        String.class);
    authHeader = cucumberUtil.bearerAuthHeader(response.getBody());
  }

  @When("the user with email {string} reacts with a reaction {string} to an idea with id {string}")
  public void theUserWithEmailReactsWithAReactionToAnIdeaWithId(
      String email, String reactionType, String idea_id) {
    String user_id = regularUserRepository
        .findRegularUserByAppUser_Id(appUserRepository.findAppUserByEmail(email).getId())
        .getId();

    ReactionDTO reactionDTO = new ReactionDTO(new Date(), ReactionType.valueOf(reactionType),
        idea_id, user_id);

    // make a post request to create the user and store the response
    this.response = client.postForEntity("/api/reaction/react", reactionDTO, ReactionDTO.class);
  }

  @Then("a new reaction of idea {string} and user {string} shall be added to the reaction database")
  public void aNewEntryOfTypeShallBeAddedToTheReactionDatabase(String idea_id, String email) {
    String user_id = regularUserRepository
        .findRegularUserByAppUser_Id(appUserRepository.findAppUserByEmail(email).getId())
        .getId();
    Reaction reaction = reactionRepository.findReactionByIdea_IdAndRegularUser_Id(idea_id, user_id);
    assertNotNull(reaction);
  }

  @When("the user with email {string} reacts again with a reaction {string} to an idea with id {string}")
  public void theUserWithEmailReactsAgainWithAReactionToAnIdeaWithId(
      String email, String reactionType, String idea_id) {
    String user_id = regularUserRepository
        .findRegularUserByAppUser_Id(appUserRepository.findAppUserByEmail(email).getId())
        .getId();

    ReactionDTO reactionDTO = new ReactionDTO(new Date(), ReactionType.valueOf(reactionType),
        idea_id, user_id);

    // make a post request to create the user and store the response
    this.response = client.postForEntity("/api/reaction/react", reactionDTO, ReactionDTO.class);
  }

  @Then("the reaction entry of idea {string} and user {string} shall be removed from the reaction database")
  public void theReactionEntryOfIdShallBeRemovedFromTheReactionDatabase(
      String idea_id, String email) {
    String user_id = regularUserRepository
        .findRegularUserByAppUser_Id(appUserRepository.findAppUserByEmail(email).getId())
        .getId();
    Reaction reaction = reactionRepository.findReactionByIdea_IdAndRegularUser_Id(idea_id, user_id);
    assertNull(reaction);
  }

  @When("the user requests to react with the reactionType {string} to an idea with id {string} on behalf of another regular user with id {string}")
  public void theUserRequestsToReactWithTheReactionTypeToAnIdeaWithIdOnBehalfOfAnotherRegularUserWithId(
      String reactionType, String idea_id, String appUser_email) {
    String uuid_idea = idMap.get(idea_id.toString());
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(
        appUserRepository.findAppUserByEmail(appUser_email).getId()).getId();

    ReactionDTO reactionDTO = new ReactionDTO(new Date(), ReactionType.valueOf(reactionType),
        uuid_idea, user_id);

    // make a post request to create the user and store the response
    this.response = client.postForEntity("/api/reaction/react", reactionDTO, ReactionDTO.class);
  }

  @Then("the error message {string} will be thrown with status code {string} and the reaction database will not be modified")
  public void theErrorMessageWillBeThrownWithStatusCodeAndTheReactionDatabaseWillNotBeModified(
      String errorMessage, String status) {
    assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
  }
}
