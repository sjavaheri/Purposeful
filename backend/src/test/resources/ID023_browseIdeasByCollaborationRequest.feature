Feature: Browse ideas by collaboration request.
  As a user, I want to be able to access all the ideas that I have requested to collaborate on, so that I can keep track of my interests and view responses from idea creators

  Background:
    Given the id map is initialized
    And the database contains the following RegularUser accounts (ID023):
      | id | firstname | lastname | email                    | password     |
      | 1  | John      | Goblikon | john.goblikon@gmail.com  | P@ssWord1234 |
      | 2  | Jane      | Doe      | jane.doe@gmail.com       | P@ssWord1234 |
      | 3  | Wassim    | Jabbour  | wassim.jabbour@gmail.com | P@ssWord1234 |
    And the database contains the following domains (ID023):
      | id | name       |
      | 4  | Software   |
      | 5  | English    |
      | 6  | Electrical |
    And the database contains the following topics (ID023):
      | id | name              |
      | 7  | Web Dev           |
      | 8  | Game Dev          |
      | 9  | Embedded Software |
      | 10 | CLI Tool          |
      | 11 | Other             |
    And the database contains the following techs (ID023):
      | id | name       |
      | 12 | Rust       |
      | 13 | TypeScript |
      | 14 | Go         |
      | 15 | C++        |
      | 16 | Other      |
    And the database contains the following urls (ID023):
      | id | url       |
      | 17 | test.com  |
      | 18 | test1.com |
      | 19 | test2.com |
      | 20 | test3.com |
      | 21 | test4.com |
    And the database contains the following ideas (ID023):
      # Date in ms from the unix epoch (Other constructors are deprecated)
      | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | author | isPrivate | user |
      | 22 | Website Idea      | 4       | 7      | 12,13,14 | 1500 | Cool idea   | 17      | Great idea | 1      | False     | 1    |
      | 23 | Video Game        | 4       | 8      | 15       | 1400 | Cool idea   | 18      | Great idea | 1      | False     | 1    |
      | 24 | Microcontroller   | 4,6     | 9      | 12,15    | 1300 | Cool idea   | 19      | Great idea | 2      | False     | 2    |
      | 25 | Command-Line tool | 4       | 10     | 12       | 1200 | Cool idea   | 20      | Great idea | 2      | False     | 2    |
      | 26 | Novel             | 5       | 11     | 16       | 1100 | Cool idea   | 21      | Great idea | 3      | False     | 3    |
    And the database contains the following collaboration requests (ID023):
      | id | ideaId | userId | status   | additionalContact | message                                                                       |
      | 27 | 22     | 2      | Pending  | "438-764-1940"    | "Hi, I would like to join this project!"                                      |
      | 28 | 23     | 2      | Declined | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 29 | 24     | 3      | Accepted | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 30 | 26     | 2      | Pending  | null              | "Hi, I have experience in that field and I am interested in working on this!" |

  # Normal/alternate flows
  Scenario Outline: Successfully browse ideas by collaboration request
    Given I am logged in as the user with email "<email>" and password "<password>" (ID023)
    When the user requests to browse ideas by collaboration request
    Then the ideas with ids "<idea_ids>" shall be displayed to the user

    Examples:
      | email                    | password     | idea_ids |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 24       |
      | jane.doe@gmail.com       | P@ssWord1234 | 22,23,26 |
      | john.goblikon@gmail.com  | P@ssWord1234 |          |


    # Error Flow
  Scenario: Unsuccessfully access created ideas if logged out
    When the user requests to browse ideas by collaboration request
    Then the status code "401" unauthorized error will be received (ID023)