Feature: view collaboration responses
  As a user, I want to be able to view the response from the idea creator to the collobation request that I sent so that I can begin to work on the idea

  Background:
    Given the database contains the following RegularUser accounts:
      | id | firstname | lastname | email                    | password     |
      | 0  | John      | Goblikon | john.goblikon@gmail.com  | P@ssWord1234 |
      | 1  | Jane      | Doe      | jane.doe@gmail.com       | P@ssWord1234 |
      | 2  | Wassim    | Jabbour  | wassim.jabbour@gmail.com | P@ssWord1234 |
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
      | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | isPrivate | user |
      | 17 | Website Idea      | 2       | 5      | 10,11,12 | 1500 | Cool idea   | 16      | Great idea | False     | 0    |
      | 18 | Video Game        | 2       | 6      | 13       | 1400 | Cool idea   | 16      | Great idea | False     | 0    |
      | 19 | Microcontroller   | 2,4     | 7      | 10,13    | 1300 | Cool idea   | 16      | Great idea | False     | 1    |
      | 20 | Command-Line tool | 2       | 8      | 10       | 1200 | Cool idea   | 16      | Great idea | False     | 1    |
      | 21 | Novel             | 3       | 9      | 14       | 1100 | Cool idea   | 16      | Great idea | False     | 2    |
    And the database contains the following CollaborationResponses:
      | id | additionalContact                 | status   | message                                                          |
      | 31 | ""                                | Declined | "Hi, could you develop on your experience in that field please." |
      | 32 | "Hi, my contact is +438-764-1940" | Approved | "I'd love to work with you!"                                     |
    And the database contains the following CollaborationRequests:
      | id | ideaId | collaborationResponseId | userId | additionalContact | message                                                                       |
      | 23 | 17     | 31                      | 1      | "438-764-1940"    | "Hi, I would like to join this project!"                                      |
      | 24 | 18     | 32                      | 1      | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 25 | 20     | null                    | 2      | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 26 | 21     | null                    | 1      | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 27 | 20     | null                    | 0      | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 28 | 17     | null                    | 2      | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 29 | 18     | null                    | 2      | null              | "Hi, I have experience in that field and I am interested in working on this!" |
      | 30 | 19     | null                    | 0      | null              | "Hi, I have experience in that field and I am interested in working on this!" |
    And I am logged in as the user with the email "jane.doe@gmail.com" and password "P@ssWord1234"

  # normal flow

  Scenario Outline: successfully view collaboration response for a collaboration request
    When the user requests to access the collaboration response for the idea with id "<ideaId>"
    Then the user shall have access to the collaboration response with id "<collaborationResponseIds>"

    Examples:
      | email              | password     | ideaId | collaborationResponseIds |
      | jane.doe@gmail.com | P@ssWord1234 | 17     | 31                       |
      | jane.doe@gmail.com | P@ssWord1234 | 18     | 32                       |


  # error flows

  Scenario: try to access collaboration response for an idea that does not have any responses yet
    When the user requests to access the collaboration response for the idea with id "21"
    Then the user shall receive the error message "There is no response for this collaboration request" with status code 404

  Scenario: try to access collaboration response for an idea on which the user has not made a collaboration request
    When the user requests to access the collaboration response for the idea with id "19"
    Then the user shall receive the error message "You did not send a collaboration request for this idea" with status code 400