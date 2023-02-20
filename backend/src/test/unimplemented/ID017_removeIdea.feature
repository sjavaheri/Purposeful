Feature: Remove Idea
  As a user, I want to be able to remove any idea that I have previously posted from the Purposeful application so that it no longer exists.

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
    And I am logged in as the user with email "user.steve@gmail.com" and password "SteveIsAwesome01"

  Scenario: Successfully remove an idea
    When the user requests to remove the idea with id "4"
    Then the idea entry with id "4" will no longer exist in the idea database
    And only the following entries shall remain in the idea database:
      | id | title             | date       | purpose   | descriptions  | isPaid | inProgress | isPrivate | domains            | topics                    | techs     | image_urls                 | icon_url     | user_email           |
      | 1  | Home Care App     | 2022-02-15 | Health    | Quality app   | True   | True       | False     | Software           | Frontend Dev              | Python    | something.com              | interest.com | user.steve@gmail.com |
      | 2  | Football Game     | 2022-02-16 | Entertain | For fun       | False  | True       | False     | Software, Computer | Frontend Dev              | Python    | something.com, another.com | interest.com | user.steve@gmail.com |
      | 3  | Car Detection App | 2022-02-17 | Police    | Effective app | True   | False      | False     | Computer           | Backend Dev, Frontend Dev | Python, C | keepitup.com               | bestteam.com | user.steve@gmail.com |

    # error flow

  Scenario: Unsuccessfully remove an idea that does not exist in the idea database
    Given I request to remove the idea with UUID "00000000-0000-0000-000000000000"
    Then the error message "The requested idea does not exist" will be thrown with status code "400"