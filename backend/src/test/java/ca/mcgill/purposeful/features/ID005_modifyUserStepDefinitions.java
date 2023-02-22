package ca.mcgill.purposeful.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.OwnerRepository;
import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Owner;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Step Definition for the ID005_modifyUser.feature file
 *
 * @author Enzo Benoit-Jeannin
 */
public class ID005_modifyUserStepDefinitions {

    @Autowired AppUserService appUserService;

    @Autowired AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OwnerRepository ownerRepository;

    // Util class
    @Autowired
    private CucumberUtil cucumberUtil;

    // fields for catching the response of the http request
    private ResponseEntity<?> response;

    // client that will send requests
    @Autowired private TestRestTemplate client;

    // util class for clearing the database
    @Autowired private DatabaseUtil databaseUtil;

    @Given("the database contains the following accounts:")
    public void theDatabaseContainsTheFollowingAccounts(DataTable dataTable) {
        // get access to the app users with the helper method
        ArrayList<AppUser> appUsers = CucumberUtil.unpackTableIntoUsers(dataTable);

        // create each app user
        for (AppUser appUser : appUsers) {
            if (appUser.getAuthorities().contains(Authority.Moderator)) {
                appUserService.registerModerator(
                        appUser.getEmail(), appUser.getPassword(), appUser.getFirstname(),
                        appUser.getLastname());
            } else if (appUser.getAuthorities().contains(Authority.Owner)) {
                appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
                appUserRepository.save(appUser);

                Owner owner = new Owner();
                owner.setAppUser(appUser);
                ownerRepository.save(owner);
            } else if (appUser.getAuthorities().contains(Authority.User)) {
                appUserService.registerRegularUser(
                        appUser.getEmail(), appUser.getPassword(), appUser.getFirstname(),
                        appUser.getLastname());
            }
        }
    }

    @Given("that the user is logged as user with email {string} and password {string}")
    public void iAmLoggedInAsModeratorWithEmailAndPassword(String email, String password) {
        // Login as the owner
        HttpEntity<String> requestEntity = new HttpEntity<>(
                cucumberUtil.basicAuthHeader(email, password));
        this.response = client.exchange("/login", HttpMethod.POST, requestEntity, String.class);

        // check that the login was successful
        assertEquals(200, this.response.getStatusCode().value());

        // save the jwt token
        this.jwtToken = this.response.getBody().toString();
    }

    @Given("I am not logged in")
    public void iAmNotLoggedIn() {
        // save the jwt token
        this.jwtToken = null;
    }

    @When("the user requests to modify the lastname {string} to become {string} and the firstname {string} to become {string}")
    public void aNewModeratorAccountIsCreatedWithFirstNameLastNameEmailAndPassword(String old_lastname,
                                                                                   String new_lastname, String old_firstname, String new_firstname) {
        // create a DTO to send the request to the service
        AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

        // make a post request to create the user and store the response
        HttpEntity<AppUserDto> requestEntity = new HttpEntity<>(appUserDto,
                cucumberUtil.bearerAuthHeader(this.jwtToken));
        this.response = client.exchange("/api/appuser/moderator", HttpMethod.POST, requestEntity,
                AppUserDto.class);
    }


}
