Feature: Access Created Ideas
  As a user, I want to see all ideas that I have created so that I can recall what ideas I have put on the app

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
      | id | title            | purpose      | description | domains | topics | techs | supportingImageUrls | iconUrl | isPaid | isInProgress | isPrivate | user |
      | 18 | Music generation | Open source  | extra info1 | 2       | 5      | 8,9   | 16                  | 17      | false  | false        | false     | 0    |
      | 19 | Techno boom      | Techno music | extra info2 | 2       | 6      | 10    | 12                  | 15      | false  | false        | false     | 0    |
      | 20 | Signal process   | Open source  | extra info3 | 2       | 7      | 9,10  | 15                  | 16      | false  | false        | false     | 1    |
      | 21 | Art blog site    | Open source  | extra info4 | 2       | 8      | 11    | 13                  | 12      | true   | false        | false     | 1    |

    # Normal Flow

    Scenario Outline: Successfully access all created ideas
        Given user with email "<email>" and password "<password>" is successfully logged in
        When the user requests to access all ideas associated to them
        Then then all ideas with ids "<idea_ids>" will be provided

        Examples:
        | email                | password            | idea_ids |
        | user.steve@gmail.com | SteveIsAwesome01    | 18,19    |
        | user.bob@gmail.com   | BobIsAwesome01      | 20,21    |