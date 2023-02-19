Feature: Modify Moderator
  As a user, I want to modify the details of my account so that they contain my updated information

  Background:
    Given the database contains the following user accounts:
      | firstName | lastName       | email                    | password          |
      | Enzo      | Benoit-Jeannin | enzo.benoit@gmail.com    | EnzoIsAwesome01   |
      | Wassim    | Jabbour        | wassim.jabbour@gmail.com | WassimIsAwesome01 |

  # Normal Flow

  Scenario: Successfully update a user account
    Given that the user is logged with the email "enzo.benoit@gmail.com" and the password "EnzoIsAwesome01"
    When the user requests to update their account information with the following details:
      | firstName | lastName | oldPassword     | newPassword        |
      | NewEnzo   | Benoit   | EnzoIsAwesome01 | NewEnzoIsAwesome01 |
    Then account with email "enzo.benoit@gmail.com" should be updated with the following details:
      | firstName | lastName | password           |
      | NewEnzo   | Benoit   | NewEnzoIsAwesome01 |
    Then the number of user accounts in the datavase shall be "2"

  # Error Flows

  Scenario: Unsuccessfully update a user account because you are not logged in
    When the user requests to update their account information with the following details:
      | firstName | lastName | oldPassword     | newPassword        |
      | NewEnzo   | Benoit   | EnzoIsAwesome01 | NewEnzoIsAwesome01 |
    Then the user should be denied permission to the requested resource with an HTTP status code of "401"

  Scenario: Unsuccessfully update a user account because you are not logged in with the correct account
    Given that the user is logged with the email "wassim.jabbour@gmail.com" and the password "WassimIsAwesome01"
    When the user requests to update their account information with the following details:
      | email                 | firstName | lastName | oldPassword     | newPassword        |
      | enzo.benoit@gmail.com | NewEnzo   | Benoit   | EnzoIsAwesome01 | NewEnzoIsAwesome01 |
    Then the user should be denied permission to the requested resource with an HTTP status code of "403"

  Scenario Outline: Unsuccessfully update a user account
    Given that the user is logged with the email "enzo.benoit@gmail.com" and the password "EnzoIsAwesome01"
    When the user request to update their account using the old password <oldPassword> with new password <newPassword>
    Then the following error <error> should be raised
    Then the number of moderator accounts in the database is still "2"

    Examples:
      | oldPassword     | newPassword    | error                                                                                                                                                            |
      | EnzoIsAwesome0  | enzo           | The password you entered is incorrect                                                                                                                            |
      | EnzoIsAwesome01 | enzo           | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | EnzoIsAwesome01 | Enzo1          | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | EnzoIsAwesome01 | thisisenzo     | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | EnzoIsAwesome01 | 111111111      | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | EnzoIsAwesome01 | thispassword1  | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | EnzoIsAwesome01 | FFFFFFFFFFFFF8 | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | EnzoIsAwesome01 |                | Please enter a valid password. Passwords cannot be left empty                                                                                                    |