package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.dao.*;
import ca.mcgill.purposeful.dto.IdeaRequestDTO;
import ca.mcgill.purposeful.model.*;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ID015_createIdeaStepDefinitions {

  @Autowired AppUserService appUserService;

  @Autowired DomainRepository domainRepository;

  @Autowired TechnologyRepository technologyRepository;

  @Autowired TopicRepository topicRepository;

  @Autowired IdeaRepository ideaRepository;

  @Autowired URLRepository urlRepository;

  @Autowired private TestRestTemplate client;

  @Autowired private CucumberUtil cucumberUtil;

  private String jwtToken;

  private ResponseEntity<?> response;

  @Given("the database contains the following user account:")
  public void the_database_contains_the_following_user_account(DataTable dataTable) {
    // create app user
     cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, null);
  }

  @Given("the number of ideas in the database is {int}")
  public void the_number_of_ideas_in_the_database_is(int int1) {
    assertEquals(ideaRepository.count(), int1);
  }

  @Given("the database contains the following domains:")
  public void the_database_contains_the_following_domains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, null);
  }

  @Given("the database contains the following topics:")
  public void the_database_contains_the_following_topics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, null); 
  }

  @Given("the database contains the following techs:")
  public void the_database_contains_the_following_techs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, null);
  }

  @Given("the database contains the following URLs:")
  public void the_database_contains_the_following_urls(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, null);
  }

  @Given("that the user is logged in with the email {string} and the password {string}")
  public void that_the_user_is_logged_in_with_the_email_and_the_password(
      String string, String string2) {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      // Login as the user
      HttpEntity<String> requestEntity =
          new HttpEntity<>(cucumberUtil.basicAuthHeader(string, string2));
      this.response = client.exchange("/login", HttpMethod.POST, requestEntity, String.class);

      // check that the login was successful
      assertEquals(200, this.response.getStatusCode().value());

      // save the jwt token
      this.jwtToken = this.response.getBody().toString();
    }
  }

  @When(
      "the user creates an idea with title {string}, purpose {string}, description {string}, paid status {string}, progress status {string}, private status {string}, domains {string}, topics {string}, techs {string}, icon {string}, supporting images {string}")
  public void
      the_user_creates_an_idea_with_title_purpose_description_paid_status_progress_status_private_status_domains_topics_techs_icon_supporting_images(
          String title,
          String purpose,
          String description,
          String ispaid,
          String inprogress,
          String isprivate,
          String domains,
          String topics,
          String techs,
          String iconurl,
          String supportingimageurls) {
    Boolean isPaid = Boolean.parseBoolean(ispaid);
    Boolean inProgress = Boolean.parseBoolean(inprogress);
    Boolean isPrivate = Boolean.parseBoolean(isprivate);
    ArrayList<String> domainIds = new ArrayList<String>();
    ArrayList<String> techIds = new ArrayList<String>();
    ArrayList<String> topicIds = new ArrayList<String>();
    ArrayList<String> supportingImgIds = new ArrayList<String>();
    String iconUrlId = "";

    String[] domainStr = domains.split(",");
    String[] techStr = techs.split(",");
    String[] topicStr = topics.split(",");
    String[] urlStr = supportingimageurls.split(",");

    for (String domain_name : domainStr) {
      String domainId = "";
      for (Domain domain : domainRepository.findAll()) {
        if (domain.getName().equals(domain_name)) {
          domainId = domain.getId();
          break;
        }
      }
      domainIds.add(domainId);
    }
    for (String tech_name : techStr) {
      for (Technology tech : technologyRepository.findAll()) {
        if (tech.getName().equals(tech_name)) {
          techIds.add(tech.getId());
          break;
        }
      }
    }
    for (String topic_name : topicStr) {
      String topicId = "";
      for (Topic topic : topicRepository.findAll()) {
        if (topic.getName().equals(topic_name)) {
          topicId = topic.getId();
          break;
        }
      }
      topicIds.add(topicId);
    }
    for (String url_str : urlStr) {
      for (URL url : urlRepository.findAll()) {
        if (url.getURL().equals(url_str)) {
          supportingImgIds.add(url.getId());
          break;
        }
      }
    }
    for (URL url : urlRepository.findAll()) {
      if (url.getURL().equals(iconurl)) {
        iconUrlId = url.getId();
        break;
      }
    }
    IdeaRequestDTO ideaDto =
        new IdeaRequestDTO(
            null,
            title,
            purpose,
            description,
            null,
            isPaid,
            inProgress,
            isPrivate,
            domainIds,
            techIds,
            topicIds,
            supportingImgIds,
            iconUrlId);

    // make a post request to create the idea and store the response
    HttpEntity<IdeaRequestDTO> requestEntity =
        new HttpEntity<>(ideaDto, cucumberUtil.bearerAuthHeader(this.jwtToken));
    try {
      this.response =
          client.exchange("/api/idea/create", HttpMethod.POST, requestEntity, IdeaRequestDTO.class);
    } catch (Exception e) {
      this.response =
          client.exchange("/api/idea/create", HttpMethod.POST, requestEntity, String.class);
    }
  }

  @Then("a new idea exists in the database with title {string}, icon {string}")
  public void a_new_idea_exists_in_the_database_with_title_icon(String title, String iconURL) {
    assertNotNull(this.response, "The response was null");
    Idea createdIdea = null;
    for (Idea idea : ideaRepository.findAll()) {
      if (idea.getTitle().equals(title) && idea.getIconUrl().getURL().equals(iconURL)) {
        createdIdea = idea;
        break;
      }
    }
    assertNotNull(createdIdea);
  }

  @Then("the following error {string} shall be raised with the status code {int}")
  public void the_following_error_shall_be_raised_with_the_status_code(
      String error, Integer statusCode) {
    assertNotNull(this.response, "The response was null");
    assertEquals(statusCode, response.getStatusCode().value());
    assertEquals(error, response.getBody());
  }
}
