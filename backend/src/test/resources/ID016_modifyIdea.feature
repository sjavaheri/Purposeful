Feature: Modify Idea
	As a user, I want to be able to modify the details of any idea that I have posted it so that my idea can be changed over time

	Background:
		Given the database contains the following user accounts:
			| id | firstName | lastName | email                | password         |
			| 1  | User      | Steve    | user.steve@gmail.com | SteveIsAwesome01 |
			| 2  | User      | Bob      | user.bob@gmail.com   | BobIsAwesome01   |
		And the database contains the following domains:
			| id | name       |
			| 1  | Software   |
			| 2  | Computer   |
			| 3  | Electrical |
		And the database contains the following topics:
			| id | name              |
			| 1  | Frontend Dev      |
			| 2  | Backend Dev       |
			| 3  | Embedded Software |
		And the database contains the following techs:
			| id | name   |
			| 1  | Python |
			| 2  | Java   |
			| 3  | React  |
			| 4  | C      |
		And the database contains the following URLs:
			| id | URL           |
			| 1  | something.com |
			| 2  | another.com   |
			| 3  | sayless.com   |
			| 4  | keepitup.com  |
			| 5  | interest.com  |
			| 6  | bestteam.com  |
		And the database contains the following ideas:
			| id | title             | date       | purpose   | descriptions     | isPaid | inProgress | isPrivate | domains | topics | techs | image URLs | icon URL | user |
			| 1  | Home Care App     | 2022-02-15 | Health    | Quality app      | True   | True       | False     | 1       | 1, 2   | 2, 3  | 1, 3       | 2        | 1    |
			| 2  | Football Game     | 2022-02-16 | Entertain | For fun          | False  | True       | False     | 1, 2    | 1      | 1, 3  | 1, 3       | 3        | 1    |
			| 3  | Car Detection App | 2022-02-17 | Police    | Effective app    | True   | False      | False     | 1, 3    | 1, 2   | 1, 2  | 3, 4       | 4        | 1    |
			| 4  | Circuit Design    | 2022-02-18 | Electric  | Silicon photonic | True   | False      | True      | 3       | 1, 3   | 1     | 5          | 6        | 1    |
		And I am logged in as the user with email "user.steve@gmail.com" and password "SteveIsAwesome01"

	# Normal Flow

	Scenario Outline: Successfully modify an idea
		When the user requests to modify the field <field> to become <new_value> instead of <old_value> for idea with id <idea_id>
		Then the idea with id <idea_id> will have value <new_value> for the field <field>

		Examples:
			| idea_id | field        | old_value     | new_value    |
			| 1       | title        | Home Care App | Health App   |
			| 1       | date         | 2022-02-15    | 2022-01-15   |
			| 2       | purpose      | Entertain     | FIFA Product |
			| 2       | descriptions | For fun       | La Liga      |
			| 3       | isPaid       | True          | False        |
			| 3       | inProgress   | False         | True         |
			| 4       | isPrivate    | True          | False        |
			| 4       | domains      | 3             | 1, 3         |
			| 1       | topics       | 1, 2          | 1            |
			| 2       | techs        | 1, 3          | 2, 3         |
			| 3       | image URLs   | 3, 4          | 1, 3, 4      |
			| 4       | icon URL     | 6             | 5            |

	# Alternate Flow

	Scenario Outline: Successfully modify the idea with an empty field that can be empty
		When the user requests to modify the field <field> to become empty for idea with id <idea_id>
		Then the idea with id <idea_id> will have empty for the field <field>

		Examples:
			| idea_id | field      |
			| 4       | domains    |
			| 1       | topics     |
			| 2       | techs      |
			| 3       | image URLs |
			| 4       | icon URL   |

	# Error Flow

	Scenario Outline: Unsuccessfully modify the idea with an empty field that cannot be empty
		When the user requests to modify the field <field> to become empty for idea with id <idea_id>
		Then the idea with id <idea_id> will have value <old_value> for the field <field>
		Then the error message <error> will be thrown with status code <Http_status>

		Examples:
			| idea_id | field        | old_value     | error                          | Http_status |
			| 1       | title        | Home Care App | title cannot be empty          | 400         |
			| 1       | date         | 2022-02-15    | date cannot be empty           | 400         |
			| 2       | purpose      | Entertain     | purpose cannot be empty        | 400         |
			| 2       | descriptions | For fun       | descriptions cannot be empty   | 400         |
			| 3       | isPaid       | True          | paid field cannot be empty     | 400         |
			| 3       | inProgress   | False         | progress field cannot be empty | 400         |
			| 4       | isPrivate    | True          | private field cannot be empty  | 400         |

	# Error Flow

	Scenario Outline: (Error Flow) Unsuccessfully modify an idea with a non-existing object
		When the user requests to modify the field <field> to become new value <new value> for idea with id <idea_id>
		Then the idea with id <idea_id> will have value <old_value> for the field <field>
		Then the error message <error> will be thrown with status code <Http_status>

		Examples:
			| idea_id | field      | old_value | new_value | error                     | Http_status |
			| 4       | domains    | 3         | 11        | domain does not exist     | 400         |
			| 1       | topics     | 1, 2      | 12        | topic does not exist      | 400         |
			| 2       | techs      | 1, 3      | 13        | technology does not exist | 400         |
			| 3       | image URLs | 3, 4      | 14        | URL(s) does not exist     | 400         |
			| 4       | icon URL   | 6         | 15        | URL does not exist        | 400         |



