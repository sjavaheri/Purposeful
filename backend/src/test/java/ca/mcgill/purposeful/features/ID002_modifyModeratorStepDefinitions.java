package ca.mcgill.purposeful.features;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import ca.mcgill.purposeful.controller.AppUserController;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.util.CucumberUtil;
import ca.mcgill.purposeful.util.DatabaseUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;


public class ID002_modifyModeratorStepDefinitions {

    // client that will send requests
    @Autowired private TestRestTemplate client;

    @Autowired AppUserService appUserService;

    @Autowired AppUserController appUserController;

    // util class for clearing the database
    @Autowired private DatabaseUtil databaseUtil;

    @Autowired private CucumberUtil cucumberUtil;

    // fields for catching the response of the http request
    private ResponseEntity<?> response;
			
    @Given("the database contains the following regular user account:")
    public void theDatabaseContainsTheFollowingModeratorAccount(DataTable dataTable) {

        // get access to the app users with the helper method
        ArrayList<AppUser> appUsers = CucumberUtil.unpackTableIntoUsers(dataTable);

        // create each app user
        for (AppUser appUser : appUsers) {
        appUserService.registerModerator(
            appUser.getEmail(), appUser.getPassword(), appUser.getFirstname(), appUser.getLastname());
        }
    }

    // it was critical to have a separate step definition for a successful account creation and an
    // erroneous one
    @When("a new regular user account is created with first name {string}, last name {string}, email {string} and password {string}")
    public void aNewModeratorAccountIsCreatedWithFirstNameLastNameEmailAndPassword(
        String firstname, String lastname, String email, String password) {

        // create a DTO to send the request to the service
        AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

        // make a post request to create the user and store the response
        this.response = client.postForEntity("/api/appuser/regular", appUserDto, AppUserDto.class);
    }

    @When(
        "a new regular user account is created erroneously with first name {string}, last name {string}, email {string} and password {string}")
    public void aNewModeratorAccountIsCreatedErroneouslyWithFirstNameLastNameEmailAndPassword(
        String firstname, String lastname, String email, String password) {
        // create a DTO to send the request to the service
        AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

        // make a post request to create the user
        this.response = client.postForEntity("/api/appuser/regular", appUserDto, String.class);
    }

    @Then(
        "a new regular user account exists in the database with first name {string}, last name {string}, email {string} and authorities {string}")
    public void aNewModeratorAccountExistsInTheDatabaseWithFirstNameLastNameEmailAndAuthorities(
        String firstname, String lastname, String email, String authorities) {

        // assert the response was not null
        assertNotNull(response, "The response was null");

        // get the app user from the database
        AppUser retrievedUser = appUserRepository.findAppUserByEmail(email);

        // check that the app user has the correct attributes
        assertEquals(firstname, retrievedUser.getFirstname());
        assertEquals(lastname, retrievedUser.getLastname());
        assertEquals(email, retrievedUser.getEmail());

        // check that the app user has the correct authorities
        Set<Authority> setOfAuthorities = new HashSet<>();
        setOfAuthorities.add(Authority.valueOf(authorities));
        assertEquals(setOfAuthorities, retrievedUser.getAuthorities());
    }

    @Then("the number of regular user accounts in the database is {string}")
    public void theNumberOfModeratorAccountsInTheDatabaseIs(String number) {
        // get all the app users from the database
        ArrayList<AppUser> appUsers = appUserRepository.findAll();
        // assert that the number of app users is correct
        assertEquals(Integer.parseInt(number), appUsers.size());
    }

    @Then("the following error {string} shall be raised with http status {string}")
    public void theFollowingErrorShallBeRaised(String message, String status) {
        // assert that the response was not null
        assertNotNull(response, "The response was null");

        // assert that the error message is correct
        assertEquals(message, this.response.getBody());

        // assert that the http status is correct
        int statusNumber = Integer.parseInt(status);
        assertEquals(HttpStatus.valueOf(statusNumber), this.response.getStatusCode());
    }
}