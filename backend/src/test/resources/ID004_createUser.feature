Feature: Create Regular User
  As a regular user, I want to create an account for myself so that I can access all the features of the Purposeful application.

  Background:
    Given the database contains the following regular user account:
      | firstname | lastname | email                | password              | authorities |
      | User      | Steve    | user.steve@gmail.com | PurposefulIsAwesome01 | User        |

  # Normal Flow

  Scenario Outline: Successfully create a new regular user account
    When a new regular user account is created with first name "<firstname>", last name "<lastname>", email "<email>" and password "<password>"
    Then a new regular user account exists in the database with first name "<firstname>", last name "<lastname>", email "<email>" and authorities "<authorities>"
    Then the number of regular user accounts in the database is "2"

    Examples:
      | firstname | lastname | email                | password       | authorities |
      | Mo        | Salah    | mo.salah@gmail.com   | MoIsAwesome01  | User        |
      | Bob       | Marley   | bob.marley@gmail.com | BobIsAwesome01 | User        |

  # Error Flows

  Scenario Outline: Unsuccessfully create a new regular user account
    When a new regular user account is created erroneously with first name "<firstname>", last name "<lastname>", email "<email>" and password "<password>"
    Then the following error "<error>" shall be raised with http status "<httpstatus>"
    Then the number of regular user accounts in the database is "1"

    Examples:
      | firstname | lastname | email                    | password              | error                                                                                                                                                            | httpstatus |
      |           | McMillan | henry.mcmillan@gmail.com | HenryIsAwesome01      | Please enter a valid first name. First name cannot be left empty                                                                                                 | 400        |
      | Henry     |          | henry.mcmillan@gmail.com | HenryIsAwesome01      | Please enter a valid last name. Last name cannot be left empty                                                                                                   | 400        |
      | User      | Steve    | user.steve@gmail.com     | PurposefulIsAwesome01 | An account with this email address already exists                                                                                                                | 400        |
      | Henry     | McMillan | henry.mcmillangmail.com  | HenryIsAwesome01      | Please enter a valid email. The email address you entered is not valid                                                                                           | 400        |
      | Henry     | McMillan | henry.mcmill@            | HenryIsAwesome01      | Please enter a valid email. The email address you entered is not valid                                                                                           | 400        |
      | Henry     | McMillan | henry.email@com          | HenryIsAwesome01      | Please enter a valid email. The email address you entered is not valid                                                                                           | 400        |
      | Henry     | McMillan |                          | HenryIsAwesome01      | Please enter a valid email. Email cannot be left empty                                                                                                           | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henrymcmillan01       | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com |                       | Please enter a valid password. Password cannot be left empty                                                                                                     | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryMcMillan         | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | Henry01               | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryisapassword      | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henry                 | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | FFFFFFFFFFFFF8        | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |


