package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.ReactionRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dto.ReactionDTO;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Step definitions for the ID021_userHighFiveIdea.feature file
 *
 * @author Athmane Benarous
 */
public class ID021_userHighFiveIdeaStepDefinitions {

  @Autowired private TestRestTemplate client;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired AppUserRepository appUserRepository;

  @Autowired ReactionRepository reactionRepository;

  @Autowired RegularUserRepository regularUserRepository;

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
    cucumberUtil.createAndSaveReactionsFromTable(dataTable, idMap);
  }

  @Given(
      "the user is logged in with the email {string} and the password {string} before high fiving an idea")
  public void theUserIsLoggedInWithTheEmailAndThePasswordBeforeHighFivingAnIdea(
      String email, String password) {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      // Login as the user
      HttpEntity<String> requestEntity =
          new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
      this.response = client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);

      // check that the login was successful
      assertEquals(200, this.response.getStatusCode().value());
      authHeader = cucumberUtil.bearerAuthHeader(response.getBody().toString());
    }
  }

  @When("the user with id {string} reacts with a reaction {string} to an idea with id {string}")
  public void theUserWithIdReactsWithAReactionToAnIdeaWithId(
      String user, String reactionType, String idea_id) {
    String correctedUser = idMap.get(user);
    String correctedIdea = idMap.get(idea_id);
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(correctedUser).getId();

    ReactionDTO reactionDTO =
        new ReactionDTO(new Date(), ReactionType.valueOf(reactionType), correctedIdea, user_id);
    // make a post request to create the user and store the response
    HttpEntity<ReactionDTO> requestEntity = new HttpEntity<>(reactionDTO, authHeader);
    this.response =
        client.exchange("/api/reaction", HttpMethod.POST, requestEntity, ReactionDTO.class);
  }

  @Then("a new reaction of idea {string} and user {string} shall be added to the reaction database")
  public void aNewEntryOfTypeShallBeAddedToTheReactionDatabase(String idea_id, String user) {

    String correctedIdea = idMap.get(idea_id);
    String correctedUser = idMap.get(user);
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(correctedUser).getId();
    Reaction reaction =
        reactionRepository.findReactionByIdea_IdAndRegularUser_Id(correctedIdea, user_id);
    assertNotNull("The reaction was null", reaction);
  }

  @When(
      "the user with id {string} reacts a first time with a reaction {string} to an idea with id {string}")
  public void theUserWithIdReactsAFirstTimeWithAReactionToAnIdeaWithId(
      String user, String reactionType, String idea_id) {
    String correctedUser = idMap.get(user);
    String correctedIdea = idMap.get(idea_id);
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(correctedUser).getId();

    ReactionDTO reactionDTO =
        new ReactionDTO(new Date(), ReactionType.valueOf(reactionType), correctedIdea, user_id);
    // make a post request to create the user and store the response
    HttpEntity<ReactionDTO> requestEntity = new HttpEntity<>(reactionDTO, authHeader);
    this.response =
        client.exchange("/api/reaction", HttpMethod.POST, requestEntity, ReactionDTO.class);
  }

  @Then(
      "the user with id {string} reacts again with a reaction {string} to an idea with id {string}")
  public void theUserWithIdReactsAgainWithAReactionToAnIdeaWithId(
      String user, String reactionType, String idea_id) {
    String correctedUser = idMap.get(user);
    String correctedIdea = idMap.get(idea_id);
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(correctedUser).getId();

    ReactionDTO reactionDTO =
        new ReactionDTO(new Date(), ReactionType.valueOf(reactionType), correctedIdea, user_id);
    // make a post request to create the user and store the response
    HttpEntity<ReactionDTO> requestEntity = new HttpEntity<>(reactionDTO, authHeader);
    this.response =
        client.exchange("/api/reaction", HttpMethod.POST, requestEntity, ReactionDTO.class);
  }

  @Then(
      "the reaction entry of idea {string} and user {string} shall be removed from the reaction database")
  public void theReactionEntryOfIdShallBeRemovedFromTheReactionDatabase(
      String idea_id, String user) {
    Assertions.assertNotNull(response, "The response was null");

    String correctedIdea = idMap.get(idea_id);
    String correctedUser = idMap.get(user);
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(correctedUser).getId();
    Reaction reaction =
        reactionRepository.findReactionByIdea_IdAndRegularUser_Id(correctedIdea, user_id);
    assertNull(reaction);
  }

  @When(
      "the user with id {string} requests to react with the reaction {string} to the idea with id {string} on behalf of the user with id {string}")
  public void theUserWithIdRequestsToReactWithTheReactionToTheIdeaWithIdOnBehalfOfTheUserWithId(
      String request_user, String reactionType, String idea_id, String target_user) {
    String correctedTargetUser = idMap.get(target_user);
    String correctedIdea = idMap.get(idea_id);
    String user_id = regularUserRepository.findRegularUserByAppUser_Id(correctedTargetUser).getId();

    ReactionDTO reactionDTO =
        new ReactionDTO(new Date(), ReactionType.valueOf(reactionType), correctedIdea, user_id);
    // make a post request to create the user and store the response
    HttpEntity<ReactionDTO> requestEntity = new HttpEntity<>(reactionDTO);
    this.response =
        client.exchange("/api/reaction", HttpMethod.POST, requestEntity, ReactionDTO.class);
  }

  @Then(
      "the error message {string} will be thrown with status code {int} after attempting to react")
  public void theErrorMessageWillBeThrownWithStatusCodeAfterAttemptingToReact(
      String msg, Integer code) {
    assertEquals(HttpStatus.valueOf(code), response.getStatusCode());
  }
}
