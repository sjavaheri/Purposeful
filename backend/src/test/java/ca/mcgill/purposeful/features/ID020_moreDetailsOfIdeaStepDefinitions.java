package ca.mcgill.purposeful.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Step definitions for the ID020_moreDetailsOfIdea.feature file
 *
 * @author Thibaut Baguette
 */
public class ID020_moreDetailsOfIdeaStepDefinitions {

  @Autowired private TestRestTemplate client;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired AppUserRepository appUserRepository;

  private HttpHeaders authHeader;
  private ResponseEntity<?> response;
  private Map<String, String> idMap = new HashMap<String, String>();

  @Given("the database contains the following users \\(ID020):")
  public void theDatabaseContainsTheFollowingUsers(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @Given("the database contains the following domains \\(ID020):")
  public void theDatabaseContainsTheFollowingDomains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following topics \\(ID020):")
  public void theDatabaseContainsTheFollowingTopics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following techs \\(ID020):")
  public void theDatabaseContainsTheFollowingTechs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following URLs \\(ID020):")
  public void theDatabaseContainsTheFollowingURLs(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, idMap);
  }

  @Given("the database contains the following ideas \\(ID020):")
  public void theDatabaseContainsTheFollowingIdeas(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable(dataTable, idMap);
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
    appUser.setAuthorities(setOfAuthorities);
    appUserRepository.save(appUser);

    HttpEntity<String> request = new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    ResponseEntity<String> response =
        client.exchange("/api/login", HttpMethod.POST, request, String.class);
    authHeader = cucumberUtil.bearerAuthHeader(response.getBody());
  }

  @When("I request to view the details of idea with id {int}")
  public void iRequestToViewTheDetailsOfIdeaWithId(Integer id) {
    String correctedId = idMap.get(id.toString());
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response = client.exchange("/api/idea/" + correctedId, HttpMethod.GET, request, String.class);
  }

  @Then("the following information about the idea should be displayed:")
  public void theFollowingInformationAboutTheIdeaShouldBeDisplayed(Map<String, String> dataTable) {
    assertEquals(HttpStatus.OK, response.getStatusCode());

    JsonObject json = Json.parse(response.getBody().toString()).asObject();

    assertEquals(dataTable.get("title"), json.get("title").asString());
    assertEquals(dataTable.get("purpose"), json.get("purpose").asString());

    // domains
    Set<String> domains = new HashSet<String>();
    json.get("domains")
        .asArray()
        .iterator()
        .forEachRemaining(
            domain -> {
              domains.add(domain.asObject().get("name").asString());
            });
    Set<String> domainsExpected =
        new HashSet<String>(Arrays.asList(dataTable.get("domains").split(", ")));
    assertEquals(domainsExpected, domains);

    // topics
    Set<String> topics = new HashSet<String>();
    json.get("topics")
        .asArray()
        .iterator()
        .forEachRemaining(
            topic -> {
              topics.add(topic.asObject().get("name").asString());
            });
    Set<String> topicsExpected =
        new HashSet<String>(Arrays.asList(dataTable.get("topics").split(", ")));
    assertEquals(topicsExpected, topics);

    // techs
    Set<String> techs = new HashSet<String>();
    json.get("techs")
        .asArray()
        .iterator()
        .forEachRemaining(
            tech -> {
              techs.add(tech.asObject().get("name").asString());
            });
    Set<String> techsExpected =
        new HashSet<String>(Arrays.asList(dataTable.get("techs").split(", ")));
    assertEquals(techsExpected, techs);

    assertEquals(Boolean.parseBoolean(dataTable.get("isPaid")), json.get("isPaid").asBoolean());
    assertEquals(
        Boolean.parseBoolean(dataTable.get("isInProgress")), json.get("inProgress").asBoolean());
    assertEquals(
        Boolean.parseBoolean(dataTable.get("isPrivate")), json.get("isPrivate").asBoolean());
  }

  @Then("the supporting image with the following URL should be displayed:")
  public void theSupportingImageWithTheFollowingURLShouldBeDisplayed(List<String> dataTable) {
    JsonObject json = Json.parse(response.getBody().toString()).asObject();
    Set<String> urls = new HashSet<String>();
    json.get("imgUrls")
        .asArray()
        .iterator()
        .forEachRemaining(
            url -> {
              urls.add(url.asObject().get("url").asString());
            });

    Set<String> urlsExpected = new HashSet<String>();
    for (String url : dataTable) {
      if (!url.equals("url")) {
        urlsExpected.add(url);
      }
    }

    assertEquals(urlsExpected, urls);
  }

  @Then("the icon with the following URL should be displayed:")
  public void theIconWithTheFollowingURLShouldBeDisplayed(List<String> dataTable) {
    JsonObject json = Json.parse(response.getBody().toString()).asObject();
    String url = json.get("iconUrl").asObject().get("url").asString();

    assertEquals(dataTable.get(1), url);
  }

  @Then("no supporting images should be displayed")
  public void noSupportingImagesShouldBeDisplayed() {
    JsonObject json = Json.parse(response.getBody().toString()).asObject();
    JsonArray urls = json.get("imgUrls").asArray();

    assertTrue(urls.isEmpty());
  }

  @When("I request to view the details of idea with UUID {string}")
  public void iRequestToViewTheDetailsOfIdeaWithUUID(String uuid) {
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response = client.exchange("/api/idea/" + uuid, HttpMethod.GET, request, String.class);
  }

  @Then("the user shall receive the error message {string} with status {int}")
  public void theUserShallReceiveTheErrorMessageWithStatus(String errorMessage, int status) {
    assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
  }
}
