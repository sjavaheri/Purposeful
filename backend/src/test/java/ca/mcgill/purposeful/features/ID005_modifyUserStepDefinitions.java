package ca.mcgill.purposeful.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.util.CucumberUtil;
import ca.mcgill.purposeful.util.DatabaseUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Step Definition for the ID005_modifyUser.feature file
 *
 * @author Enzo Benoit-Jeannin
 */
public class ID005_modifyUserStepDefinitions {

    @Autowired AppUserService appUserService;

    @Autowired AppUserRepository appUserRepository;

    // client that will send requests
    @Autowired private TestRestTemplate client;

    // util class for clearing the database
    @Autowired private DatabaseUtil databaseUtil;

    // fields for catching the response of the http request
    private ResponseEntity<?> response;

    @Given("the database contains the following regular user account:")
    public void theDatabaseContainsTheFollowingRegularUserAccount(DataTable dataTable) {

        // get access to the app users with the helper method
        ArrayList<AppUser> appUsers = CucumberUtil.unpackTableIntoUsers(dataTable);

        // create each app user
        for (AppUser appUser : appUsers) {
            appUserService.registerRegularUser(
                    appUser.getEmail(), appUser.getPassword(), appUser.getFirstname(), appUser.getLastname());
        }
    }

    @When(" that the user is logged with the email {string} and the password {string}")
    public void aNewRegularUserAccountIsModifiedWithFirstNameLastNamePassword(
            String firstname, String lastname, String email, String password) {

        // create a DTO to send the request to the service
        AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

        // make a post request to create the user and store the response
        this.response = client.postForEntity("/api/appuser/regular", appUserDto, AppUserDto.class);
    }
}
