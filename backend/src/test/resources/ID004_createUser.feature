Feature: Create Regular User
  As a regular user, I want to create an account for myself so that I can access all the features of the Purposeful application.

  Background:
    Given the database contains the following regular user account:
      | username   | firstName | lastName | email                | password              | authorities |
      | user.steve | User      | Steve    | user.steve@gmail.com | PurposefulIsAwesome01 | [User]      |

  # Normal Flow

  Scenario Outline: Successfully create a new regular user account
    When a new regular user account is created with first name <firstName>, last name <lastName>, username <username>, email <email> and password <password>
    Then a new regular user account exists in the database with first name <firstName>, last name <lastName>, username <username>, email <email>, password <password> and authorities <authorities>

    Examples:
      | firstName | lastName | username   | email                | password       | authorities |
      | Mo        | Salah    | mo.salah   | mo.salah@gmail.com   | MoIsAwesome01  | [User]      |
      | Bob       | Marley   | bob.marley | bob.marley@gmail.com | BobIsAwesome01 | [User]      |

  # Error Flows

  Scenario Outline: Unsuccessfully create a new regular user account
    When a new regular user account is created with first name <firstName>, last name <lastName>, username <username>, email <email> and password <password>
    Then the following error <error> shall be raised
    Then the number of regular user accounts in the database is "1"

    Examples:
      | firstName | lastName | username       | email                    | password              | error                                                                                                                                                            |
      | Henry     | McMillan |                | henry.mcmillan@gmail.com | HenryIsAwesome01      | Please enter a valid username. Username cannot be left empty                                                                                                     |
      | User      | Steve    | user.steve     | user.steve.1@gmail.com   | PurposefulIsAwesome01 | An account with this username already exists                                                                                                                     |
      | User      | Steve    | user.steve.1   | user.steve@gmail.com     | PurposefulIsAwesome01 | An account with this email address already exists                                                                                                                |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillangmail.com  | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan | henry.mcmillan | henry.mcmill@            | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan | henry.mcmillan | henry.email@com          | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan | henry.mcmillan |                          | HenryIsAwesome01      | Please enter a valid email. Emails cannot be left empty                                                                                                          |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillan@gmail.com | henrymcmillan01       | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillan@gmail.com |                       | Please enter a valid password. Passwords cannot be left empty                                                                                                    |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillan@gmail.com | henryMcMillan         | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillan@gmail.com | Henry01               | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillan@gmail.com | henryisapassword      | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillan@gmail.com | henry                 | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan | henry.mcmillan@gmail.com | FFFFFFFFFFFFF8        | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |


