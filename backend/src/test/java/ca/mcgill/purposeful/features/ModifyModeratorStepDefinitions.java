package ca.mcgill.purposeful.features;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ModifyModeratorStepDefinitions {

    @Given("^the database contains the following moderator accounts:$")
    public void the_database_contains_the_following_moderator_accounts() throws Throwable {
        throw new PendingException();
    }

    @Given("^that the user is logged with the email \"([^\"]*)\" and the password \"([^\"]*)\"$")
    public void that_the_user_is_logged_with_the_email_something_and_the_password_something(String strArg1,
            String strArg2) throws Throwable {
        throw new PendingException();
    }

    @When("^the user requests to update their account information with the following details:$")
    public void the_user_requests_to_update_their_account_information_with_the_following_details() throws Throwable {
        throw new PendingException();
    }

    @When("^the user request to update their account using the old password (.+) with new password (.+)$")
    public void the_user_request_to_update_their_account_using_the_old_password_with_new_password(String oldpassword,
            String newpassword) throws Throwable {
        throw new PendingException();
    }

    @Then("^account with email \"([^\"]*)\" should be updated with the following details:$")
    public void account_with_email_something_should_be_updated_with_the_following_details(String strArg1)
            throws Throwable {
        throw new PendingException();
    }

    @Then("^the number of moderator accounts in the datavase shall be \"([^\"]*)\"$")
    public void the_number_of_moderator_accounts_in_the_datavase_shall_be_something(String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^the user should be denied permission to the requested resource with an HTTP status code of \"([^\"]*)\"$")
    public void the_user_should_be_denied_permission_to_the_requested_resource_with_an_http_status_code_of_something(
            String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^the following error (.+) should be raised$")
    public void the_following_error_should_be_raised(String error) throws Throwable {
        throw new PendingException();
    }

    @Then("^the number of moderator accounts in the database is still \"([^\"]*)\"$")
    public void the_number_of_moderator_accounts_in_the_database_is_still_something(String strArg1) throws Throwable {
        throw new PendingException();
    }

}