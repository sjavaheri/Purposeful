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
            | 3  | Software   |
            | 4  | English    |
            | 5  | Electrical |
        And the database contains the following topics (ID022):
            | id | name              |
            | 6  | Web Dev           |
            | 7  | Game Dev          |
            | 8  | Embedded Software |
            | 9  | CLI Tool          |
            | 10 | Other             |
        And the database contains the following techs (ID022):
            | id | name       |
            | 11 | Rust       |
            | 12 | TypeScript |
            | 13 | Go         |
            | 14 | C++        |
            | 15 | Other      |
        And the database contains the following urls (ID022):
            | id | url      |
            | 16 | test.com |
        And the database contains the following ideas (ID022):
            # Date in ms from the unix epoch (Other constructors are deprecated)
            | id | title             | domains | topics | techs    | date | description | iconUrl | purpose    | author | isPrivate | user |
            | 17 | Website Idea      | 2       | 5      | 10,11,12 | 1500 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
            | 18 | Video Game        | 2       | 6      | 13       | 1400 | Cool idea   | 15      | Great idea | 1      | False     | 1    |
            | 19 | Microcontroller   | 2,4     | 7      | 10,13    | 1300 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
            | 20 | Command-Line tool | 2       | 8      | 10       | 1200 | Cool idea   | 15      | Great idea | 2      | False     | 2    |
            | 21 | Novel             | 3       | 9      | 14       | 1100 | Cool idea   | 15      | Great idea | 3      | False     | 3    |
        And the database contains the following collaboration requests (ID022):
            | id | ideaId | userId | status   | additionalContact | message                                                                       |
            | 23 | 17     | 2      | Pending  | "438-764-1940"    | "Hi, I would like to join this project!"                                      |
            | 24 | 18     | 2      | Declined | null              | "Hi, I have experience in that field and I am interested in working on this!" |
            | 25 | 20     | 3      | Accepted | null              | "Hi, I have experience in that field and I am interested in working on this!" |
            | 26 | 21     | 2      | Pending  | null              | "Hi, I have experience in that field and I am interested in working on this!" |

    # Normal/alternate flows
    Scenario Outline: Successfully send collaboration request
        Given I am logged in as the user with email "<email>" and password "<password>"
        When I request to send a collaboration request to the creator of the idea with id "<idea_id>" with message "<message>" and additionalContact "<additionalContact>"
        Then the number of collaboration requests in the database should be "<total_num_requests>"
        And the following collaboration request shall exist in the database:
            | ideaId    | userId    | status  | additionalContact     | message     |
            | <idea_id> | <user_id> | Pending | "<additionalContact>" | "<message>" |
        And the number of incoming collaboration requests for the creator of the idea with id "<idea_id>" shall be "<num_incoming_requests>"
        And the number of outgoing collaboration requests for the user with id "<user_id>" shall be "<num_outgoing_requests>"

        Examples:
            | email                    | password     | idea_id | user_id | total_num_requests | message                               | additionalContact | num_incoming_requests | num_outgoing_requests |
            | wassim.jabbour@gmail.com | P@ssWord1234 | 17      | 3       | 5                  | "Hi, please let me join your project" | null              | 3                     | 2                     |
            | wassim.jabbour@gmail.com | P@ssWord1234 | 18      | 3       | 5                  | "Hi, please let me join your project" | null              | 3                     | 2                     |
            | john.goblikon@gmail.com  | P@ssWord1234 | 20      | 2       | 5                  | "Hi, please let me join your project" | "428-8794-4237"   | 2                     | 1                     |
            | john.goblikon@gmail.com  | P@ssWord1234 | 21      | 3       | 5                  | "Hi, please let me join your project" | "428-8794-4238"   | 2                     | 1                     |


    # Error flows
    Scenario Outline: Unsuccessfully send collaboration request
        Given I am logged in as the user with email "<email>" and password "<password>"
        When I request to send a collaboration request to the creator of the idea with id "<idea_id>" with message "<message>" and additionalContact "<additionalContact>"
        Then the number of collaboration requests in the database should be "4"
        And the number of incoming collaboration requests for the creator of the idea with id "<idea_id>" shall be "<num_incoming_requests>"
        And the number of outgoing collaboration requests for the user with email "email" shall be "<num_outgoing_requests>"
        And the following error message shall be returned "<errorMessage>"

        Examples:
            | email                    | password     | idea_id | message                               | additionalContact | num_incoming_requests | num_outgoing_requests | errorMessage                                                            |
            | jane.doe@gmail.com       | P@ssWord1234 | 17      | "Hi, please let me join your project" | null              | 2                     | 3                     | You can only send one collaboration request to this user for this idea  |
            | jane.doe@gmail.com       | P@ssWord1234 | 18      | "Hi, please let me join your project" | null              | 2                     | 3                     | You can only send one collaboration request to this user for this idea  |
            | wassim.jabbour@gmail.com | P@ssWord1234 | 20      | "Hi, please let me join your project" | "428-8794-4237"   | 1                     | 1                     | You can only send one collaboration request to this user for this idea  |
            | jane.doe@gmail.com       | P@ssWord1234 | 21      | "Hi, please let me join your project" | "428-8794-4238"   | 1                     | 3                     | You can only send one collaboration request to this user for this ideaF |
            | wassim.jabbour@gmail.com | P@ssWord1234 | 21      | "Hi, please let me join your project" | "428-8794-4239"   | 1                     | 3                     | Cannot send a collaboration request to oneself                          |
            | jane.doe@gmail.com       | P@ssWord1234 | 20      | "Hi, please let me join your project" | "428-8794-4230"   | 1                     | 1                     | Cannot send a collaboration request to oneself                          |
            | john.goblikon@gmail.com  | P@ssWord1234 | 17      | "Hi, please let me join your project" | "428-8794-4231"   | 2                     | 0                     | Cannot send a collaboration request to oneself                          |