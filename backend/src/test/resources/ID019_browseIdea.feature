Feature: Browse ideas by domain by Domain, Topic, or Tech
  As a user, I want to be able to browse public ideas that are linked to a specific domain, so that I can find and learn about new and innovative ideas in my area of interest.

  Background:
    Given the database contains the following domains:
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


  Normal/alternate flows

  Scenario Outline: Successfully browse ideas by domain
    When the user requests to browse ideas by the following domains:
      | id |
      | 1  |
      | 2  |
      | 3  |
    Then the user shall have access to the following ideas:
      | ids_list   |
      | 1, 2, 3, 4 |
      | 5          |
      | 3, 6       |

  Scenario Outline: Successfully browse ideas by topic
    When the user requests to browse ideas by the following topic:
      | id |
      | 1  |
      | 2  |
      | 3  |
      | 4  |
      | 5  |
    Then the user shall have access to the following ideas:
      | ids_list |
      | 1        |
      | 2        |
      | 3        |
      | 4        |
      | 5, 6     |

  Scenario Outline: Successfully browse ideas by technology
    When the user requests to browse ideas by the following technologies:
      | id |
      | 1  |
      | 2  |
      | 3  |
      | 4  |
      | 5  |
    Then the user shall have access to the following ideas:
      | ids_list |
      | 1, 3, 4  |
      | 1        |
      | 1        |
      | 2, 3     |
      | 5, 6     |
