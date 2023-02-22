Feature: Modify Moderator
  As a moderator, I want to modify the details of my account so that they contain my updated information

  Background:
    Given the database contains these accounts:
      | firstname | lastname       | email                      | password              | authorities |
      | Enzo      | Benoit-Jeannin | enzo.benoit@gmail.com      | EnzoIsAwesome01       | User        |
      | Owner     | Steve          | owner.steve@gmail.com      | OwnerIsAwesome01      | Owner       |
      | Moderator | Bob            | moderator.bob@gmail.com    | ModeratorIsAwesome01  | Moderator   |
      | Moderator2| Adrien         | moderator.adrien@gmail.com | ModeratorIsAwesome01  | Moderator   |

  # Normal Flow

  Scenario Outline: Successfully update a moderator account (own account)
    Given that I am logged as moderator with email "moderator.bob@gmail.com" and password "ModeratorIsAwesome01"
    When I request to modify the account with email "moderator.bob@gmail.com" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then account with email "moderator.bob@gmail.com" have "<new_lastname>" "<new_firstname>" as lastname and firstname
    Then the number of accounts in the database shall be "4"

    Examples:
      | new_lastname   | new_firstname |
      | Steve2         | Owner2        |
      | Steve3         | Owner3        |

  Scenario Outline: Successfully update a moderator account (from owner account)
    Given that I am logged as owner with email "owner.steve@gmail.com" and password "OwnerIsAwesome01"
    When I request to modify the account with email "moderator.bob@gmail.com" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then account with email "moderator.bob@gmail.com" have "<new_lastname>" "<new_firstname>" as lastname and firstname
    Then the number of accounts in the database shall be "4"

    Examples:
      | new_lastname   | new_firstname |
      | Steve2         | Owner2        |
      | Steve3         | Owner3        |


  Scenario Outline: Successfully update a moderator password (own account)
    Given that I am logged as moderator with email "moderator.bob@gmail.com" and password "ModeratorIsAwesome01"
    When I request to modify the account with email "moderator.bob@gmail.com" with "<new_password>" as the password
    Then account with email "moderator.bob@gmail.com" have "<new_password>" as new password
    Then the number of accounts in the database shall be "4"

    Examples:
      | new_password         |
      | ModeratorIsAwesome03 |
      | ModeratorIsAwesome02 |

  Scenario Outline: Successfully update a moderator password (from owner account)
    Given that I am logged as owner with email "owner.steve@gmail.com" and password "OwnerIsAwesome01"
    When I request to modify the account with email "moderator.bob@gmail.com" with "<new_password>" as the password
    Then account with email "moderator.bob@gmail.com" have "<new_password>" as new password
    Then the number of accounts in the database shall be "4"

    Examples:
      | new_password         |
      | ModeratorIsAwesome03 |
      | ModeratorIsAwesome02 |

  # Error Flows
  Scenario Outline: Unsuccessfully update a moderator account because you are not logged in
    Given I am not logged in the application
    When I erroneously request to modify the account with email "<email>" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then the user should be denied permission to the requested resource and receive an HTTP status code of "<httpstatus>"

    Examples:
      | email                    | new_lastname   | new_firstname | httpstatus |
      | moderator.bob@gmail.com  | Steve2         | Owner2        | 401        |

  Scenario Outline: Unsuccessfully update a moderator password because you are not logged in
    Given I am not logged in the application
    When I erroneously request to modify the account with email "<email>" with new password "<new_password>"
    Then the user should be denied permission to the requested resource and receive an HTTP status code of "<httpstatus>"

    Examples:
      | email                    | new_password         | httpstatus |
      | moderator.bob@gmail.com  | ModeratorIsAwesome02 | 401        |

  Scenario Outline: Unsuccessfully update a moderator account because you are not logged in with the correct account
    Given that I am logged as user with email "<email>" and password "<password>"
    When I erroneously request to modify the account with email "moderator.bob@gmail.com" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then the user should be denied permission to the requested resource and receive an HTTP status code of "<httpstatus>"
    Examples:
      |email                      | password            | new_lastname   | new_firstname | httpstatus |
      |moderator.adrien@gmail.com | ModeratorIsAwesome01| Benoit         | NewEnzo       | 403        |
      |enzo.benoit@gmail.com      | EnzoIsAwesome01     | Benoit         | NewEnzo       | 403        |


  Scenario Outline: Unsuccessfully update a user password because you are not logged in with the correct account
    Given that I am logged as user with email "<email>" and password "<password>"
    When I erroneously request to modify the account with email "moderator.bob@gmail.com" with new password "<new_password>"
    Then the user should be denied permission to the requested resource and receive an HTTP status code of "<httpstatus>"

    Examples:
      |email                      | password            | new_password               | httpstatus |
      |moderator.adrien@gmail.com | ModeratorIsAwesome01| NewEnzoIsAwesome01         | 403        |
      |enzo.benoit@gmail.com      | EnzoIsAwesome01     | NewEnzoIsAwesome01         | 403        |


  Scenario Outline: Unsuccessfully update a user account because of wrong password
    Given that I am logged as user with email "moderator.bob@gmail.com" and password "ModeratorIsAwesome01"
    When I erroneously request to modify the account with email "moderator.bob@gmail.com" with new password "<newPassword>"
    Then the error "<error>" shall be raised with http status code "<httpstatus>"
    Then the number of accounts in the database shall be "4"

    Examples:
      | newPassword    | error                                                                                                                              | httpstatus |
      | enzo           | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      | enzo           | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400       |
      | Enzo1          | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400       |
      | thisisenzo     | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400       |
      | 111111111      | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400       |
      | thispassword1  | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400       |
      | FFFFFFFFFFFFF8 | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400       |
      |                | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400       |