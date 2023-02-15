Feature: Browse ideas by domain by Domain, Topic, or Tech
  As a user, I want to be able to browse public ideas that are linked to a specific domain, so that I can find and learn about new and innovative ideas in my area of interest.

  Background:
    Given the database contains the following RegularUser accounts:
      | id | name          | email                  | password     |
      | 1  | John Goblikon | john.goblikon@mail.com | P@ssWord1234 |
    And the database contains the following domains:
      | id | name       |
      | 1  | Software   |
      | 2  | English    |
      | 3  | Electrical |
    And the database contains the following topics:
      | id | name              |
      | 1  | Web Dev           |
      | 2  | Game Dev          |
      | 3  | Embedded Software |
      | 4  | CLI Tool          |
      | 5  | Other             |
    And the database contains the following techs:
      | id | name       |
      | 1  | Rust       |
      | 2  | TypeScript |
      | 3  | Go         |
      | 4  | C++        |
      | 5  | Other      |
    And the database contains the following ideas:
      | id | title             | domains | topics | techs   |
      | 1  | Website Idea      | 1       | 1      | 1, 2, 3 |
      | 2  | Video Game        | 1       | 2      | 4       |
      | 3  | Microcontroller   | 1, 3    | 3      | 1, 4    |
      | 4  | Command-Line tool | 1       | 4      | 1       |
      | 5  | Novel             | 2       | 5      | 5       |
      | 6  | Circuit           | 3       | 5      | 5       |
    And I am logged in as the user with email "john.goblikon@mail.com" and password "P@ssWord1234"


  # Normal/alternate flows

  Scenario Outline: Successfully browse ideas by domain, topic, and tech
    When the user requests to browse ideas by domains <domain_ids>, topics <topic_ids>, and techs <techs_ids>
    Then the user shall have access to the ideas with ids <idea_ids>

    Examples:
      | domain_ids | topic_ids | techs_ids | idea_ids         |
      | null       | null      | null      | 1, 2, 3, 4, 5, 6 |
      | 1          | null      | null      | 1, 2, 3, 4       |
      | 2          | null      | null      | 5                |
      | 3          | null      | null      | 3, 6             |
      | null       | 1         | null      | 1                |
      | null       | 2         | null      | 2                |
      | null       | 3         | null      | 3                |
      | null       | 4         | null      | 4                |
      | null       | 5         | null      | 5, 6             |
      | null       | null      | 1         | 1, 3, 4          |
      | null       | null      | 2         | 1                |
      | null       | null      | 3         | 1                |
      | null       | null      | 4         | 2, 3             |
      | null       | null      | 5         | 5, 6             |
      | 1, 2       | null      | null      | 1, 2, 3, 4, 5    |
      | 2, 3       | null      | null      | 3, 5, 6          |
      | 1, 3       | null      | null      | 1, 2, 3, 4, 6    |
      | 1, 2, 3    | null      | null      | 1, 2, 3, 4, 5, 6 |
      | 1          | 1         | null      | 1                |
      | 1          | 2         | 1         | null             |
      | 3          | null      | 1         | 3                |

  # Error flows

  Scenario Outline: Browse ideas by domain, topic, or tech that does not exist
    When the user requests to browse ideas by domains <domain_ids>, topics <topic_ids>, and techs <techs_ids>
    Then the user shall recieve the error message <error_message> with status <HTTP_status>

    Examples:
      | domain_ids | topic_ids | techs_ids | error_message                                     | HTTP_status |
      | 4          | null      | null      | The requested domain does not exist.              | 400         |
      | null       | 6         | null      | The requested topic does not exist.               | 400         |
      | null       | null      | 6         | The requested tech does not exist.                | 400         |
