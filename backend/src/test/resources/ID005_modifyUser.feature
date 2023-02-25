Feature: Modify Moderator
  As a user, I want to modify the details of my account so that they contain my updated information

  Background:
    Given the database contains the following accounts:
      | firstname | lastname       | email                    | password             | authorities |
      | Enzo      | Benoit-Jeannin | enzo.benoit@gmail.com    | EnzoIsAwesome01      | User        |
      | Wassim    | Jabbour        | wassim.jabbour@gmail.com | WassimIsAwesome01    | User        |
      | Owner     | Steve          | owner.steve@gmail.com    | OwnerIsAwesome01     | Owner       |
      | Moderator | Bob            | moderator.bob@gmail.com  | ModeratorIsAwesome01 | Moderator   |

  # Normal Flow

  Scenario Outline: Successfully update a user account (own account)
    Given that the user is logged as user with email "enzo.benoit@gmail.com" and password "EnzoIsAwesome01"
    When the user requests to modify the account with email "enzo.benoit@gmail.com" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then account with email "enzo.benoit@gmail.com" have "<new_lastname>" as lastname and "<new_firstname>" as firstname
    Then the number of user accounts in the database shall be "4"

    Examples:
      | new_lastname   | new_firstname |
      | Benoit-Jeannin | Enzo          |
      | Benoit         | Enzo-Jeannin  |

  Scenario Outline: Successfully update a user account (from owner account)
    Given that the user is logged as owner with email "owner.steve@gmail.com" and password "OwnerIsAwesome01"
    When the user requests to modify the account with email "enzo.benoit@gmail.com" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then account with email "enzo.benoit@gmail.com" have "<new_lastname>" as lastname and "<new_firstname>" as firstname
    Then the number of user accounts in the database shall be "4"

    Examples:
      | new_lastname   | new_firstname |
      | Benoit-Jeannin | Enzo          |
      | Benoit         | Enzo-Jeannin  |

  Scenario Outline: Successfully update a user account (from moderator account)
    Given that the user is logged as moderator with email "moderator.bob@gmail.com" and password "ModeratorIsAwesome01"
    When the user requests to modify the account with email "enzo.benoit@gmail.com" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then account with email "enzo.benoit@gmail.com" have "<new_lastname>" as lastname and "<new_firstname>" as firstname
    Then the number of user accounts in the database shall be "4"

    Examples:
      | new_lastname   | new_firstname |
      | Benoit-Jeannin | Enzo          |
      | Benoit         | Enzo-Jeannin  |

  Scenario Outline: Successfully update a user password (own account)
    Given that the user is logged as user with email "enzo.benoit@gmail.com" and password "EnzoIsAwesome01"
    When the user requests to modify the account with email "enzo.benoit@gmail.com" with "<new_password>" as the password
    Then account with email "enzo.benoit@gmail.com" have "<new_password>" as password
    Then the number of user accounts in the database shall be "4"

    Examples:
      | new_password       |
      | EnzoIsAwesome02    |
      | NewEnzoIsAwesome01 |

  Scenario Outline: Successfully update a user password (from owner account)
    Given that the user is logged as owner with email "owner.steve@gmail.com" and password "OwnerIsAwesome01"
    When the user requests to modify the account with email "enzo.benoit@gmail.com" with "<new_password>" as the password
    Then account with email "enzo.benoit@gmail.com" have "<new_password>" as password
    Then the number of user accounts in the database shall be "4"

    Examples:
      | new_password       |
      | EnzoIsAwesome02    |
      | NewEnzoIsAwesome01 |

  Scenario Outline: Successfully update a user password (from moderator account)
    Given that the user is logged as moderator with email "moderator.bob@gmail.com" and password "ModeratorIsAwesome01"
    When the user requests to modify the account with email "enzo.benoit@gmail.com" with "<new_password>" as the password
    Then account with email "enzo.benoit@gmail.com" have "<new_password>" as password
    Then the number of user accounts in the database shall be "4"

    Examples:
      | new_password       |
      | EnzoIsAwesome02    |
      | NewEnzoIsAwesome01 |




  # Error Flows
  Scenario Outline: Unsuccessfully update a user account because you are not logged in
    Given The user is not logged in
    When the user erroneously requests to modify the account with email "<email>" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then the user should be denied permission to the requested resource with an HTTP status code of "<httpstatus>"

    Examples:
      | email                 | new_lastname | new_firstname | httpstatus |
      | enzo.benoit@gmail.com | Benoit       | NewEnzo       | 401        |

  Scenario Outline: Unsuccessfully update a user password because you are not logged in
    Given The user is not logged in
    When the user erroneously request to modify the account with email "<email>" with new password "<new_password>"
    Then the user should be denied permission to the requested resource with an HTTP status code of "<httpstatus>"

    Examples:
      | email                 | new_password       | httpstatus |
      | enzo.benoit@gmail.com | NewEnzoIsAwesome01 | 401        |

  Scenario Outline: Unsuccessfully update a user account because you are not logged in with the correct account
    Given that the user is logged as user with email "enzo.benoit@gmail.com" and password "EnzoIsAwesome01"
    When the user erroneously requests to modify the account with email "wassim.jabbour@gmail.com" with "<new_lastname>" as the new lastname and "<new_firstname>" as the new first name
    Then the user should be denied permission to the requested resource with an HTTP status code of "<httpstatus>"

    Examples:
      | new_lastname | new_firstname | httpstatus |
      | Benoit       | NewEnzo       | 403        |

  Scenario Outline: Unsuccessfully update a user password because you are not logged in with the correct account
    Given that the user is logged as user with email "enzo.benoit@gmail.com" and password "EnzoIsAwesome01"
    When the user erroneously request to modify the account with email "wassim.jabbour@gmail.com" with new password "<new_password>"
    Then the user should be denied permission to the requested resource with an HTTP status code of "<httpstatus>"

    Examples:
      | new_password       | httpstatus |
      | NewEnzoIsAwesome01 | 403        |

  Scenario Outline: Unsuccessfully update a user account because of wrong password
    Given that the user is logged as user with email "enzo.benoit@gmail.com" and password "EnzoIsAwesome01"
    When the user erroneously request to modify the account with email "enzo.benoit@gmail.com" with new password "<newPassword>"
    Then this error "<error>" shall be raised with http status code "<httpstatus>"
    Then the number of user accounts in the database shall be "4"

    Examples:
      | newPassword    | error                                                                                                                             | httpstatus |
      | enzo           | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      | enzo           | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      | Enzo1          | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      | thisisenzo     | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      | 111111111      | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      | thispassword1  | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      | FFFFFFFFFFFFF8 | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |
      |                | Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! | 400        |