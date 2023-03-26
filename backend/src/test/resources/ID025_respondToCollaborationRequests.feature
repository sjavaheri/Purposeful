Feature: Respond to Collaboration Requests
  As a user who has created an idea, I want to be able to choose to accept or ignore a collaboration request for an idea I have created so that the person I respond to receives a custom message from me with my contact information

  Background:
    Given the id map is initialized (ID025)
    And the database contains the following RegularUser accounts (ID025):
      | id | firstname | lastname | email                    | password     |
      | 1  | John      | Goblikon | john.goblikon@gmail.com  | P@ssWord1234 |
      | 2  | Jane      | Doe      | jane.doe@gmail.com       | P@ssWord1234 |
      | 3  | Wassim    | Jabbour  | wassim.jabbour@gmail.com | P@ssWord1234 |
    And the database contains the following domains (ID025):
      | id | name       |
      | 4  | Software   |
      | 5  | English    |
      | 6  | Electrical |
    And the database contains the following topics (ID025):
      | id | name              |
      | 7  | Web Dev           |
      | 8  | Game Dev          |
      | 9  | Embedded Software |
      | 10 | CLI Tool          |
      | 11 | Other             |
    And the database contains the following techs (ID025):
      | id | name       |
      | 12 | Rust       |
      | 13 | TypeScript |
      | 14 | Go         |
      | 15 | C++        |
      | 16 | Other      |
    And the database contains the following urls (ID025):
      | id | url      |
      | 17 | test.com |
    And the database contains the following ideas (ID025):
      # Date in ms from the unix epoch (Other constructors are deprecated)
      | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | isPrivate | user |
      | 18 | Website Idea      | 4       | 7      | 12,13,14 | 1500 | Cool idea   | 17      | Great idea | False     | 1    |
      | 19 | Video Game        | 4       | 8      | 15       | 1400 | Cool idea   | 17      | Great idea | False     | 1    |
      | 20 | Microcontroller   | 4,6     | 9      | 12,15    | 1300 | Cool idea   | 17      | Great idea | False     | 2    |
      | 21 | Command-Line tool | 4       | 10     | 12       | 1200 | Cool idea   | 17      | Great idea | False     | 2    |
      | 22 | Novel             | 5       | 11     | 16       | 1100 | Cool idea   | 17      | Great idea | False     | 3    |
    And the database contains the following collaboration requests (ID025):
      | id | ideaId | userId | additionalContact | message                                                                     |
      | 23 | 18     | 2      | 438-764-1940      | Hi, I would like to join this project!                                      |
      | 24 | 19     | 2      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 25 | 21     | 3      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 26 | 22     | 2      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 27 | 21     | 1      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 28 | 18     | 3      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
      | 29 | 19     | 3      | 438-764-1940      | Hi, I have experience in that field and I am interested in working on this! |
    And the database contains the following collaboration responses (ID025):
    # No need for contact info if rejected (empty string here will be converted to null automatically by cucumber)
      | id | additionalContact               | message                                                        | status   | collaborationRequestId |
      | 30 | Hi, my contact is +438-764-1940 | Hi, could you develop on your experience in that field please. | Approved | 23                     |
      | 31 | Hi, my contact is +438-764-1940 | Hi, could you develop on your experience in that field please. | Approved | 24                     |
      | 32 |                                 | No sorry :(                                                    | Declined | 25                     |

  # Normal/alternate flows
  Scenario Outline: User successfully accepts collaboration request for their created idea
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user approves the collaboration request with id "<collaboration_request_id>" using message "<message>" and additional contact "<additional_contact>"
    Then the collaboration request with id "<collaboration_request_id>" has an associated collaboration response with status "Approved", message "<message>" and additional contact "<additional_contact>"
    And the number of collaboration responses in the database is "4"

    Examples:
      | email                    | password     | collaboration_request_id | message                                                        | additional_contact |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 26                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |
      | jane.doe@gmail.com       | P@ssWord1234 | 27                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |
      | john.goblikon@gmail.com  | P@ssWord1234 | 28                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |
      | john.goblikon@gmail.com  | P@ssWord1234 | 29                       | Hi, could you develop on your experience in that field please. | 438-764-1940       |

  Scenario Outline: User successfully declines collaboration request for their created idea
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user declines the collaboration request with id "<collaboration_request_id>" using message "<message>"
    Then the collaboration request with id "<collaboration_request_id>" has an associated collaboration response with status "Declined", message "<message>" and no additional contact

    Examples:
      | email                    | password     | collaboration_request_id | message                                                        |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 26                       | Hi, could you develop on your experience in that field please. |
      | jane.doe@gmail.com       | P@ssWord1234 | 27                       | Hi, could you develop on your experience in that field please. |
      | john.goblikon@gmail.com  | P@ssWord1234 | 28                       | Hi, could you develop on your experience in that field please. |
      | john.goblikon@gmail.com  | P@ssWord1234 | 29                       | Hi, could you develop on your experience in that field please. |


  # Error flows
  Scenario Outline: User tries to accept a collaboration request erroneously
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user erroneously approves the collaboration request with id "<collaboration_request_id>" and message "<message>" and additional contact "<additional_contact>"
    Then the user shall receive the error message "<errorMessage>" with status "<status>" (ID025)

    Examples:
      | email                   | password     | collaboration_request_id | errorMessage                                      | status | message                                                        | additional_contact |
      | john.goblikon@gmail.com | P@ssWord1234 | 23                       | This collaboration request already has a response | 409    | Sounds good, text me at the provided number to get started     | 123-123-1234       |
      | john.goblikon@gmail.com | P@ssWord1234 | 24                       | This collaboration request already has a response | 409    | Sounds good, text me at the provided number to get started     | 123-123-1234       |
      | jane.doe@gmail.com      | P@ssWord1234 | 29                       | The handler is not the owner of the idea          | 403    | Hi, could you develop on your experience in that field please. | 438-764-1940       |
      | jane.doe@gmail.com      | P@ssWord1234 | 28                       | The handler is not the owner of the idea          | 403    | Hi, could you develop on your experience in that field please. | 438-764-1940       |

  Scenario Outline: User tries to decline a collaboration request erroneously
    Given I am logged in as the user with email "<email>" and password "<password>" (ID025)
    When the user erroneously declines the collaboration request with id "<collaboration_request_id>" and message "<message>"
    Then the user shall receive the error message "<errorMessage>" with status "<status>" (ID025)

    Examples:
      | email                   | password     | collaboration_request_id | errorMessage                                      | status | message |
      | john.goblikon@gmail.com | P@ssWord1234 | 23                       | This collaboration request already has a response | 409    | Nope    |
      | john.goblikon@gmail.com | P@ssWord1234 | 24                       | This collaboration request already has a response | 409    | Nope    |
      | jane.doe@gmail.com      | P@ssWord1234 | 29                       | The handler is not the owner of the idea          | 403    | Nope    |
      | jane.doe@gmail.com      | P@ssWord1234 | 28                       | The handler is not the owner of the idea          | 403    | Nope    |