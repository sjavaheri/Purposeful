Feature: Respond to Collaboration Requests
  As a user who has created an idea, I want to be able to choose to accept or ignore a collaboration request for an idea I have created so that the person I respond to receives a custom message from me with my contact information

  Background:
    Given the id map is initialized
    And the database contains the following RegularUser accounts (ID025):
      | id | firstname | lastname | email                    | password     |
      | 1  | John      | Goblikon | john.goblikon@gmail.com  | P@ssWord1234 |
      | 2  | Jane      | Doe      | jane.doe@gmail.com       | P@ssWord1234 |
      | 3  | Wassim    | Jabbour  | wassim.jabbour@gmail.com | P@ssWord1234 |
    And the database contains the following domains (ID025):
      | id | name       |
      | 3  | Software   |
      | 4  | English    |
      | 5  | Electrical |
    And the database contains the following topics (ID025):
      | id | name              |
      | 6  | Web Dev           |
      | 7  | Game Dev          |
      | 8  | Embedded Software |
      | 9  | CLI Tool          |
      | 10 | Other             |
    And the database contains the following techs (ID025):
      | id | name       |
      | 11 | Rust       |
      | 12 | TypeScript |
      | 13 | Go         |
      | 14 | C++        |
      | 15 | Other      |
    And the database contains the following urls (ID025):
      | id | url      |
      | 16 | test.com |
    And the database contains the following ideas (ID025):
      # Date in ms from the unix epoch (Other constructors are deprecated)
      | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | author | isPrivate | user |
      | 17 | Website Idea      | 2       | 5      | 10,11,12 | 1500 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
      | 18 | Video Game        | 2       | 6      | 13       | 1400 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
      | 19 | Microcontroller   | 2,4     | 7      | 10,13    | 1300 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
      | 20 | Command-Line tool | 2       | 8      | 10       | 1200 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
      | 21 | Novel             | 3       | 9      | 14       | 1100 | Cool idea   | 15      | Great idea | 3      | False     | 3    |
    And the database contains the following collaboration requests (ID025):
      | id | ideaId | userId | additionalContact | message                                                                     |
      | 23 | 17     | 2      | 438-764-1940      | Hi, I would like to join this project!                                      |
      | 24 | 18     | 2      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 25 | 20     | 3      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 26 | 21     | 2      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 27 | 20     | 1      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 28 | 17     | 3      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 29 | 18     | 3      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
    And the database contains the following collaboration responses (ID025):
    # No need for contact info if rejected
      | id | additionalContact                 | message                                                        | status   | collaborationRequestId |
      | 31 | "Hi, my contact is +438-764-1940" | Hi, could you develop on your experience in that field please. | Accepted | 23                     |
      | 32 | "Hi, my contact is +438-764-1940" | Hi, could you develop on your experience in that field please. | Accepted | 24                     |
      | 33 |                                   | No sorry :(                                                    | Rejected | 25                     |

  # Normal/alternate flows
  Scenario Outline: User successfully accepts collaboration request for their created idea
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user approves the collaboration request with id "<collaboration_request_id>" using message "<message>" and additional contact "<additional_contact>"
    Then the collaboration request with id "<collaboration_request_id>" has an associated collaboration response with status "Approved", message "<message>" and additional contact "<additional_contact>"

    Examples:
      | email                   | password     | collaboration_request_id | message                                                        | additional_contact |
      | john.goblikon@gmail.com | P@ssWord1234 | 23                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |
      | jane.doe@gmail.com      | P@ssWord1234 | 24                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |

  Scenario Outline: User successfully declines collaboration request for their created idea
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user declines the collaboration request with id "<collaboration_request_id>" using message "<message>" and additional contact "<additional_contact>"
    Then the collaboration request with id "<collaboration_request_id>" has an associated collaboration response with status "Declined", message "<message>" and additional contact "<additional_contact>"

    Examples:
      | email                   | password     | collaboration_request_id | message                                                        | additional_contact |
      | john.goblikon@gmail.com | P@ssWord1234 | 23                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |
      | jane.doe@gmail.com      | P@ssWord1234 | 24                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |


  # Error flows
  Scenario Outline: User tries to decline a collaboration request for an idea that was already Accepted
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user erroneously declines the collaboration request with id "<collaboration_request_id>"
    Then the user shall receive the error message "This collaboration request has already been accepted and its status cannot be changed" with status 409

    Examples:
      | email                   | password     | collaboration_request_id |
      | jane.doe@gmail.com      | P@ssWord1234 | 25                       |
      | john.goblikon@gmail.com | P@ssWord1234 | 28                       |

  Scenario Outline: User tries to accept a collaboration request for an idea that was already Declined
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user erroneously accepts the collaboration request with id "<collaboration_request_id>"
    Then the user shall receive the error message "This collaboration request has already been declined and its status cannot be changed" with status 409

    Examples:
      | email                   | password     | collaboration_request_id |
      | jane.doe@gmail.com      | P@ssWord1234 | 27                       |
      | john.goblikon@gmail.com | P@ssWord1234 | 29                       |
