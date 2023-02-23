package ca.mcgill.purposeful.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.*;
import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
 * Step Definitions for Modifying an Idea
 * @author Ramin Akhavan
 */
public class ID016_modifyIdeaStepDefinitions {

    @Autowired
    private TestRestTemplate client;
  
    @Autowired
    private PasswordEncoder passwordEncoder;
  
    @Autowired
    private CucumberUtil cucumberUtil;
  
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private URLRepository urlRepository;

    private ResponseEntity<?> response;
  
    private HttpHeaders authHeader;
    private Map<String, String> idMap = new HashMap<String, String>();

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
        cucumberUtil.createAndSaveIdeasFromTable1(dataTable, idMap);
    }

    @And("I am logged in as the user with email {string} and password {string}")
    public void iAmLoggedInAsTheUserWithEmailAndPassword(String email, String password) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email);
        appUser.setFirstname("User");
        appUser.setLastname("Steve");
        appUser.setEmail(email);
        appUser.setPassword(passwordEncoder.encode(password));
        Set<Authority> setOfAuthorities = new HashSet<Authority>();
        Authority authority = Authority.valueOf("User");
        setOfAuthorities.add(authority);
        appUser.setAuthorities(setOfAuthorities);
        appUserRepository.save(appUser);
    
        HttpEntity<String> request = new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
        ResponseEntity<String> response = client.exchange("/login", HttpMethod.POST, request,
            String.class);
        authHeader = cucumberUtil.bearerAuthHeader(response.getBody());
    }

    @When("the user requests to modify the field {string} to become {string} instead of {string} for idea with id {string}")
    public void theUserRequestsToModifyTheFieldToBecomeInsteadOfForIdeaWithId(String field, String new_value, String old_value, String id) {
        String tableId = idMap.get(id.toString());
        assertEquals(1, tableId);
        Idea idea = ideaRepository.findIdeaById(tableId);
        Map<String, Object> params = new HashMap<String, Object>();

        // Parameters for String fields
        if (field.equalsIgnoreCase("title")){
            params.put("title", new_value);
        }
        if (field.equalsIgnoreCase("purpose")){
            params.put("purpose", new_value);
        }
        if (field.equalsIgnoreCase("descriptions")){
            params.put("purpose", new_value);
        }

        // Parameters for mandatory fields
        if (field.equalsIgnoreCase("isPaid")){
            params.put("isPaid", Boolean.valueOf(new_value));
        }
//        else{
//            params.put("isPaid", idea.isPaid());
//        }

        if (field.equalsIgnoreCase("inProgress")){
            params.put("inProgress", Boolean.valueOf(new_value));
        }
//        else{
//            params.put("isProgress", idea.isInProgress());
//        }

        if (field.equalsIgnoreCase("isPrivate")){
            params.put("isPrivate", Boolean.valueOf(new_value));
        }
//        else{
//            params.put("isPrivate", idea.isPrivate());
//        }
        if (field.equalsIgnoreCase("icon URL")){
            params.put("iconUrl", new_value.split(","));
        }
//        else{
//            params.put("iconUrl", idea.getIconUrl());
//        }

        if (field.equalsIgnoreCase("date")){
            params.put("date", Date.valueOf(new_value));
        }

        // Parameters for
        if (field.equalsIgnoreCase("domains")){
            params.put("domains", new_value.split(","));
        }
        if (field.equalsIgnoreCase("topics")){
            params.put("topics", new_value.split(","));
        }
        if (field.equalsIgnoreCase("techs")){
            params.put("techs", new_value.split(","));
        }
        if (field.equalsIgnoreCase("image URLs")){
            params.put("imgUrls", new_value.split(","));
        }

        HttpEntity<?> request = new HttpEntity<>(authHeader);
        this.response = client.exchange(
                "/api/idea/edit" + tableId,
                HttpMethod.PUT,
                request,
                IdeaDTO.class,
                params);

        this.response = response;

    }

    @Then("the idea with id {string} will have value {string} for the field {string}")
    public void theIdeaWithIdWillHaveValueForTheField(String id, String value, String field) {
        String tableId = idMap.get(id.toString());
        Idea idea = ideaRepository.findIdeaById(tableId);
        assertEquals("title", idea.getTitle());
    }

    @When("the user requests to modify the field {string} to become empty for idea with id {string}")
    public void theUserRequestsToModifyTheFieldToBecomeEmptyForIdeaWithId(String field, String id) {

    }

    @Then("the idea with id {string} will have empty for the field {string}")
    public void theIdeaWithIdWillHaveEmptyForTheField(String id, String field) {

    }

    @Then("the error message {string} will be thrown with status code {string}")
    public void theErrorMessageWillBeThrownWithStatusCode(String err_message, String status_code) {

    }

    @When("the user requests to modify the field {string} to become new value {string} for idea with id {string}")
    public void theUserRequestsToModifyTheFieldToBecomeNewValueForIdeaWithId(String field, String new_value, String id) {
    }
}
