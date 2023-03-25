Feature: Send a collaboration request
  As a user, I want to be able to request to collaborate on an idea with a custom message so that the person who created it can receive my contact information and can confirm whether they would like to collaborate.

  Background:
    Given the id map is initialized
    And the database contains the following RegularUser accounts (ID022):
      | id | firstname | lastname | email                    | password     |
      | 1  | John      | Goblikon | john.goblikon@gmail.com  | P@ssWord1234 |
      | 2  | Jane      | Doe      | jane.doe@gmail.com       | P@ssWord1234 |
      | 3  | Wassim    | Jabbour  | wassim.jabbour@gmail.com | P@ssWord1234 |
    And the database contains the following domains (ID022):
      | id | name       |
      | 4  | Software   |
      | 5  | English    |
      | 6  | Electrical |
    And the database contains the following topics (ID022):
      | id | name              |
      | 7  | Web Dev           |
      | 8  | Game Dev          |
      | 9  | Embedded Software |
      | 10 | CLI Tool          |
      | 11 | Other             |
    And the database contains the following techs (ID022):
      | id | name       |
      | 12 | Rust       |
      | 13 | TypeScript |
      | 14 | Go         |
      | 15 | C++        |
      | 16 | Other      |
    And the database contains the following urls (ID022):
      | id | url      |
      | 17 | test.com |
    And the database contains the following ideas (ID022):
            # Date in ms from the unix epoch (Other constructors are deprecated)
      | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | isPrivate | user |
      | 18 | Website Idea      | 4       | 7      | 12,13,14 | 1500 | Cool idea   | 17      | Great idea | False     | 1    |
      | 19 | Video Game        | 4       | 8      | 15       | 1400 | Cool idea   | 17      | Great idea | False     | 1    |
      | 20 | Microcontroller   | 4,6     | 9      | 12,15    | 1300 | Cool idea   | 17      | Great idea | False     | 2    |
      | 21 | Command-Line tool | 4       | 10     | 12       | 1200 | Cool idea   | 17      | Great idea | False     | 2    |
      | 22 | Novel             | 5       | 11     | 16       | 1100 | Cool idea   | 17      | Great idea | False     | 3    |
    And the database contains the following collaboration requests (ID022):
      | id | ideaId | userId | status   | additionalContact | message                                                                     |
      | 23 | 18     | 2      | Pending  | 438-764-1940      | Hi, I would like to join this project!                                      |
      | 24 | 19     | 2      | Declined | null              | Hi, I have experience in that field and I am interested in working on this! |
      | 25 | 21     | 3      | Accepted | null              | Hi, I have experience in that field and I am interested in working on this! |
      | 26 | 22     | 2      | Pending  | null              | Hi, I have experience in that field and I am interested in working on this! |

    # Normal/alternate flows
  Scenario Outline: Successfully send collaboration request
    Given I am logged in as the user with email "<email>" and password "<password>"
    When I successfully request to send a collaboration request to the creator of the idea with id "<idea_id>" with message "<message>" and additionalContact "<additionalContact>"
    Then the number of collaboration requests in the database should be "<total_num_requests>"
    And the collaboration request with idea_id "<idea_id>" and user_id "<user_id>" shall exist in the system

    Examples:
      | email                    | password     | idea_id | user_id | total_num_requests | message                             | additionalContact |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 18      | 3       | 5                  | Hi, please let me join your project | null              |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 19      | 3       | 5                  | Hi, please let me join your project | null              |
      | john.goblikon@gmail.com  | P@ssWord1234 | 22      | 1       | 5                  | Hi, please let me join your project | 428-8794-4237     |
      | john.goblikon@gmail.com  | P@ssWord1234 | 21      | 1       | 5                  | Hi, please let me join your project | 428-8794-4238     |


    # Error flows
  Scenario Outline: Unsuccessfully send collaboration request
    Given I am logged in as the user with email "<email>" and password "<password>" (ID022)
    When I unsuccessfully request to send a collaboration request to the creator of the idea with id "<idea_id>" with message "<message>" and additionalContact "<additionalContact>"
    Then the number of collaboration requests in the database should be "4"
    And the following error message shall be returned "<errorMessage>"

    Examples:
      | email                    | password     | idea_id | message                             | additionalContact | errorMessage                                                           |
      | jane.doe@gmail.com       | P@ssWord1234 | 18      | Hi, please let me join your project | null              | You can only send one collaboration request to this user for this idea |
      | jane.doe@gmail.com       | P@ssWord1234 | 19      | Hi, please let me join your project | null              | You can only send one collaboration request to this user for this idea |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 21      | Hi, please let me join your project | 428-8794-4237     | You can only send one collaboration request to this user for this idea |
      | jane.doe@gmail.com       | P@ssWord1234 | 22      | Hi, please let me join your project | 428-8794-4238     | You can only send one collaboration request to this user for this idea |
      | wassim.jabbour@gmail.com | P@ssWord1234 | 22      | Hi, please let me join your project | 428-8794-4239     | Cannot send a collaboration request to oneself                         |
      | jane.doe@gmail.com       | P@ssWord1234 | 21      | Hi, please let me join your project | 428-8794-4230     | Cannot send a collaboration request to oneself                         |
      | john.goblikon@gmail.com  | P@ssWord1234 | 18      | Hi, please let me join your project | 428-8794-4231     | Cannot send a collaboration request to oneself                         |
