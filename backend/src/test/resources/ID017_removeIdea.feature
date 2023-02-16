Feature: Remove Idea
    As a user, I want to be able to remove any idea that I have previously posted from the Purposeful application so that it no longer exists.

    Background:
        Given the database contains the following ideas:
            | id | title         | date       | purpose   | descriptions | isPaid | inProgress | isPrivate | domains | topics | techs | image URLs | icon URL | user |
            | 1  | Home Care App | 2022-02-15 | Health    | Quality app  | True   | True       | False     | 1       | 1, 2   | 2, 3  | 1, 3       | 2        | 1    |
            | 2  | Football Game | 2022-02-16 | Entertain | For fun      | False  | True       | False     | 1, 2    | 1      | 1, 3  | 1, 3       | 3        | 1    |
        And I am logged in as the user with email "user.steve@gmail.com" and password "SteveIsAwesome01"

    Scenario Outline: Successfully remove an idea
        When the user requests to remove the idea with id <idea_id>
        Then the idea with id <idea_id> and its attributes will no longer exist in the database

        Examples:
            | database     | id | title         | date       | purpose   | descriptions | isPaid | inProgress | isPrivate | domains | topics | techs | image URLs | icon URL | user |
            | old_database | 1  | Home Care App | 2022-02-15 | Health    | Quality app  | True   | True       | False     | 1       | 1, 2   | 2, 3  | 1, 3       | 2        | 1    |
            | old_database | 2  | Football Game | 2022-02-16 | Entertain | For fun      | False  | True       | False     | 1, 2    | 1      | 1, 3  | 1, 3       | 3        | 1    |
            | new_database | 1  | Home Care App | 2022-02-15 | Health    | Quality app  | True   | True       | False     | 1       | 1, 2   | 2, 3  | 1, 3       | 2        | 1    |


    Scenario Outline: (Alternate Flow) Successfully remove an idea with empty fields that cannot be empty
        When the user requests to remove the idea with id <idea_id> and an empty field <title>
        Then the idea with id <idea_id> and its attributes will no longer exist in the database

        Examples:
            | database     | id | title         | date       | purpose   | descriptions | isPaid | inProgress | isPrivate | domains | topics | techs | image URLs | icon URL | user |
            | old_database | 1  | Home Care App | 2022-02-15 | Health    | Quality app  | True   | True       | False     | 1       | 1, 2   | 2, 3  | 1, 3       | 2        | 1    |
            | old_database | 2  | title         | 2022-02-16 | Entertain | For fun      | False  | True       | False     | 1, 2    | 1      | 1, 3  | 1, 3       | 3        | 1    |
            | new_database | 1  | Home Care App | 2022-02-15 | Health    | Quality app  | True   | True       | False     | 1       | 1, 2   | 2, 3  | 1, 3       | 2        | 1    |

    Scenario Outline: (Error Flow) Unsuccessfully remove an idea with a non-existing object
        When the user requests to remove the idea with id <idea_id>
        Then the error message <error> will be thrown with status code <Http_status>

        Examples:
            | idea_id | error               | Http_status |
            | 1       | idea does not exist | 400         |