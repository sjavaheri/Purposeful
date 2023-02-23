#noinspection CucumberUndefinedStep
Feature: Modify Idea
  As a user, I want to be able to modify the details of any idea that I have posted it so that my idea can be changed over time

  Background:
    Given the database contains the following user accounts:
      | id | firstname | lastname | email                | password         |
      | 0  | User      | Steve    | user.steve@gmail.com | SteveIsAwesome01 |
      | 1  | User      | Bob      | user.bob@gmail.com   | BobIsAwesome01   |
    And the database contains the following domain objects:
      | id | name       |
      | 2  | Software   |
      | 3  | Computer   |
      | 4  | Electrical |
    And the database contains the following topic objects:
      | id | name              |
      | 5  | Frontend Dev      |
      | 6  | Backend Dev       |
      | 7  | Embedded Software |
    And the database contains the following tech objects:
      | id | name   |
      | 8  | Python |
      | 9  | Java   |
      | 10 | React  |
      | 11 | C      |
    And the database contains the following URL objects:
      | id | url           |
      | 12 | something.com |
      | 13 | another.com   |
      | 14 | sayless.com   |
      | 15 | keepitup.com  |
      | 16 | interest.com  |
      | 17 | bestteam.com  |
    And the database contains the following idea objects:
      | id | title            | purpose                                           | domains | topics | techs | supportingImageUrls | iconUrl | isPaid | isInProgress | isPrivate | user |
      | 18 | Music generation | Open sourced software to generate classical music | 2       | 5      | 8, 9  | 16                  | 17      | false  | false        | false     | 0    |
      | 19 | Techno boom      | Open sourced software to generate techno music    | 2       | 6      | 10    | 12                  | 15      | false  | false        | false     | 0    |
    And I am logged in as the user with email "user.steve@gmail.com" and password "SteveIsAwesome01"


	# Normal Flow

  Scenario Outline: Successfully modify an idea
    When the user requests to modify the field "<field>" to become "<new_value>" instead of "<old_value>" for idea with id "<id>"
    Then the idea with id "<id>" will have value "<new_value>" for the field "<field>"

    Examples:
      | id | title             | field        | old_value     | new_value    |
      | 1  | Home Care App     | title        | Home Care App | Health App   |
      | 1  | Home Care App     | date         | 2022-02-15    | 2022-01-15   |
      | 2  | Football Game     | purpose      | Entertain     | FIFA Product |
      | 2  | Football Game     | descriptions | For fun       | La Liga      |
      | 3  | Car Detection App | isPaid       | True          | False        |
      | 3  | Car Detection App | inProgress   | False         | True         |
      | 4  | Circuit Design    | isPrivate    | True          | False        |
      | 4  | Circuit Design    | domains      | Electrical    | Software     |
      | 1  | Home Care App     | topics       | Frontend Dev  | Backend Dev  |
      | 2  | Football Game     | techs        | Python        | Java, React  |
      | 3  | Car Detection App | image URLs   | keepitup.com  | sayless.com  |
      | 4  | Circuit Design    | icon URL     | bestteam.com  | another.com  |

	# Alternate Flow

  Scenario Outline: Successfully modify the idea with an empty field that can be empty
    When the user requests to modify the field "<field>" to become empty for idea with id "<id>"
    Then the idea with id "<id>" will have empty for the field "<field>"

    Examples:
      | id | title             | field      |
      | 4  | Circuit Design    | domains    |
      | 1  | Home Care App     | topics     |
      | 2  | Football Game     | techs      |
      | 3  | Car Detection App | image URLs |

	# Error Flow

  Scenario Outline: Unsuccessfully modify the idea with an empty field that cannot be empty
    When the user requests to modify the field "<field>" to become empty for idea with id "<id>"
    Then the idea with id "<id>" will have value "<old_value>" for the field "<field>"
    Then the error message "<error>" will be thrown with status code "<Http_status>"

    Examples:
      | id | title         | field        | old_value     | error                                 | Http_status |
      | 1  | Home Care App | title        | Home Care App | Necessary fields have been left empty | 400         |
      | 2  | Football Game | purpose      | Entertain     | Necessary fields have been left empty | 400         |
      | 2  | Football Game | descriptions | For fun       | Necessary fields have been left empty | 400         |

	# Error Flow

  Scenario Outline: (Error Flow) Unsuccessfully modify an idea with a non-existing object
    When the user requests to modify the field "<field>" to become new value "<new value>" for idea with id "<id>"
    Then the idea with id "<id>" will have value "<old_value>" for the field "<field>"
    Then the error message "<error>" will be thrown with status code "<Http_status>"

    Examples:
      | id | title             | field      | old_value    | error                                                                 | Http_status |
      | 4  | Circuit Design    | domains    | Electrical   | You are attempting to link your idea to an object that does not exist | 400         |
      | 1  | Home Care App     | topics     | Frontend Dev | You are attempting to link your idea to an object that does not exist | 400         |
      | 2  | Football Game     | techs      | Python       | You are attempting to link your idea to an object that does not exist | 400         |
      | 3  | Car Detection App | image URLs | keepitup.com | You are attempting to link your idea to an object that does not exist | 400         |
      | 4  | Circuit Design    | icon URL   | bestteam.com | You are attempting to link your idea to an object that does not exist | 400         |



