Feature: Modify Moderator
    As a user, I want to be able to express my support for an idea by giving it a "high five," so that I can express my positive sentiment for an idea without adding to a numerical score or ranking.

    # reaction database (date is omitted for brevity)

    Background:
        Given the reaction database contains the following ideas and regularUsers:
            | id  | idea_id | user_id | reaction_type |
            | 001 | 021     | 457     | HighFive      |
            | 002 | 543     | 223     | HighFive      |
        And the user with user_id "223" is logged in with email "user.steve@gmail.com" and password "SteveIsAwesome01"
        And the user with user_id "223" is viewing the idea with id "021"

    # Normal Flow

    Scenario: Successfully high five an idea
        Given that the user with id "223" high fives an idea with id "021"
        Then a new entry of type "HighFive" shall be added to the reaction database with the following details:
            | id  | idea_id | user_id | reaction_type |
            | 001 | 021     | 457     | HighFive      |
            | 002 | 543     | 223     | HighFive      |
            | 003 | 021     | 223     | HighFive      |

    # Alternate Flow

    Scenario: Successfully high five an idea which I already high fived to remove the high five
        Given that the user with id "223" high fives an idea with id "021"
        And a new entry of type "HighFive" is added to the reaction database with the following details:
            | id  | idea_id | user_id | reaction_type |
            | 001 | 021     | 457     | HighFive      |
            | 002 | 543     | 223     | HighFive      |
            | 003 | 021     | 223     | HighFive      |
        When the user with id "223" once again high fives the idea with id "021"
        Then a the reaction entry of id "003" shall be removed from the reaction database
            | id  | idea_id | user_id | reaction_type |
            | 001 | 021     | 457     | HighFive      |
            | 002 | 543     | 223     | HighFive      |

    # Error Flow

    Scenario: Unsuccessfully high five an idea that does not exist in the idea database
        Given the idea database only contains the following entries:
            | id  | title         | date       | purpose   | descriptions | isPaid | inProgress | isPrivate | domains | topics | techs | image URLs | icon URL | user |
            | 001 | Home Care App | 2022-02-15 | Health    | Quality app  | True   | True       | False     | 1       | 1, 2   | 2, 3  | 1, 3       | 2        | 1    |
            | 002 | Football Game | 2022-02-16 | Entertain | For fun      | False  | True       | False     | 1, 2    | 1      | 1, 3  | 1, 3       | 3        | 1    |
        When the user with id "223" high fives an idea with id "021"
        Then the error message <error> will be thrown with status code <Http_status>

        Examples:
            | error                             | Http_status |
            | Idea with id "021" does not exist | 404         |