Feature: Login User
  As any type of user, I want to be able to login to the purposeful application with my account so that I can access the services available to me.

  Background:
    Given the database contains the following appUser accounts:
      | firstname | lastname | email                    | password             | authorities |
      | Owner     | Steve    | owner.steve@gmail.com    | OwnerIsAwesome01     | Owner       |
      | Moderator | John     | moderator.john@gmail.com | moderatorIsAwesome02 | Moderator   |
      | User      | Jack     | user.jack@gmail.com      | userIsAwesome03      | User        |

    # Normal flow

  Scenario Outline: Login with valid credentials
    When I request to login with the email "<email>" and password "<password>"
    Then I should receive a valid JWT token and a HTTP status code "<HTTP status>"

    Examples:
      | email                    | password             | authorities         | HTTP status |
      | owner.steve@gmail.com    | OwnerIsAwesome01     | Authority.Owner     | 200         |
      | moderator.john@gmail.com | moderatorIsAwesome02 | Authority.Moderator | 200         |
      | user.jack@gmail.com      | userIsAwesome03      | Authority.User      | 200         |

    # Error flow

  Scenario Outline: Login with invalid password
    When I request to login erroneously with the email "<email>" and password "<password>"
    Then I should receive a HTTP status code "<HTTP status>"

    Examples:
      | email                    | password             | HTTP status |
      | owner.steve@gmail.com    | OwnerIsAwesome02     | 401         |
      | moderator.john@gmail.com | moderatorIsAwesome03 | 401         |
      | user.jack@gmail.com      | userIsAwesome01      | 401         |

  Scenario Outline: Login with invalid email
    When I request to login erroneously with the email "<email>" and password "<password>"
    Then I should receive a HTTP status code "<HTTP status>"

    Examples:
      | email                    | password                | HTTP status |
      | owner.steve.gmail.com    | OwnerIsAwesome01        | 401         |
      | moderator.john.gmail.com | moderatorIsNotAwesome02 | 401         |
      | user.jack.gmail.com      | userIsAwesome03         | 401         |






