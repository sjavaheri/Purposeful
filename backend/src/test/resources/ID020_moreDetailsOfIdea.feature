#Author: Wassim Jabbour
Feature: View more details for a feature
  As a user, I want to be able to view the details of an idea in a structured manner, so that I can grasp the essence of the idea and make informed decisions about its value and potential impact.

  Background:
    Given the database contains the following users:
      | id | firstname | lastname | email                    | password |
      | 0  | Leia      | Organa   | leia.organa@republic.org | P@ssw0rd |
    And the database contains the following domains:
      | id | name     |
      | 1  | Software |
    And the database contains the following topics:
      | id | name    |
      | 2  | Music   |
      | 3  | Biology |
    And the database contains the following techs:
      | id | name    |
      | 4  | PyTorch |
      | 5  | React   |
    And the database contains the following URLs:
      | id | url                                                                                 |
      | 6  | https://www.flaticon.com/free-icon/music_3844724                                    |
      | 7  | https://miro.medium.com/v2/resize:fit:4800/format:webp/1*IWBf4ZlgysgEl-AaUJedRQ.png |
    And the database contains the following ideas:
      | id | title            | purpose                                           | domains | topics | techs | supportingImageUrls | iconUrl | isPaid | isInProgress | isPrivate | user |
      | 8  | Music generation | Open sourced software to generate classical music | 1       | 2      | 4, 5  | 7                   | 6       | false  | false        | false     | 0    |
      | 9  | Techno boom      | Open sourced software to generate techno music    | 1       | 2      | 4     |                     | 6       | false  | false        | false     | 0    |
    And I am logged in

  # Main flow: Includes supporting images
  Scenario: View the details of an idea that includes supporting images
    When I request to view the details of idea with id 8
    Then the following information about the idea should be displayed:
      | title        | Music generation                                  |
      | purpose      | Open sourced software to generate classical music |
      | domains      | Software                                          |
      | topics       | Music                                             |
      | techs        | PyTorch, React                                    |
      | isPaid       | false                                             |
      | isInProgress | false                                             |
      | isPrivate    | false                                             |
    And the supporting image with the following URL should be displayed:
      | url                                                                                 |
      | https://miro.medium.com/v2/resize:fit:4800/format:webp/1*IWBf4ZlgysgEl-AaUJedRQ.png |
    And the icon with the following URL should be displayed:
      | url                                              |
      | https://www.flaticon.com/free-icon/music_3844724 |

  # Alternate flow: No supporting images
  Scenario: View the details of an idea that does not include supporting images
    When I request to view the details of idea with id 9
    Then the following information about the idea should be displayed:
      | title        | Techno boom                                    |
      | purpose      | Open sourced software to generate techno music |
      | domains      | Software                                       |
      | topics       | Music                                          |
      | techs        | PyTorch                                        |
      | isPaid       | false                                          |
      | isInProgress | false                                          |
      | isPrivate    | false                                          |
    And the icon with the following URL should be displayed:
      | url                                              |
      | https://www.flaticon.com/free-icon/music_3844724 |
    But no supporting images should be displayed

  # Error flow: Idea does not exist
  Scenario: Title of your scenario outline
    When I request to view the details of idea with UUID "00000000-0000-0000-0000-000000000000"
    Then the user shall recieve the error message "The requested idea does not exist" with status "400"

