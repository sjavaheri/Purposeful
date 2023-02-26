Feature: Modify Moderator
  As a user, I want to be able to express my support for an idea by giving it a "high five," so that I can express my positive sentiment for an idea without adding to a numerical score or ranking.

    # reaction database

  Background:
    Given the database contains the following users before high fiving an idea:
      | id | firstname | lastname | email                    | password |
      | 21 | Leia      | Organa   | leia.organa@republic.org | P@ssw0rd |
    And the database contains the following domains before high fiving an idea:
      | id | name     |
      | 1  | Software |
    And the database contains the following topics before high fiving an idea:
      | id | name    |
      | 2  | Music   |
      | 3  | Biology |
    And the database contains the following techs before high fiving an idea:
      | id | name    |
      | 4  | PyTorch |
      | 5  | React   |
    And the database contains the following URLs before high fiving an idea:
      | id | url                                                                                 |
      | 6  | https://www.flaticon.com/free-icon/music_3844724                                    |
      | 7  | https://miro.medium.com/v2/resize:fit:4800/format:webp/1*IWBf4ZlgysgEl-AaUJedRQ.png |
    And the database contains the following ideas before high fiving an idea:
      | id | title            | purpose                                           | domains | topics | techs | supportingImageUrls | iconUrl | isPaid | isInProgress | isPrivate | user |
      | 8  | Music generation | Open sourced software to generate classical music | 1       | 2      | 4, 5  | 7                   | 6       | false  | false        | false     | 21   |
      | 9  | Techno boom      | Open sourced software to generate techno music    | 1       | 2      | 4     |                     | 6       | false  | false        | false     | 21   |
    And the database contains the following reactions before high fiving an idea:
      | id | date       | reactionType | idea_id | user |
      | 10 | 25-02-2023 | HighFive     | 8       | 21   |
    And I am logged in before high fiving an idea

    # Normal Flow

  Scenario Outline: Successfully high five an idea
    When the user with id "<user>" reacts with a reaction "<reactionType>" to an idea with id "<idea_id>" on the date "<date>"
    Then a new reaction of idea "<idea_id>" and user "<user>" shall be added to the reaction database

    Examples:
      | id | date       | idea_id | user | reactionType |
      | 11 | 25-02-2023 | 9       | 21   | HighFive     |

    # Alternate Flow

  Scenario Outline: Successfully high five an idea which I already high fived to remove the high five
    When the user with id "<user>" reacts again with a reaction "<reactionType>" to an idea with id "<idea_id>" on the date "<date>"
    Then the reaction entry of idea "<idea_id>" and user "<user>" shall be removed from the reaction database

    Examples:
      | id | date       | idea_id | user | reactionType | exists |
      | 10 | 25-02-2023 | 8       | 21   | HighFive     | false  |
      | 11 | 25-02-2023 | 9       | 21   | HighFive     | false  |

    # Error Flow

  Scenario: Unsuccessfully react to an idea that does not exist in the idea database
    When the user with id "21" requests to react with the reaction "HighFive" to the idea with the invalid UUID "00000000-0000-0000-000000000000" on the date "25-02-2023"
    Then the error message "The requested idea does not exist" will be thrown with status code 401 after attempting to react