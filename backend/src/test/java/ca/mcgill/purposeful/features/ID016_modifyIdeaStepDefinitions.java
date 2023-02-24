package ca.mcgill.purposeful.features;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.*;
import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.dto.IdeaRequestDTO;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.URL;
import ca.mcgill.purposeful.util.CucumberUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
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
    private String jwtToken;
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
        cucumberUtil.createAndSaveIdeasFromTable2(dataTable, idMap);
    }

    @And("I am successfully logged in as the user with email {string} and password {string}")
    public void iAmSuccessfullyLoggedInAsTheUserWithEmailAndPassword(String email, String password) {
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

    @When("the user requests to modify the field {string} to become {string} instead of {string} for idea with id {string}")
    public void theUserRequestsToModifyTheFieldToBecomeInsteadOfForIdeaWithId(String field, String new_value, String old_value, String id)
            throws JsonProcessingException, JSONException {
        String tableId = idMap.get(id);
        Idea idea = ideaRepository.findIdeaById(idMap.get(id));

        // Not required fields
        String title = null;
        String purpose = null;
        String description = null;
        List<String> domainIds = null;
        List<String> techIds = null;
        List<String> topicIds = null;
        List<String> imgUrlIds = null;

        // Required fields
        boolean isPaid = idea.isPaid();
        boolean inProgress = idea.isInProgress();
        boolean isPrivate = idea.isPrivate();
        Date date = idea.getDate();
        String iconUrlId = idea.getIconUrl().getId();


        // Parameters for String fields
        if (field.equalsIgnoreCase("title")){
            title = new_value;
        }
        if (field.equalsIgnoreCase("purpose")){
            purpose = new_value;
        }
        if (field.equalsIgnoreCase("descriptions")){
            description = new_value;
        }

        // Parameters for mandatory fields
        if (field.equalsIgnoreCase("isPaid")){
            isPaid = Boolean.valueOf(new_value);
        }
        if (field.equalsIgnoreCase("inProgress")){
            inProgress = Boolean.valueOf(new_value);
        }
        if (field.equalsIgnoreCase("isPrivate")){
            isPrivate = Boolean.valueOf(new_value);
        }
        if (field.equalsIgnoreCase("icon URL")){
            iconUrlId = new_value;
        }

        if (field.equalsIgnoreCase("date")){
            try {
                date= new SimpleDateFormat("yyyy-mm-dd").parse(new_value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (field.equalsIgnoreCase("domains")){
            for(String single_id: List.of(new_value.split(","))){
                domainIds.add(idMap.get(single_id));
            }
        }
        if (field.equalsIgnoreCase("topics")){
            for(String single_id: List.of(new_value.split(","))){
                topicIds.add(idMap.get(single_id));
            }
        }
        if (field.equalsIgnoreCase("techs")){
            for(String single_id: List.of(new_value.split(","))){
                techIds.add(idMap.get(single_id));
            }
        }
        if (field.equalsIgnoreCase("image URLs")){
            for(String single_id: List.of(new_value.split(","))){
                imgUrlIds.add(idMap.get(single_id));
            }
        }

        IdeaRequestDTO ideaDTO = new IdeaRequestDTO(id, title, purpose, description, date, isPaid, inProgress, isPrivate, domainIds, techIds, topicIds, imgUrlIds, iconUrlId);

        this.authHeader = cucumberUtil.bearerAuthHeader(jwtToken);
        this.authHeader.setContentType(MediaType.APPLICATION_JSON);
        this.authHeader.setAccessControlAllowOrigin("*");

        HttpEntity<?> requestEntity = new HttpEntity<>(ideaDTO, this.authHeader);

        try {
            this.response = client.exchange(
                    "/api/idea/edit/",
                    HttpMethod.PUT,
                    requestEntity,
                    IdeaRequestDTO.class);
        } catch(Exception e){
            assertEquals("hello", e.getMessage());
        }

        assertEquals(200, this.response.getStatusCode());

    }

    @Then("the idea with id {string} will have value {string} for the field {string}")
    public void theIdeaWithIdWillHaveValueForTheField(String id, String value, String field) {
        String tableId = idMap.get(id.toString());
        Idea idea = ideaRepository.findIdeaById(tableId);
        //assertEquals(value, idea.getTitle());

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
