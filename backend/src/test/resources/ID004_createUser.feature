Feature: Create Regular User
  As a regular user, I want to create an account for myself so that I can access all the features of the Purposeful application.

  Background:
    Given the database contains the following regular user account:
      | username   | email                | password              | authorities |
      | user.steve | user.steve@gmail.com | PurposefulIsAwesome01 | [User]      |

  # Normal Flow

  Scenario Outline: Successfully create a new regular user account
    When a new regular user account is created with username <username>, email <email> and password <password>
    Then a new regulat user account exists in the database with username <username>, email <email>, password <password> and authorities <authorities>

    Examples:
      | username   | email                | password       | authorities |
      | mo.salah   | mo.salah@gmail.com   | MoIsAwesome01  | [User]      |
      | bob.marley | bob.marley@gmail.com | BobIsAwesome01 | [User]      |

  # Error Flows

  Scenario Outline: Unsuccessfully create a new regular user account
    When a new regular user account is created with username <username>, email <email> and password <password>
    Then the following error <error> shall be raised
    Then the number of regulat user accounts in the database is "1"

    Examples:
      | username       | email                    | password              | error                                                                                                                                                            |
      |                | henry.mcmillan@gmail.com | HenryIsAwesome01      | Please enter a valid username. Username cannot be left empty                                                                                                     |
      | user.steve     | user.steve.1@gmail.com   | PurposefulIsAwesome01 | An account with this username already exists                                                                                                                     |
      | user.steve.1   | user.steve@gmail.com     | PurposefulIsAwesome01 | An account with this email address already exists                                                                                                                |
      | henry.mcmillan | henry.mcmillangmail.com  | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | henry.mcmillan | henry.mcmill@            | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | henry.mcmillan | henry.email@com          | HenryIsAwesome01      | The email address you entered is not valid                                                                                                                       |
      | henry.mcmillan |                          | HenryIsAwesome01      | Please enter a valid email. Emails cannot be left empty                                                                                                          |
      | henry.mcmillan | henry.mcmillan@gmail.com | henrymcmillan01       | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | henry.mcmillan | henry.mcmillan@gmail.com |                       | Please enter a valid password. Passwords cannot be left empty                                                                                                    |
      | henry.mcmillan | henry.mcmillan@gmail.com | henryMcMillan         | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | henry.mcmillan | henry.mcmillan@gmail.com | Henry01               | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | henry.mcmillan | henry.mcmillan@gmail.com | henryisapassword      | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | henry.mcmillan | henry.mcmillan@gmail.com | henry                 | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | henry.mcmillan | henry.mcmillan@gmail.com | FFFFFFFFFFFFF8        | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |


