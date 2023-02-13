package ca.mcgill.purposeful.features;

import org.junit.runner.RunWith;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateModeratorStepDefinitions {

    @Given("^the database contains the following moderator account:$")
    public void the_database_contains_the_following_moderator_account() throws Throwable {
        throw new PendingException();
    }

    @When("^a new moderator account is created with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void a_new_moderator_account_is_created_with_something_something_something_and_something(String firstname, String lastname, String email, String password, String strArg1, String strArg2, String strArg3, String strArg4) throws Throwable {
        throw new PendingException();
    }

    @Then("^a new moderator account exists in the database with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void a_new_moderator_account_exists_in_the_database_with_something_something_something_and_something(String firstname, String lastname, String email, String password, String strArg1, String strArg2, String strArg3, String strArg4) throws Throwable {
        throw new PendingException();
    }

    @Then("^the number of moderator accounts in the database is 2$")
    public void the_number_of_moderator_accounts_in_the_database_is_2() throws Throwable {
        throw new PendingException();
    }

    @Then("^the following \"([^\"]*)\" shall be raised$")
    public void the_following_something_shall_be_raised(String error, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^the number of moderator accounts in the database is 1$")
    public void the_number_of_moderator_accounts_in_the_database_is_1() throws Throwable {
        throw new PendingException();
    }

}
