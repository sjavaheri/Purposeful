Feature: Create Moderator
  As the owner, I want to create an moderator account so that I can monitor the use of the Purposeful application

  Background:
    Given the database contains the following moderator account:
      | firstname | lastname | email                 | password         | authorities |
      | Owner     | Steve    | owner.steve@gmail.com | OwnerIsAwesome01 | Moderator   |

  Scenario Outline: Successfully create a new moderator account
    When a new moderator account is created with first name "<firstname>", last name "<lastname>", email "<email>" and password "<password>"
    Then a new moderator account exists in the database with first name "<firstname>", last name "<lastname>", email "<email>", password "<password>" and authorities "<authorities>"
    Then the number of moderator accounts in the database is "2"

    Examples:
      | firstname | lastname | email                | password       | authorities |
      | Mo        | Salah    | mo.salah@gmail.com   | MoIsAwesome01  | Moderator   |
      | Bob       | Marley   | bob.marley@gmail.com | BobIsAwesome01 | Moderator   |

  Scenario Outline: Unsuccessfully create a new moderator account
    When a new moderator account is created erroneously with first name "<firstname>", last name "<lastname>", email "<email>" and password "<password>"
    Then the following error "<error>" shall be raised with http status "<httpstatus>"
    Then the number of moderator accounts in the database is "1"

    Examples:
      | firstname | lastname | email                    | password         | error                                                                                                                                                            | httpstatus |
      |           | McMillan | henry.mcmillan@gmail.com | HenryIsAwesome01 | Please enter a valid first name. First name cannot be left empty                                                                                                 | 400        |
      | Henry     |          | henry.mcmillan@gmail.com | HenryIsAwesome01 | Please enter a valid last name. Last name cannot be left empty                                                                                                   | 400        |
      | Owner     | Steve    | owner.steve@gmail.com    | OwnerIsAwesome01 | An account with this email address already exists                                                                                                                | 400        |
      | Henry     | McMillan | henry.mcmillangmail.com  | HenryIsAwesome01 | Please enter a valid email. The email address you entered is not valid                                                                                           | 400        |
      | Henry     | McMillan | henry.mcmill@            | HenryIsAwesome01 | Please enter a valid email. The email address you entered is not valid                                                                                           | 400        |
      | Henry     | McMillan | henry.email@com          | HenryIsAwesome01 | Please enter a valid email. The email address you entered is not valid                                                                                           | 400        |
      | Henry     | McMillan |                          | HenryIsAwesome01 | Please enter a valid email. Email cannot be left empty                                                                                                           | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henrymcmillan01  | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com |                  | Please enter a valid password. Password cannot be left empty                                                                                                     | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryMcMillan    | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | Henry01          | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henryisapassword | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | henry            | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
      | Henry     | McMillan | henry.mcmillan@gmail.com | FFFFFFFFFFFFF8   | Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character | 400        |
