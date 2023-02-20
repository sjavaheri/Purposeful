Feature: Login User
  As any type of user, I want to be able to login to the purposeful application with my account so that I can access the services available to me.

  Background:
    Given the database contains the following appUser accounts:
      | firstName | lastName | email                    | password             | authorities         |
      | Owner     | Steve    | owner.steve@gmail.com    | OwnerIsAwesome01     | Authority.OWNER     |
      | Moderator | John     | moderator.john@gmail.com | moderatorIsAwesome02 | Authority.MODERATOR |
      | User      | Jack     | user.jack@gmail.com      | userIsAwesome03      | Authority.USER      |

    # Normal flow

  Scenario Outline: Login with valid credentials
    When I request to login with the email <email> and password <password>
    Then I should receive a valid JWT token that contains the email <email> and the authority <authority>

    Examples:
      | email                    | password             | authorities         |
      | owner.steve@gmail.com    | OwnerIsAwesome01     | Authority.OWNER     |
      | moderator.john@gmail.com | moderatorIsAwesome02 | Authority.MODERATOR |
      | user.jack@gmail.com      | userIsAwesome03      | Authority.USER      |

    # Error flow

  Scenario Outline: Login with invalid password
    When I request to login with the email <email> and password <password>
    Then I should receive an error message "The password you entered is incorrect" with HTTP status code 400

    Examples:
      | email                    | password             |
      | owner.steve@gmail.com    | OwnerIsAwesome02     |
      | moderator.john@gmail.com | moderatorIsAwesome03 |
      | user.jack@gmail.com      | userIsAwesome01      |

  Scenario: Login with invalid email
    When I request to login with the email <email> and password <password>
    Then I should receive an error message "No account with this email address exists" with HTTP status code 404

    Examples:
      | email                    | password                |
      | owner.steve.gmail.com    | OwnerIsAwesome01        |
      | moderator.john.gmail.com | moderatorIsNotAwesome02 |
      | user.jack.gmail.com      | userIsAwesome03         |






