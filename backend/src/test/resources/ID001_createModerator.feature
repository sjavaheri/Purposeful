Feature: Create Moderator
  As the owner, I want to create an moderator account so that I can monitor the use of the Purposeful application

  Background:
    Given the database contains the following moderator account:
      | username    | email                 | password         | authorities |
      | owner.steve | owner.steve@gmail.com | OwnerIsAwesome01 | [Moderator] |

  # Normal Flow

  Scenario Outline: Successfully create a new moderator account
    When a new moderator account is created with first name <firstName>, last name <lastName>, email <email> and password <password>
    Then a new moderator account exists in the database with username <username>, email <email>, password <password> and authorities <authorities>

    Examples:
      | firstName | lastName | username     | email                  | password       | authorities |
      | Mo        | Salah    | mo.salah     | mo.salah@gmail.com     | MoIsAwesome01  | [Moderator] |
      | Bob       | Marley   | bob.marley   | bob.marley@gmail.com   | BobIsAwesome01 | [Moderator] |
      | Bob       | Marley   | bob.marley.1 | bob.marley.1@gmail.com | BobIsAwesome01 | [Moderator] |

  # Error Flows

  Scenario Outline: Unsuccessfully create a new moderator account
    When a new moderator account is created with first name <firstName>, last name <lastName>, email <email> and password <password>
    Then the following error <error> shall be raised
    Then the number of moderator accounts in the database is "1"

    Examples:
      | firstName | lastName | email                    | password         | error                                                                                                                                                            |
      |           |          | henry.mcmillan@gmail.com | HenryIsAwesome01 | Please enter a valid name. Name cannot be left empty                                                                                                             |
      | Owner     | Steve    | owner.steve@gmail.com    | OwnerIsAwesome01 | An account with this email address already exists                                                                                                                |
      | Henry     | McMillan | henry.mcmillangmail.com  | HenryIsAwesome01 | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan | henry.mcmill@            | HenryIsAwesome01 | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan | henry.email@com          | HenryIsAwesome01 | The email address you entered is not valid                                                                                                                       |
      | Henry     | McMillan |                          | HenryIsAwesome01 | Please enter a valid email. Emails cannot be left empty                                                                                                          |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henrymcmillan01  | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com |                  | Please enter a valid password. Passwords cannot be left empty                                                                                                    |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryMcMillan    | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | Henry01          | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryisapassword | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henry            | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
      | Henry     | McMillan | henry.mcmillan@gmail.com | FFFFFFFFFFFFF8   | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character |
