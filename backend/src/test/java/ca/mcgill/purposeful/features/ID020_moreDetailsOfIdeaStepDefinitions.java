package ca.mcgill.purposeful.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.controller.IdeaController;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.util.CucumberUtil;
import ca.mcgill.purposeful.util.DatabaseUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for the ID020_moreDetailsOfIdea.feature file
 * 
 * @author Thibaut Baguette
 */
public class ID020_moreDetailsOfIdeaStepDefinitions {
  @Autowired
  private TestRestTemplate client;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  IdeaController ideaController;

  @Autowired
  private DatabaseUtil databaseUtil;

  @Autowired
  private CucumberUtil cucumberUtil;

  @Autowired
  AppUserRepository appUserRepository;

  private String jwtToken;
  private ResponseEntity<?> response;
  private Map<String, String> idMap = new HashMap<String, String>();
  
  @Given("the database contains the following users:")
  public void theDatabaseContainsTheFollowingUsers(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @Given("the database contains the following domains:")
  public void theDatabaseContainsTheFollowingDomains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainFromTable(dataTable, idMap);
  }

  @Given("the database contains the following topics:")
  public void theDatabaseContainsTheFollowingTopics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicFromTable(dataTable, idMap);
  }

  @Given("the database contains the following techs:")
  public void theDatabaseContainsTheFollowingTechs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechFromTable(dataTable, idMap);
  }

  @Given("the database contains the following URLs:")
  public void theDatabaseContainsTheFollowingURLs(DataTable dataTable) {
    cucumberUtil.createAndSaveURLFromTable(dataTable, idMap);
  }

  @Given("the database contains the following ideas:")
  public void theDatabaseContainsTheFollowingIdeas(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeaFromTable(dataTable, idMap);
  }

  @Given("I am logged in")
  public void iAmLoggedIn() {
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
    appUserRepository.save(appUser);

    HttpEntity<String> request = new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    ResponseEntity<String> response = client.exchange("/login", HttpMethod.POST, request, String.class);
    jwtToken = response.getBody();
  }

  @When("I request to view the details of idea with id {int}")
  public void iRequestToViewTheDetailsOfIdeaWithId(Integer id) {
    String correctedId = idMap.get(id.toString());
    HttpEntity<String> request = new HttpEntity<>(jwtToken);
    response = client.exchange(
        "/idea/" + correctedId + "/details",
        HttpMethod.GET,
        request,
        String.class);
  }

  @Then("the following information about the idea should be displayed:")
  public void theFollowingInformationAboutTheIdeaShouldBeDisplayed(Map<String, String> dataTable) {
    // TODO: check the response body
  }

  @Then("the supporting image with the following URL should be displayed:")
  public void theSupportingImageWithTheFollowingURLShouldBeDisplayed(List<String> dataTable) {
    // TODO: check the response body
  }

  @Then("the icon with the following URL should be displayed:")
  public void theIconWithTheFollowingURLShouldBeDisplayed(List<String> dataTable) {
    // TODO: check the response body
  }

  @Then("no supporting images should be displayed")
  public void noSupportingImagesShouldBeDisplayed() {
    // TODO: check the response body
  }

  @When("I request to view the details of idea with UUID {string}")
  public void iRequestToViewTheDetailsOfIdeaWithUUID(String uuid) {
    HttpEntity<String> request = new HttpEntity<>(jwtToken);
    response = client.exchange(
        "/idea/" + uuid + "/details",
        HttpMethod.GET,
        request,
        String.class);
  }

  @Then("the user shall recieve the error message {string} with status {int}")
  public void theUserShallRecieveTheErrorMessageWithStatus(String errorMessage, int status) {
    assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
    // TODO: check the response body
  }
}