Feature: Remove Idea
  As a user, I want to be able to remove any idea that I have previously posted from the Purposeful application so that it no longer exists.

  Background:
    Given the database contains the following user accounts before removing an idea:
      | id | firstname |  | lastname | email                | password         |
      | 0  | Steve     |  | Nash     | steve.nash@gmail.com | SteveIsAwesome01 |
    And the database contains the following domains before removing an idea:
      | name       |
      | Software   |
      | Computer   |
      | Electrical |
    And the database contains the following topics before removing an idea:
      | name              |
      | Frontend Dev      |
      | Backend Dev       |
      | Embedded Software |
    And the database contains the following techs before removing an idea:
      | name   |
      | Python |
      | Java   |
      | React  |
      | C      |
    And the database contains the following URLs before removing an idea:
      | url           |
      | something.com |
      | another.com   |
      | sayless.com   |
      | keepitup.com  |
      | interest.com  |
      | bestteam.com  |
    And the database contains the following ideas before removing an idea:

      | id | title             | date | purpose   | descriptions     | isPaid | inProgress | isPrivate | domains            | topics                    | techs          | image_urls                 | icon_url     | user |
      | 1  | Home Care App     | 1000 | Health    | Quality app      | True   | True       | False     | Software           | Frontend Dev              | Python         | something.com              | interest.com | 0    |
      | 2  | Football Game     | 1100 | Entertain | For fun          | False  | True       | False     | Software, Computer | Frontend Dev              | Python         | something.com, another.com | interest.com | 0    |
      | 3  | Car Detection App | 1200 | Police    | Effective app    | True   | False      | False     | Computer           | Backend Dev, Frontend Dev | Python, C      | keepitup.com               | bestteam.com | 0    |
      | 4  | Circuit Design    | 1300 | Electric  | Silicon photonic | True   | False      | True      | Electrical         | Embedded Software         | Java, React, C | sayless.com                | bestteam.com | 0    |
    And I am logged in before removing an idea


  Scenario: Successfully remove an idea
    When the user requests to remove the idea with id 4
    Then the idea entry with id 4 will no longer exist in the idea database

    # error flow

  Scenario: Unsuccessfully remove an idea that does not exist in the idea database
    When I request to remove the idea with the invalid UUID "00000000-0000-0000-000000000000"
    Then the error message "The requested idea does not exist" will be thrown with status code 400