Feature: Modify Admin 
    As the owner, I want to be able to modify the details of my account so that they contain my updated information

Background: 
    Given the database contains the following admin accounts:
        | firstName | lastName | email                  | password         | 
        | Owner     | Steve    | owner.steve@gmail.com  | OwnerIsAwesome01 |
        | User      | Bob      | user.bob@gmail.com     | UserIsAwesome01  |


Scenario Outline: Successfully update an admin account
    Given that the user is logged with the email "owner.steve@gmail.com" and the password "OwnerIsAwesome01"
    When the user requests to update their account information with the following details:
        | firstName | lastName | password            | 
        | NewOwner  | Steve2   | NewOwnerIsAwesome01 |
    Then account with email "owner.steve@gmail.com" should be updated with the following details:
        | firstName | lastName | password            | 
        | NewOwner  | Steve2   | NewOwnerIsAwesome01 |
    Then the number of admin accounts in the datavase shall be "2"

Scenario Outline: Unsuccessfully update an admin account because you are not logged in
    When the user requests to update their account information with the following details:
    | firstName | lastName | password            | 
    | NewOwner  | Steve2   | NewOwnerIsAwesome01 |
    Then the user should be denied permission to the requested resource with an HTTP status code of "401"

Scenario Outline: Unsuccessfully update an admin account because you are not logged in with the correct account
    Given that the user is logged with the email "user.bob@gmail.com" and the password "UserIsAwesome01"
    When the user requests to update their account information with the following details:
    | firstName | lastName | password            | 
    | NewOwner  | Steve2   | NewOwnerIsAwesome01 |
    Then the user should be denied permission to the requested resource with an HTTP status code of "403"

Scenario Outline: Unsuccessfully update an admin account
    Given that the user is logged with the email "owner.steve@gmail.com" and the password "OwnerIsAwesome01"
    When the user request to update the account with email "<email>" using the old password "<oldPassword>" with new password "<newPassword>" :
    Then the following "<error>" should be raised
    Then the number of admin accounts in the database is still "2"

    Examples: 
    | email                 | oldPassword         | newPassword         | error                                                   |
    | owner.steve@gmail.com | OwnerIsAwesome0     | owner               | The password you entered is incorrect                   |
    | owner.steve@gmail.com | OwnerIsAwesome01    | owner               | Please enter a valid new password. Passwords must be at least 8 characters long and contain at least one number and one uppercase character         |

