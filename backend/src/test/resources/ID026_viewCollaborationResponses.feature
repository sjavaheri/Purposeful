Feature: view collaboration responses
As a user, I want to be able to view the response from the idea creator to the collobation request that I sent so that I can begin to work on the idea

  Background:
    Given the id map is initialized
    And the database contains the following RegularUser accounts:
      | id | firstname | lastname | email                    | password     |
      | 1  | John      | Goblikon | john.goblikon@gmail.com  | P@ssWord1234 |
      | 2  | Jane      | Doe      | jane.doe@gmail.com       | P@ssWord1234 |
      | 3  | Wassim    | Jabbour  | wassim.jabbour@gmail.com | P@ssWord1234 |
    And the database contains the following Domains:
      | id | name       |
      | 3  | Software   |
      | 4  | English    |
      | 5  | Electrical |
    And the database contains the following Topics:
      | id | name              |
      | 6  | Web Dev           |
      | 7  | Game Dev          |
      | 8  | Embedded Software |
      | 9  | CLI Tool          |
      | 10 | Other             |
    And the database contains the following Techs:
      | id | name       |
      | 11 | Rust       |
      | 12 | TypeScript |
      | 13 | Go         |
      | 14 | C++        |
      | 15 | Other      |
    And the database contains the following Urls:
      | id | url      |
      | 16 | test.com |
    And the database contains the following Ideas:
      # Date in ms from the unix epoch (Other constructors are deprecated)
      | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | author | isPrivate | user |
      | 17 | Website Idea      | 2       | 5      | 10,11,12 | 1500 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
      | 18 | Video Game        | 2       | 6      | 13       | 1400 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
      | 19 | Microcontroller   | 2,4     | 7      | 10,13    | 1300 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
      | 20 | Command-Line tool | 2       | 8      | 10       | 1200 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
      | 21 | Novel             | 3       | 9      | 14       | 1100 | Cool idea   | 15      | Great idea | 3      | False     | 3    |
    And the database contains the following CollaborationRequests:
      | id | ideaId | userId | status   | additionalContact | message                                                                       |
      | 23 | 17     | 2      | Pending  | "438-764-1940"    | "Hi, I would like to join this project!"                                      |
      | 24 | 18     | 2      | Pending  | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 25 | 20     | 3      | Accepted | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 26 | 21     | 2      | Pending  | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 27 | 20     | 1      | Declined | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 28 | 17     | 3      | Accepted | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 29 | 18     | 3      | Declined | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 30 | 19     | 1      | Pending  | null              | "Hi, I have experience in that field and I am interested in working on this!" |
    And the database contains the following CollaborationResponses:
      | id | collaborationRequestId | additionalContact                 | message                                                          |
      | 31 | 23                     | "Hi, my contact is +438-764-1940" | "Hi, could you develop on your experience in that field please." |
      | 32 | 24                     | ""                                | ""                                                               |

  # normal flow

  Scenario Outline: successfully view collaboration responses for a collaboration request
    Given I am logged in as the user with email "<email>" and password "<password>"
    When the user requests to access the collaboration responses for the collaboration request with id "<collaborationRequestId>"
    Then the user shall have access to the collaboration responses with ids "<collaborationResponseIds>"

    Examples:
      | email              | password     | collaborationRequestId | collaborationResponseIds |
      | jane.doe@gmail.com | P@ssWord1234 | 23                     | 31                       |
      | jane.doe@gmail.com | P@ssWord1234 | 24                     | 32                       |

  # alternate flow

  Scenario Outline: try to access collaboration responses for a collaboration request that does not have any responses
    Given I am logged in as the user with email "<email>" and password "<password>"
    When the user requests to access the collaboration responses for the collaboration request with id "<collaborationRequestId>"
    Then the user shall not have access to any collaboration responses

    Examples:
      | email                   | password     | collaborationRequestId |
      | jane.doe@gmail.com      | P@ssword1234 | 26                     |
      | john.goblikon@gmail.com | P@ssword1234 | 30                     |

  # error flow

  Scenario: try to access collaboration responses for a collaboration request that does not belong to the user
    Given I am logged in as the user with email "john.goblikon@gmail.com" and password "P@ssword1234"
    When the user requests to access the collaboration responses for the collaboration request with id "23"
    Then the user shall receive the error message "You do not have permission to access this collaboration request." with status "401"