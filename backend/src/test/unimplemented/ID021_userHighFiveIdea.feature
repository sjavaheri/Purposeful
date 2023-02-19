Feature: Modify Moderator
  As a user, I want to be able to express my support for an idea by giving it a "high five," so that I can express my positive sentiment for an idea without adding to a numerical score or ranking.

    # reaction database (date is omitted for brevity)

  Background:
    Given the database contains the following user accounts:
      | firstName | lastName | email                | password         |
      | User      | Steve    | user.steve@gmail.com | SteveIsAwesome01 |
    And the database contains the following domains:
      | name       |
      | Software   |
      | Computer   |
      | Electrical |
    And the database contains the following topics:
      | name              |
      | Frontend Dev      |
      | Backend Dev       |
      | Embedded Software |
    And the database contains the following techs:
      | name   |
      | Python |
      | Java   |
      | React  |
      | C      |
    And the database contains the following URLs:
      | URL           |
      | something.com |
      | another.com   |
      | sayless.com   |
      | keepitup.com  |
      | interest.com  |
      | bestteam.com  |
    And the database contains the following ideas:
      | id | title             | date       | purpose   | descriptions     | isPaid | inProgress | isPrivate | domains            | topics                    | techs          | image_urls                 | icon_url     | user_email           |
      | 1  | Home Care App     | 2022-02-15 | Health    | Quality app      | True   | True       | False     | Software           | Frontend Dev              | Python         | something.com              | interest.com | user.steve@gmail.com |
      | 2  | Football Game     | 2022-02-16 | Entertain | For fun          | False  | True       | False     | Software, Computer | Frontend Dev              | Python         | something.com, another.com | interest.com | user.steve@gmail.com |
      | 3  | Car Detection App | 2022-02-17 | Police    | Effective app    | True   | False      | False     | Computer           | Backend Dev, Frontend Dev | Python, C      | keepitup.com               | bestteam.com | user.steve@gmail.com |
      | 4  | Circuit Design    | 2022-02-18 | Electric  | Silicon photonic | True   | False      | True      | Electrical         | Embedded Software         | Java, React, C | sayless.com                | bestteam.com | user.steve@gmail.com |
    And the database contains the following reactions:
      | id | idea_id | user_id              | reaction_type |
      | 1  | 1       | user.steve@gmail.com | HighFive      |
      | 2  | 3       | user.steve@gmail.com | HighFive      |
    And I am logged in as the user with email "user.steve@gmail.com" and password "SteveIsAwesome01"

    # Normal Flow

  Scenario Outline: Successfully high five an idea
    When the user with email <user_id> reacts with a reaction <reaction_type> to an idea with id <idea_id>
    Then a new entry of type <reaction_type> shall be added to the reaction database

    Examples:
      | id | idea_id | user_id              | reaction_type |
      | 1  | 1       | user.steve@gmail.com | HighFive      |
      | 2  | 3       | user.steve@gmail.com | HighFive      |
      | 3  | 4       | user.steve@gmail.com | HighFive      |

    # Alternate Flow

  Scenario Outline: Successfully high five an idea which I already high fived to remove the high five
    When the user with email <user_id> reacts with a reaction <reaction_type> to an idea with id <idea_id>
    And a reaction with my email <user_id> and the idea id <idea_id> already exists in the reaction database
    Then the reaction entry of id <id> shall be removed from the reaction database

    Examples:
      | id | idea_id | user_id              | reaction_type |
      | 2  | 3       | user.steve@gmail.com | HighFive      |

    # Error Flow

  Scenario : Unsuccessfully high five an idea that does not exist in the idea database
    Given I request to react with the reaction "HighFive" to the idea with UUID "00000000-0000-0000-000000000000"
    Then the error message "The requested idea does not exist" will be thrown with status code "400"