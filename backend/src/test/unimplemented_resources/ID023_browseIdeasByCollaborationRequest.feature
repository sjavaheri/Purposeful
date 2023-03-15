Feature: Browse ideas by collaboration request.
  As a user, I want to be able to access all the ideas that I have requested to collaborate on, so that I can keep track of my interests and view responses from idea creators

  Background:
    Given the id map is initialized
    And the database contains the following RegularUser accounts (Strategy1):
      | id | firstname | lastname | email                    | password     |
      | 1  | John      | Goblikon | john.goblikon@gmail.com  | P@ssWord1234 |
      | 2  | Jane      | Doe      | jane.doe@gmail.com       | P@ssWord1234 |
      | 3  | Wassim    | Jabbour  | wassim.jabbour@gmail.com | P@ssWord1234 |
    And the database contains the following domains (Strategy1):
      | id | name       |
      | 3  | Software   |
      | 4  | English    |
      | 5  | Electrical |
    And the database contains the following topics (Strategy1):
      | id | name              |
      | 6  | Web Dev           |
      | 7  | Game Dev          |
      | 8  | Embedded Software |
      | 9  | CLI Tool          |
      | 10 | Other             |
    And the database contains the following techs (Strategy1):
      | id | name       |
      | 11 | Rust       |
      | 12 | TypeScript |
      | 13 | Go         |
      | 14 | C++        |
      | 15 | Other      |
    And the database contains the following urls (Strategy1):
      | id | url      |
      | 16 | test.com |
    And the database contains the following ideas (Strategy1):
      # Date in ms from the unix epoch (Other constructors are deprecated)
      | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | author | isPrivate | user |
      | 17 | Website Idea      | 2       | 5      | 10,11,12 | 1500 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
      | 18 | Video Game        | 2       | 6      | 13       | 1400 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
      | 19 | Microcontroller   | 2,4     | 7      | 10,13    | 1300 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
      | 20 | Command-Line tool | 2       | 8      | 10       | 1200 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
      | 21 | Novel             | 3       | 9      | 14       | 1100 | Cool idea   | 15      | Great idea | 3      | False     | 3    |
    And the database contains the following collaboration request:
      | id | ideaId | userId | status   | additionalContact | message                                                                       |
      | 23 | 17     | 2      | Pending  | "438-764-1940"    | "Hi, I would like to join this project!"                                      |
      | 24 | 18     | 2      | Declined | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 25 | 20     | 3      | Accepted | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 26 | 21     | 2      | Pending  | null              | "Hi, I have experience in that field and I am interested in working on this!" |

  # Normal/alternate flows
  Scenario Outline: Successfully browse ideas by collaboration request
    Given I am logged in as the user with email "<email>" and password "<password>"
    When the user requests to browse ideas by collaboration request
    Then the user shall have access to the ideas with ids "<idea_ids>"

    Examples:
      | email                    | password     | idea_ids |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 20       |
      | jane.doe@gmail.com       | P@ssWord1234 | 23,24,26 |
      | john.goblikon@gmail.com  | P@ssWord1234 |          |