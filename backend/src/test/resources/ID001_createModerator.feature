Feature: Create Moderator
  As the owner, I want to create an moderator account so that I can moniter the use of the Purposeful application

  Background:
    Given the database contains the following moderator account:
      | firstName | lastName | email                  | password         |
      | Owner     | Steve    | owner.steve@gmaill.com | OwnerIsAwesome01 |


  Scenario Outline: Successfully create a new moderator account
    When a new moderator account is created with "<firstName>", "<lastName>", "<email>" and "<password>"
    Then a new moderator account exists in the database with "<firstName>", "<lastName>", "<email>" and "<password>"
    Then the number of moderator accounts in the database is 2

    Examples:
      | firstName | lastName | email                | password       |
      | Mo        | Salah    | mo.salah@gmail.com   | MoIsAwesome01  |
      | Bob       | Marley   | bob.marley@gmail.com | BobIsAwesome01 |

  Scenario Outline: Unsuccessfully create a new moderator account
    When a new moderator account is created with "<firstName>", "<lastName>", "<email>" and "<password>"
    Then the following "<error>" shall be raised
    Then the number of moderator accounts in the database is 1

    Examples:
      | firstName | lastName | email                    | password         | error                                                                                                                                   |
      | Owner     | Steve    | owner.steve@gmail.com    | OwnerIsAwesome01 | An account with this email address already exists                                                                                       |
      | Henry     | McMillan | henry.mcmillangmail.com  | HenryIsAwesome01 | The email address you entered is not valid                                                                                              |
      | Henry     | McMillan | henry.mcmill@            | HenryIsAwesome01 | The email address you entered is not valid                                                                                              |
      | Henry     | McMillan | henry.email@com          | HenryIsAwesome01 | The email address you entered is not valid                                                                                              |
      | Henry     | McMillan |                          | HenryIsAwesome01 | Please enter a valid email. Emails cannot be left empty                                                                                 |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henrymcmillan01  | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com |                  | Please enter a valid password. Passwords cannot be left empty                                                                           |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryMcMillan    | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | Henry01          | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number and one uppercase character |





