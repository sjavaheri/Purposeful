Feature: Create Regular User
  As a regular user, I want to create an account for myself so that I can access all the features of the Purposeful application.

  Background:
    Given the database contains the following regular user account:
      | firstName | lastName | email                | password              | authorities |
      | User      | Steve    | user.steve@gmail.com | PurposefulIsAwesome01 | [User]      |

  # Normal Flow

  Scenario Outline: Successfully create a new regular user account
    When a new regular user account is created with first name <firstName>, last name <lastName>, email <email> and password <password>
    Then a new regular user account exists in the database with first name <firstName>, last name <lastName>, email <email>, password <password> and authorities <authorities>

    Examples:
      | firstName | lastName | email                | password       | authorities |
      | Mo        | Salah    | mo.salah@gmail.com   | MoIsAwesome01  | [User]      |
      | Bob       | Marley   | bob.marley@gmail.com | BobIsAwesome01 | [User]      |

  # Error Flows

  Scenario Outline: Unsuccessfully create a new regular user account
    When a new regular user account is created with first name <firstName>, last name <lastName>, email <email> and password <password>
    Then the following error <error> shall be raised
    Then the number of regular user accounts in the database is "1"

    Examples:
      | firstName | lastName | email                    | password              | error                                                                                                                                                            |
      |           |          | henry.mcmillan@gmail.com | HenryIsAwesome01      | Please enter a valid name. Name cannot be left empty                                                                                                             |
      | User      | Steve    | user.steve@gmail.com     | PurposefulIsAwesome01 | An account with this email address already exists                                                                                                                |
      | Henry     | McMillan | henry.mcmillangmail.com  | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan | henry.mcmill@            | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan | henry.email@com          | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan |                          | HenryIsAwesome01      | Please enter a valid email. Emails cannot be left empty                                                                                                          |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henrymcmillan01       | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com |                       | Please enter a valid password. Passwords cannot be left empty                                                                                                    |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryMcMillan         | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | Henry01               | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryisapassword      | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henry                 | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | FFFFFFFFFFFFF8        | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |


