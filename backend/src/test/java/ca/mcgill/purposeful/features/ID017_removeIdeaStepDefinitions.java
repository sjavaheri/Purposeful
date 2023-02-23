package ca.mcgill.purposeful.features;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
 * Step definitions for the ID017_removeIdea.feature file
 *
 * @author Athmane Benarous
 */
public class ID017_removeIdeaStepDefinitions {

  @Autowired
  private TestRestTemplate client;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private CucumberUtil cucumberUtil;

  @Autowired
  AppUserRepository appUserRepository;

  private HttpHeaders authHeader;
  private ResponseEntity<?> response;
  private Map<String, String> idMap = new HashMap<String, String>();

  @Given("the database contains the following user accounts before removing an idea:")
  public void theDatabaseContainsTheFollowingUserAccountsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @Given("the database contains the following domains before removing an idea:")
  public void theDatabaseContainsTheFollowingDomainsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainFromTable(dataTable, idMap);
  }

  @Given("the database contains the following topics before removing an idea:")
  public void theDatabaseContainsTheFollowingTopicsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicFromTable(dataTable, idMap);
  }

  @Given("the database contains the following techs before removing an idea:")
  public void theDatabaseContainsTheFollowingTechsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveTechFromTable(dataTable, idMap);
  }

  @Given("the database contains the following URLs before removing an idea:")
  public void theDatabaseContainsTheFollowingURLsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveURLFromTable(dataTable, idMap);
  }

  @Given("the database contains the following ideas before removing an idea:")
  public void theDatabaseContainsTheFollowingIdeasBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeaFromTable(dataTable, idMap);
  }

  @And("I am logged in before removing an idea")
  public void iAmLoggedInBeforeRemovingAnIdea() {
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
    ResponseEntity<String> response = client.exchange("/login", HttpMethod.POST, request,
        String.class);
    authHeader = cucumberUtil.bearerAuthHeader(response.getBody());
  }


  @When("the user requests to remove the idea with id {int}")
  public void theUserRequestsToRemoveTheIdeaWithId(Integer id) {
    String correctedId = idMap.get(id.toString());
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response = client.exchange(
        "/api/idea/" + correctedId,
        HttpMethod.DELETE,
        request,
        String.class);
  }

  @Then("the idea entry with id {int} will no longer exist in the idea database")
  public void theIdeaEntryWithIdWillNoLongerExistInTheIdeaDatabase(Integer id) {
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    String correctedId = idMap.get(id.toString());
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response = client.exchange(
        "/api/idea/" + correctedId,
        HttpMethod.GET,
        request,
        String.class);
    assertNotEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @When("I request to remove the idea with the invalid UUID {string}")
  public void iRequestToRemoveTheIdeaWithTheInvalidUUID(String uuid) {
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response = client.exchange(
        "/api/idea/" + uuid,
        HttpMethod.DELETE,
        request,
        String.class);
  }

  @Then("the error message {string} will be thrown with status code {int}")
  public void theErrorMessageWillBeThrownWithStatusCode(String msg, Integer code) {
    assertEquals(HttpStatus.valueOf(code), response.getStatusCode());
  }
}